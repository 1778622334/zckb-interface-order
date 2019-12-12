package cn.gydata.zckb.zckbinterfaceorder.service;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.WxPayReturn;
import cn.gydata.zckb.zckbinterfaceorder.enums.PayTypeEnum;
import cn.gydata.zckb.zckbinterfaceorder.service.shop_server.ShopServer;
import cn.gydata.zckb.zckbinterfaceorder.util.HttpUtil;
import cn.gydata.zckb.zckbinterfaceorder.util.MapObjUtil;
import cn.gydata.zckb.zckbinterfaceorder.vo.ReturnPayInfoVo;
import cn.gydata.zckb.zckbinterfaceorder.vo.ReturnOrderInfoVo;
import cn.gydata.zckb.zckbinterfaceorder.vo.UserVo;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WeiXinPayService
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-16 15:18
 * @VERSION 1.0
 **/
@Service
public class WeiXinPayService {

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private ShopServer shopServer;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private UserServerService userServerService;

    @Autowired
    private WxPayReturnService wxPayReturnService;

    @Value("${wxpay.appid}")
    private String WX_APPID;

    @Value("${wxpay.mch_id}")
    private String WX_MCH_ID;

    @Value("${wxpay.secret}")
    private String WX_SECRET;

    @Value("${wxpay.key}")
    private String WX_KEY;

    @Value("${wxpay.notifyurl}")
    private String WX_NOTIFYURL;

