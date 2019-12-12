package cn.gydata.zckb.zckbinterfaceorder.service;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.AliPayReturn;
import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderInfo;
import cn.gydata.zckb.zckbinterfaceorder.enums.PayTypeEnum;
import cn.gydata.zckb.zckbinterfaceorder.service.shop_server.ShopServer;
import cn.gydata.zckb.zckbinterfaceorder.service.user_server.UserServer;
import cn.gydata.zckb.zckbinterfaceorder.util.ConverterUtils;
import cn.gydata.zckb.zckbinterfaceorder.vo.ReturnPayInfoVo;
import cn.gydata.zckb.zckbinterfaceorder.vo.ReturnOrderInfoVo;
import cn.gydata.zckb.zckbinterfaceorder.vo.UserVo;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class AliPayService {

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private ShopServer shopServer;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private UserServerService userServerService;

    @Autowired
    private AliPayReturnService aliPayReturnService;

    @Value("${alipay.pid}")
    private String ALI_PID;


    @Value("${alipay.key}")
    private String ALI_KEY;

    @Value("${alipay.appid}")
    private String ALI_APPID;

    @Value("${alipay.rsa_private_key}")
    private String ALI_RSA_PRIVATE_KEY;

    @Value("${alipay.ali_public_key}")
    private String ALI_PUBLIC_KEY;

    @Value("${alipay.gateway}")
    private String ALI_GATEWAY;

    @Value("${alipay.input_charset}")
    private String ALI_INPUT_CHARSET;

    @Value("${alipay.sign_type}")
    private String ALI_SIGN_TYPE;

    @Value("${alipay.appnotify_url}")
    private String ALI_APPNOTIFY_URL;

    @Value("${alipay.seller_email}")
    private String ALI_SELLER_EMAIL;


    /**
     * 获取支付宝支付信息
     *
     * @param userId
     * @param memberGroup
     * @param shopId
     * @param deviceType
     * @return
     */
    public ReturnPayInfoVo getPayInfo(Long userId, Integer vipType, String memberGroup, Long shopId, Integer deviceType, String clientIp,Boolean isUpgrade) {
        ReturnPayInfoVo returnPayInfoVo;


        UserVo userVo = userServerService.getUserAccount(userId);


        returnPayInfoVo = isPayCompanyVip(userVo, shopId, deviceType, memberGroup);

        if (returnPayInfoVo.getCode() == 200 && returnPayInfoVo.getMsg().equals("ok")) {

            ReturnOrderInfoVo returnOrderInfoVo = orderInfoService.produceMainOrder(userVo.getUserId(), vipType, PayTypeEnum.AliPay.getCode(), deviceType, shopId, userVo, memberGroup,isUpgrade);
            if (returnOrderInfoVo.getOrderInfo() != null) {
                AlipayClient alipayClient = new DefaultAlipayClient(ALI_GATEWAY, ALI_APPID, ALI_RSA_PRIVATE_KEY, "json", ALI_INPUT_CHARSET, ALI_PUBLIC_KEY, ALI_SIGN_TYPE);


                // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
                AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
                // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。

                AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                model.setBody(returnOrderInfoVo.getOrderInfo().getRemark()); //商品描述
                model.setSubject("政策快报"); // 商品标题

                Double payAmount = returnOrderInfoVo.getOrderInfo().getPayAmount() / 100.0;

                model.setOutTradeNo(returnOrderInfoVo.getOrderInfo().getOrderNumber()); //订单号
                model.setTimeoutExpress("90m");//	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。注：若为空，则默认为15d。
                model.setTotalAmount(payAmount.toString());//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
                model.setProductCode("QUICK_MSECURITY_PAY");//销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
                request.setBizModel(model);
                request.setNotifyUrl(ALI_APPNOTIFY_URL);//支付宝异步调用后台的url

                // 这里和普通的接口调用不同，使用的是sdkExecute
                try {
                    AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
                    returnPayInfoVo.setCode(200);
                    returnPayInfoVo.setMsg(response.getBody());
                } catch (AlipayApiException e) {
                    e.printStackTrace();
                    returnPayInfoVo.setCode(400);
                    returnPayInfoVo.setMsg(e.getErrMsg());
                }
            } else {
                returnPayInfoVo.setCode(400);
                returnPayInfoVo.setMsg("订单不存在!");
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
     * @param params
     * @return
     */
    public Boolean rsaCheckV1(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(params, ALI_PUBLIC_KEY, "utf-8", "RSA"); //调用SDK验证签名
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public String aliPayNotify(
            HttpServletRequest request) {
//            商户订单号
        String outTradeNo = request.getParameter("out_trade_no").toString();
        //付款金额
        String total_amount = request.getParameter("total_amount").toString();
        String sellerId = request.getParameter("seller_id").toString();
        String appId = request.getParameter("app_id").toString();
//                String msg =request.getParameter("msg").toString();
//                String auth_app_id =request.getParameter("auth_app_id").toString();
//                String charset =request.getParameter("charset").toString();
//                String timestamp =request.getParameter("timestamp").toString();
//                String code =request.getParameter("code").toString();
        String tradeNo = request.getParameter("trade_no").toString();
        String tradeStatus = request.getParameter("trade_status").toString();

        //1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNumber(outTradeNo);
        if (orderInfo == null) {
            return "fail";
        }
        //2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；
        String totalFee = String.valueOf(orderInfo.getOrderAmount() / 100.0);
//                if(total_amount.equals(total_fee) && total_amount.equals(String.valueOf(userRecharge.getRechargemoney())) ){
//                    return writeMessage(1,"订单信息不一致,请稍后刷新重试！");
//                }
        //3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）；
        Boolean bl = ALI_PID.equals(sellerId);
        if (!ALI_PID.equals(sellerId)) {
            return "fail";
        }
        //4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明同步校验结果是无效的，只有全部验证通过后，才可以认定买家付款成功。
        if (!appId.equals(ALI_APPID)) {
            return "fail";
        }

        //上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
        if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
            if (orderInfoService.paySuccessOrderInfoPayStatus((byte) 1, outTradeNo, totalFee)) {//成功

                if (orderInfoService.paySuccessUpdateUserAccount(outTradeNo)) {
                    return "success";
                } else { // 更新用户信息失败
                    return "fail";
                }
            } else {//出错
                return "fail";
            }
        } else {
            if (tradeStatus.equals("TRADE_CLOSED")) {
                if (orderInfoService.paySuccessOrderInfoPayStatus((byte) 2, outTradeNo, totalFee)) {//成功
                    return "success";
                } else {//出错
                    return "fail";
                }
            }


        }
        return "fail";
    }


    public Map PayReturn(String payresult) {

        Map mapResult = new HashMap();
        try {
            Map map = aliPayReturn(payresult);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            mapResult.put("msg", 400);
            mapResult.put("msgBox", e.getMessage());
        }
        return mapResult;
    }

    public Map aliPayReturn(String parms) {

        try {
            Map params1 = JSONObject.fromObject(parms);
            Map params = JSONObject.fromObject(params1.get("alipay_trade_app_pay_response"));//   new HashMap<String,String>();
            String sign = params1.get("sign").toString();

            boolean signVerified = AlipaySignature.rsaCheckContent(params1.get("alipay_trade_app_pay_response").toString(), sign, ALI_PUBLIC_KEY, "utf-8");

            //——请在这里编写您的程序（以下代码仅作参考）——
            if (signVerified) {


                String code = params.get("code").toString();
                String msg = params.get("msg").toString();
                String app_id = params.get("app_id").toString();
                String auth_app_id = params.get("auth_app_id").toString();

                //商户订单号
                String outTradeNo = params.get("out_trade_no").toString();
                //付款金额
                String totalAmount = params.get("total_amount").toString();
                String trade_no = params.get("trade_no").toString();
                String seller_id = params.get("seller_id").toString();

                AliPayReturn aliPayReturn = new AliPayReturn();
                aliPayReturn.setCode(code);
                aliPayReturn.setMsg(msg);
                aliPayReturn.setAppId(app_id);
                aliPayReturn.setAuthAppId(auth_app_id);
                aliPayReturn.setOutTradeNo(outTradeNo);
                aliPayReturn.setTotalAmount(totalAmount);
                aliPayReturn.setTradeNo(trade_no);
                aliPayReturn.setSellerId(seller_id);
                aliPayReturnService.insert(aliPayReturn);

                //1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号；
                OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNumber(outTradeNo);
                if (orderInfo == null) {
                    return writeMessage(400, "订单不存在,请稍后刷新重试！");
                }
                //2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）；
                Integer total_fee = orderInfo.getPayAmount();
                if (totalAmount.equals(total_fee) && totalAmount.equals(String.valueOf(orderInfo.getPayAmount()))) {
                    return writeMessage(1, "订单信息不一致,请稍后刷新重试！");
                }
                //3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）；
                if (!ALI_PID.equals(seller_id)) {
                    return writeMessage(400, "获取商户id失败,请稍后刷新重试！");
                }
                //4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明同步校验结果是无效的，只有全部验证通过后，才可以认定买家付款成功。
                if (!ALI_APPID.equals(app_id)) {
                    return writeMessage(400, "appid验证失败,请稍后刷新重试！");
                }

                if (msg.toLowerCase().equals("success") && code.equals("10000")) {
                    if (orderInfoService.paySuccessOrderInfoPayStatus((byte) 1, outTradeNo, totalAmount)) {//成功

                        if (orderInfoService.paySuccessUpdateUserAccount(outTradeNo)) {
                            return writeMessage(200, "支付成功！", totalAmount);
                        } else {
                            return aliPayService.writeMessage(400, "更新用户信息失败，请稍后查询支付结果！");
                        }
                    } else {//出错
                        return writeMessage(400, "同步更新充值结果出错，请稍后查询支付结果！");
                    }
                } else {
                    return writeMessage(400, "支付结果未知,请稍后刷新重试！");
                }


            } else {
                return writeMessage(400, "验签失败！");
            }
            //——请在这里编写您的程序（以上代码仅作参考）——
        } catch (Exception e) {
            e.printStackTrace();
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
