package cn.gydata.zckb.zckbinterfaceorder.service;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderInfo;
import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderItem;
import cn.gydata.zckb.zckbinterfaceorder.mapper.order.OrderItemMapper;
import cn.gydata.zckb.zckbinterfaceorder.service.shop_server.ShopServer;
import cn.gydata.zckb.zckbinterfaceorder.service.user_server.UserServer;
import cn.gydata.zckb.zckbinterfaceorder.util.OrderUtils;
import cn.gydata.zckb.zckbinterfaceorder.vo.UserVo;
import cn.hutool.db.sql.Order;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderItemService
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-16 09:47
 * @VERSION 1.0
 **/
@Service
@RefreshScope
public class OrderItemService extends BaseService<OrderItem> {

    OrderItemService(OrderItemMapper mapper) {
        this.mapper = mapper;
    }

    @Value("${test.userIds}")
    private String userIds;

    @Autowired
    private ShopServer shopServer;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private UserServer userServer;

    public boolean produceOrderItems(Integer deviceType, Integer vipType, Long shopId, UserVo userVo, Long orderInfoId, String memberGroup, Boolean isUpgrade) {
        try {
            JSONObject shopInfo = getUserBuyShopPrice(userVo, shopId, deviceType, vipType, memberGroup);
            if (shopInfo.getBoolean("isDisable"))
                return false;
            Integer price;

            if (isUpgrade && shopInfo.getBoolean("isUpgrade")){
                price = shopInfo.getInteger("upgradePrice");
            }else {
                price = shopInfo.getInteger("upgradePrice") +shopInfo.getInteger("realityPrice");
            }

            if (!userIds.equals("") && userIds !=null){
                String[] userId = userIds.split(",");
                for (String user:userId
                     ) {
                    if (Long.parseLong(user) == userVo.getUserId()) {
                        price = 1;
                        break;
                    }
                }
            }


            return produceOrderItem(shopInfo,userVo, price, orderInfoId, memberGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean produceOrderItem(JSONObject shopInfo,UserVo userVo, Integer price,Long orderId, String memberGroup) {
        try {

            String body = "";
            String OrderNo = OrderUtils.produceOrderNoByTime();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setOrderNumber(OrderNo);
            orderItem.setOrderStatus((byte) 1);
            orderItem.setFormatId(shopInfo.getLong("shopId"));
            orderItem.setBuyUserId(userVo.getUserId());
            orderItem.setSellerId((long) 0);
            orderItem.setDeliveryStatus((byte) 0);
            orderItem.setDeliveryType((byte) 0);
            orderItem.setBuyerStatus((byte) 2);
            orderItem.setGoodsNumber(1);
            orderItem.setGoodsPrice(price);
            orderItem.setGoodsAmount(price);
            orderItem.setSellerFinishTime(0L);
            orderItem.setBuyerFinishTime(0L);
            orderItem.setMethodId(0);
            orderItem.setCreateTime(new Date().getTime());
            orderItem.setUpdateTime(new Date().getTime());
            orderItem.setAppKey("zckb");
            orderItem.setIsDelete((byte) 0);
            orderItem.setRemark(shopInfo.getString("shopTitle"));

            shopInfo.put("userBuyMemberGroup",memberGroup);
            orderItem.setShopSnapShot(JSON.toJSONString(shopInfo));
            mapper.insert(orderItem);
            body += shopInfo.getString("shopTitle");
            if (orderInfoService.updateOrderInfoPayAmountPrice(orderId, price, body)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 获取ShopParameter
     *
     * @param userVo
     * @param shopId
     * @param deviceType
     * @param vipType
     * @param memberGroup
     * @return
     */
    public JSONObject getUserBuyShopPrice(UserVo userVo, Long shopId, Integer deviceType, Integer vipType, String memberGroup) {
        return shopServer.countUserBuyShopPrice(shopId, userVo, deviceType, vipType, memberGroup);
    }

    public boolean updateOrderItemPayStatus(Long orderId) {
        try {
            Example example = new Example(OrderItem.class);
            example.createCriteria().andEqualTo("orderId", orderId);
            List<OrderItem> orderItems = mapper.selectByExample(example);
            for (OrderItem orderItem : orderItems
            ) {
                orderItem.setBuyerStatus((byte) 1);
                update(orderItem);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 支付成功后修改会员状态
     *
     * @param orderId
     * @return
     */
    public boolean paySuccessUpdateUserAccount(Long orderId) {
        try {
            OrderItem parameter = new OrderItem();
            parameter.setOrderId(orderId);
            List<OrderItem> orderItems = mapper.select(parameter);
            for (OrderItem orderItem : orderItems
            ) {
                JSONObject shopProductInfo = JSONObject.parseObject(orderItem.getShopSnapShot());
                Long userId = orderItem.getBuyUserId();
                Integer addValidityDays = shopProductInfo.getInteger("parameter1");
                String memberGroup = shopProductInfo.getString("userBuyMemberGroup");
                Integer pricingPackageType = shopProductInfo.getInteger("parameter2");
                Long shopId = shopProductInfo.getLong("shopId");
                return userServer.updateUserAccount(addValidityDays, userId, memberGroup, pricingPackageType,shopId);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取子订单列表
     *
     * @param orderId
     * @return
     */
    public List<OrderItem> getOrderItems(Long orderId) {
        try {
            Example example = new Example(OrderItem.class);
            example.setOrderByClause("create_time desc");
            example.createCriteria().andEqualTo("orderId", orderId);
            List<OrderItem> orderItems = mapper.selectByExample(example);
            return orderItems;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