    /**
     * 获取微信支付信息
     *
     * @param userId
     * @param memberGroup
     * @param shopId
     * @param deviceType
     * @return
     */
    public ReturnPayInfoVo getPayInfo(Long userId, Integer vipType, String memberGroup, Long shopId, Integer deviceType, String clientIp,Boolean isUpgrade) {

        UserVo userVo = userServerService.getUserAccount(userId);


        ReturnPayInfoVo returnPayInfoVo = isPayCompanyVip(userVo, shopId, deviceType, memberGroup);

        if (returnPayInfoVo.getCode() == 200 && returnPayInfoVo.getMsg().equals("ok")) {
            ReturnOrderInfoVo returnOrderInfoVo = orderInfoService.produceMainOrder(userVo.getUserId(), vipType, PayTypeEnum.WxPay.getCode(), deviceType, shopId, userVo, memberGroup,isUpgrade);

            if (returnOrderInfoVo.getOrderInfo() != null) {
                try {
                    String total_fee = String.valueOf(returnOrderInfoVo.getOrderInfo().getPayAmount());

                    String strCode = returnOrderInfoVo.getOrderInfo().getRemark();////商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。

                    String wx_body = "";
                    try {
                        wx_body = new String(strCode.getBytes("utf-8"), "utf-8");
                    } catch (Exception e) {
                    }

//            OurWxPayConfig ourWxPayConfig = new OurWxPayConfig();
//            WXPay wxPay = new WXPay(ourWxPayConfig);
                    //String nonce_str = WXPayUtil.generateNonceStr();

                    //根据微信支付api来设置
                    Map<String, String> data = new HashMap<>();
                    data.put("appid", WX_APPID);
                    data.put("mch_id", WX_MCH_ID);         //商户号
                    data.put("nonce_str", WXPayUtil.generateNonceStr());   // 随机字符串小于32位
                    data.put("body", wx_body);
                    data.put("out_trade_no", returnOrderInfoVo.getOrderInfo().getOrderNumber());   //交易号
                    data.put("total_fee", total_fee);       //订单总金额
                    data.put("spbill_create_ip", clientIp);             //终端ip
                    data.put("notify_url", WX_NOTIFYURL);                     //回调地址
                    data.put("trade_type", "APP");                         //支付场景 APP 微信app支付 JSAPI 公众号支付  NATIVE 扫码支付
                    //data.put("fee_type", "CNY");                           //默认人民币

                    String s = WXPayUtil.generateSignature(data, WX_KEY);  //签名
                    data.put("sign", s);

                    /** wxPay.unifiedOrder 这个方法中调用微信统一下单接口 */
                    String result = HttpUtil.httpMethodPost("https://api.mch.weixin.qq.com/pay/unifiedorder", WXPayUtil.mapToXml(data).toString(), "UTF-8");

                    Map<String, String> respData = WXPayUtil.xmlToMap(result);
                    //Map<String, String> respData = wxPay.unifiedOrder(data);
                    if (respData.get("return_code").equals("SUCCESS")) {
                        //返回给APP端的参数，APP端再调起支付接口
                        Map<String, String> repData = new HashMap<>();
                        repData.put("appid", WX_APPID);
                        repData.put("partnerid", WX_MCH_ID);
                        repData.put("prepayid", respData.get("prepay_id"));
                        repData.put("noncestr", data.get("nonce_str"));
                        repData.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                        repData.put("package", "Sign=WXPay");


                        returnPayInfoVo.setAppId(WX_APPID);
                        returnPayInfoVo.setPartnerId(WX_MCH_ID);
                        returnPayInfoVo.setPrepayId(respData.get("prepay_id"));
                        returnPayInfoVo.setNonceStr(data.get("nonce_str"));
                        returnPayInfoVo.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
                        returnPayInfoVo.setPackages("Sign=WXPay");

                        String sign = WXPayUtil.generateSignature(repData, WX_KEY); //签名
                        returnPayInfoVo.setSign(sign);
                        returnPayInfoVo.setRechargeCode(returnOrderInfoVo.getOrderInfo().getOrderNumber());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                returnPayInfoVo.setMsg("400");
                returnPayInfoVo.setMsg("订单不存在");
            }

        }

        return returnPayInfoVo;
    }



    /**
     * 判断用户是否可购买
     *
     * @param userVo
     * @param shopId
     * @return
     */
    public ReturnPayInfoVo isPayCompanyVip(UserVo userVo, Long shopId, Integer deviceType, String memberGroup) {
        ReturnPayInfoVo returnPayInfoVo = new ReturnPayInfoVo();

        if (userVo == null) {
            returnPayInfoVo.setCode(100);
            returnPayInfoVo.setMsg("用户不存在，请稍后再试");
        }

        if (userVo.getMemberGroup().equals("0")) {
            if (!memberGroup.equals(userVo.getMemberGroup())) {
                returnPayInfoVo.setCode(101);
                returnPayInfoVo.setMsg("您已购买全国版会员，不允许降级购买");
            }
        }

        if (!userVo.getMemberGroup().equals("")) {
            if (!compareArray(userVo.getMemberGroup().split(","), memberGroup.split(","))) {
                returnPayInfoVo.setCode(102);
                returnPayInfoVo.setMsg("选择的地区与用户不一致，请联系客服");
            }
        }
        Map resultShop = shopServer.isUserPayShop(deviceType, shopId, userVo);
        Map result = (Map) resultShop.get("JsonContent");
        returnPayInfoVo.setCode((Integer) result.get("code"));
        returnPayInfoVo.setMsg((String) result.get("msg"));
        return returnPayInfoVo;
    }

    private static boolean compareArray(String[] group1,String[] group2){

        int[] cityIds1 = strArrayToIntArray(group1);
        Arrays.sort(cityIds1);
        int[] cityIds2 = strArrayToIntArray(group2);
        Arrays.sort(cityIds2);
        boolean isBoolean = true;
        for (int i = 0; i < cityIds1.length; i ++){
            if (cityIds1[i] != cityIds2[i])
                isBoolean =false;
        }
        return isBoolean;
    }



    public static int[] strArrayToIntArray(String[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = Integer.parseInt(a[i]);
        }

        return b;
    }

    /**
     * 判断用户是否选择已购的地区
     *
     * @param userMemberGroup
     * @param selectMemberGroup
     * @return
     */
    public boolean isExistMemberGroup(String userMemberGroup, String selectMemberGroup) {
        String[] userMemberGroups = userMemberGroup.split(",");
        String[] selectMemberGroups = selectMemberGroup.split(",");
        boolean has = false;
        for (int i = 0; i < userMemberGroups.length; i++) {
            if (ArrayUtils.contains(selectMemberGroups, userMemberGroups[i])) {
                has = true;
                break;
            }
        }

        return has;
    }

    /**
     * 微信异步回调
     *
     * @param map
     * @return
     */
    public Boolean wxPayNotify(Map<String, String> map) {
        if (map == null)
            return false;
        try {
            String returnCode = map.get("return_code");
            if (WXPayConstants.SUCCESS.equals(returnCode)) {
                String resultCode = map.get("result_code");
                String outTradeNo = map.get("out_trade_no");
                String appId = map.get("appid");
                String mchId = map.get("mch_id");
                /* 验签 */
                if (WXPayUtil.isSignatureValid(map, WX_KEY)) {
                    if (WX_APPID.equals(appId) && WX_MCH_ID.equals(mchId) && "SUCCESS".equals(resultCode)) {
                        String totalFee = map.get("total_fee");// 总金额
//                        String wxTransactionId = map.get("transaction_id");
                        //3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
                        if (!mchId.equals(WX_MCH_ID)) {
                            return false;
                        }
                        //4、验证app_id是否为该商户本身。
                        if (!appId.equals(WX_APPID)) {
                            return false;
                        }

                        //必须同时订单和修改用户账户状态成功，则整个支付流程成功
                        if (orderInfoService.paySuccessOrderInfoPayStatus((byte) 1, outTradeNo, totalFee)) {
                            if (orderInfoService.paySuccessUpdateUserAccount(outTradeNo)) {
                                return true;
                            }
                        }

                        return false;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public Map weixinPayReturn(String rechargeCode, String transactionId) {


        if (StringUtils.isBlank(rechargeCode) && StringUtils.isBlank(transactionId)) {
            return writeMessage(400, "参数传递有误！");
        }
        try {
            Map<String, String> orderMap = new HashMap<>();
            orderMap.put("appid", WX_APPID);
            orderMap.put("mch_id", WX_MCH_ID);
            orderMap.put("nonce_str", WXPayUtil.generateNonceStr());
            if (StringUtils.isNotBlank(transactionId)) {
                orderMap.put("transaction_id", transactionId);
            } else {
                orderMap.put("out_trade_no", rechargeCode);
            }
            orderMap.put("sign", WXPayUtil.generateSignature(orderMap, WX_KEY));


            String result = HttpUtil.httpMethodPost("https://api.mch.weixin.qq.com/pay/orderquery", WXPayUtil.mapToXml(orderMap), "UTF-8");
            Map<String, String> returnMap = WXPayUtil.xmlToMap(result);
            String return_code = returnMap.get("return_code");
            if (WXPayConstants.SUCCESS.equals(return_code)) {
                String appId = returnMap.get("appid");
                String mchId = returnMap.get("mch_id");
                String resultCode = returnMap.get("result_code");


                WxPayReturn wxPayReturn = new WxPayReturn();
                wxPayReturn.setResultCode(rechargeCode);
                wxPayReturn.setAppId(appId);
                wxPayReturn.setMchId(mchId);
                /* 验签 */
                if (WXPayUtil.isSignatureValid(returnMap, WX_KEY)) {


                    if (WX_APPID.equals(appId) && WX_MCH_ID.equals(mchId) && "SUCCESS".equals(resultCode)) {
                        String tradeState = returnMap.get("trade_state");// 交易状态
                        String totalFee = returnMap.get("total_fee");// 总金额
                        String outTradeNo = returnMap.get("out_trade_no");// 商户订单号
                        String tradeStateDesc = returnMap.get("trade_state_desc");// 交易状态描述
                        String wxTransactionId = returnMap.get("transaction_id");
                        wxPayReturn.setTradeState(tradeState);
                        wxPayReturn.setTotalFee(totalFee);
                        wxPayReturn.setOutTradeNo(outTradeNo);
                        wxPayReturn.setOutTradeNo(tradeStateDesc);
                        wxPayReturn.setTransactionId(wxTransactionId);
                        wxPayReturnService.insert(wxPayReturn);


                        //3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
                        if (!mchId.equals(WX_MCH_ID)) {
                            return writeMessage(400, "商户id失败,请稍后刷新重试！");
                        }
                        //4、验证app_id是否为该商户本身。
                        if (!appId.equals(WX_APPID)) {
                            return writeMessage(400, "appid验证失败,请稍后刷新重试！");
                        }

                        /* 状态成功  */
                        if ("SUCCESS".equals(tradeState)) {
                            if (orderInfoService.paySuccessOrderInfoPayStatus((byte) 1, outTradeNo, totalFee)) {//成功
                                if (orderInfoService.paySuccessUpdateUserAccount(outTradeNo)) {
                                    Double totalFeeD = (Double.parseDouble(totalFee) / 100);
                                    return writeMessage(200, "支付成功！", totalFeeD.toString());
                                } else {
                                    return writeMessage(400, "更新用户信息失败，请稍后查询支付结果！");
                                }

                            } else {//出错
                                return writeMessage(400, "同步更新充值结果出错，请稍后查询支付结果！");
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return writeMessage(400, e.getMessage());
        }
        return writeMessage(400, "未知错误！");
    }


    public Map writeMessage(Integer msg, String msgBox) {
        return writeMessage(msg, msgBox, "");

    }

    public Map writeMessage(Integer msg, String msgBox, String totalAmount) {
        Map map = new HashMap();
        map.put("msg", msg);
        map.put("msgBox", msgBox);
        map.put("totalAmount", totalAmount);
        return map;

    }
}
