package cn.gydata.zckb.zckbinterfaceorder.service;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderInfo;
import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderItem;
import cn.gydata.zckb.zckbinterfaceorder.mapper.order.OrderInfoMapper;
import cn.gydata.zckb.zckbinterfaceorder.util.OrderUtils;
import cn.gydata.zckb.zckbinterfaceorder.vo.ReturnOrderInfoVo;
import cn.gydata.zckb.zckbinterfaceorder.vo.ReturnUserOrderInfoVo;
import cn.gydata.zckb.zckbinterfaceorder.vo.UserVo;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderInfoService
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-16 09:47
 * @VERSION 1.0
 **/
@Service
public class OrderInfoService extends BaseService<OrderInfo> {
    OrderInfoService(OrderInfoMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    private OrderItemService orderItemService;


    /**
     * 生成主订单
     *
     * @param userId
     * @param vipType
     * @param payType
     * @param deviceType
     * @param shopId
     * @param userVo
     * @param memberGroup
     * @return
     */
    public ReturnOrderInfoVo produceMainOrder(Long userId, Integer vipType, Integer payType, Integer deviceType, Long shopId, UserVo userVo, String memberGroup, Boolean isUpgrade) {
        ReturnOrderInfoVo returnOrderInfoVo = new ReturnOrderInfoVo();

        String orderNo = OrderUtils.produceOrderNo();
        Long buy_user = userId;
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNumber(orderNo);
        orderInfo.setBuyUserId(buy_user);
        orderInfo.setTardeStatus((byte) 0);
        orderInfo.setPayStatus((byte) 1);
        orderInfo.setOrderAmount(0);
        orderInfo.setPayAmount(0);
        orderInfo.setTotalAmount(0);
        orderInfo.setPayTime(0L);
        orderInfo.setInvoiceId(0L);
        orderInfo.setPayType((byte) payType.intValue());
        orderInfo.setOuterTradeNo("");
        orderInfo.setCreateTime(new Date().getTime());
        orderInfo.setUpdateTime(new Date().getTime());
        orderInfo.setIsDelete((byte) 0);
        orderInfo.setAppKey("zckb");

        mapper.insert(orderInfo);

        boolean result = orderItemService.produceOrderItems(deviceType, vipType, shopId, userVo, orderInfo.getOrderId(), memberGroup, isUpgrade);
        if (!result) {
            orderInfo.setIsDelete((byte) 1);
            update(orderInfo);
        }

        if (result) {
            returnOrderInfoVo.setCode(200);
            returnOrderInfoVo.setMsg("ok");
            orderInfo = getById(orderInfo.getOrderId());
            returnOrderInfoVo.setOrderInfo(orderInfo);
        } else if (!result && orderInfo.getIsDelete() == 1) {
            returnOrderInfoVo.setCode(101);
            returnOrderInfoVo.setMsg("商品状态不允许购买，请联系客服");
            returnOrderInfoVo.setOrderInfo(null);
        } else {
            returnOrderInfoVo.setCode(100);
            returnOrderInfoVo.setMsg("添加订单出错(XCODE:0000X1)，请联系客服。");
            returnOrderInfoVo.setOrderInfo(null);
        }

        return returnOrderInfoVo;
    }


    public boolean updateOrderInfoPayAmountPrice(Long orderInfoId, Integer countPrice, String body) {
        try {
            OrderInfo orderInfo = getById(orderInfoId);
            orderInfo.setOrderAmount(countPrice);
            orderInfo.setPayAmount(countPrice);
            orderInfo.setRemark(body);
            update(orderInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean paySuccessOrderInfoPayStatus(byte tardeStatus, String orderNumber, String totalFee) {
        try {
            paySuccessOrderInfoPayStatus(tardeStatus, orderNumber, totalFee, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean paySuccessOrderInfoPayStatus(byte tardeStatus, String orderNumber, String totalFee, String transactionId) {
        try {
            Double totalFeeD = (Double.parseDouble(totalFee) * 100);
            Example example = new Example(OrderInfo.class);
            example.createCriteria().andEqualTo("orderNumber", orderNumber);
            OrderInfo orderInfo = mapper.selectOneByExample(example);
            orderInfo.setTotalAmount(totalFeeD.intValue());
            orderInfo.setPayTime(new Date().getTime());
            orderInfo.setUpdateTime(new Date().getTime());
            orderInfo.setPayStatus((byte) (tardeStatus == 1 ? 2 : 1));
            orderInfo.setTardeStatus(tardeStatus);
            if (transactionId != null)
                orderInfo.setOuterTradeNo(transactionId);
            if (orderItemService.updateOrderItemPayStatus(orderInfo.getOrderId())) {
                update(orderInfo);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public OrderInfo getOrderInfoByOrderNumber(String orderNumber) {
        try {

            Example example = new Example(OrderInfo.class);
            example.createCriteria().andEqualTo("orderNumber", orderNumber);
            return mapper.selectOneByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean paySuccessUpdateUserAccount(String orderNumber) {
        OrderInfo parameter = new OrderInfo();
        parameter.setOrderNumber(orderNumber);
        OrderInfo orderInfo = mapper.selectOne(parameter);
        return orderItemService.paySuccessUpdateUserAccount(orderInfo.getOrderId());
    }

    /**
     * 获取用户订单
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<ReturnUserOrderInfoVo> getUserOrder(Long userId, Integer pageNum, Integer pageSize) {
        List<ReturnUserOrderInfoVo> returnList = new ArrayList<>();
        PageHelper.startPage((pageNum - 1) * pageSize, pageSize);
        Example example = new Example(OrderInfo.class);

        example.setOrderByClause("create_time desc");
        example.createCriteria().andEqualTo("buyUserId", userId);
        List<OrderInfo> orderInfos = mapper.selectByExample(example);
        for (OrderInfo orderInfo : orderInfos
        ) {
            ReturnUserOrderInfoVo returnUserOrderInfoVo = new ReturnUserOrderInfoVo();
            List<OrderItem> orderItems = orderItemService.getOrderItems(orderInfo.getOrderId());
            returnUserOrderInfoVo.setOrderInfo(orderInfo);
            returnUserOrderInfoVo.setOrderItems(orderItems);
            returnList.add(returnUserOrderInfoVo);
        }
        return returnList;
    }

    /**
     * 获取用户最后一笔订单
     *
     * @param userId
     * @return
     */
    public Long getUserLastOrder(Long userId) {
        Long shopId = 0L;
        PageHelper.startPage(0, 1);
        Example example = new Example(OrderInfo.class);

        example.setOrderByClause("create_time desc");
        example.createCriteria().andEqualTo("buyUserId", userId)
                .andEqualTo("tardeStatus", 1)
                .andEqualTo("payStatus", 2)
                .andNotEqualTo("payTime", 0);
        OrderInfo orderInfo = mapper.selectOneByExample(example);
        List<OrderItem> orderItems = orderItemService.getOrderItems(orderInfo.getOrderId());
        if (orderItems.size() > 0) {
            shopId = orderItems.get(0).getFormatId();
        }
        return shopId;
    }

    /**
     * 获取未开票的订单
     *
     * @return
     */
    public List<OrderInfo> getNoInvoiceOrder(Long userId) {
        Example example = new Example(OrderInfo.class);

        example.setOrderByClause("create_time desc");
        example.createCriteria().andEqualTo("buyUserId", userId)
                .andEqualTo("invoiceId", 0)
                .andEqualTo("tardeStatus", 1)
                .andEqualTo("payStatus", 2)
                .andNotEqualTo("payTime", 0)
                .andBetween("payTime", new Date().getTime() - (1000 * 60 * 60 * 24 * 30L), new Date().getTime());
        List<OrderInfo> orderInfos = mapper.selectByExample(example);
        return orderInfos;
    }


    /**
     * 修改订单开票状态
     *
     * @return
     */
    public boolean updateOrderInvoiceId(String orderId, String invoiceId) {
        try {
            String[] orderIds = orderId.split(",");
            String[] invoiceIds = invoiceId.split(",");
            if (orderIds.length == invoiceIds.length) {
                for (int i = 0; i < orderIds.length; i++) {
                    OrderInfo orderInfo = getById(Long.parseLong(orderIds[i]));
                    orderInfo.setInvoiceId(Long.parseLong(invoiceIds[i]));
                    update(orderInfo);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
