package cn.gydata.zckb.zckbinterfaceorder.controller;

import cn.gydata.zckb.zckbinterfaceorder.handle.ResponseHandle;
import cn.gydata.zckb.zckbinterfaceorder.service.WeiXinPayService;
import cn.gydata.zckb.zckbinterfaceorder.util.ConverterUtils;
import cn.gydata.zckb.zckbinterfaceorder.util.IpUtils;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName WxPayReturnController
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-17 12:25
 * @VERSION 1.0
 **/
@RestController

@Api(value = "微信回调",description = "微信回调")
@RequestMapping(value = "return")
public class WxPayReturnController {

    @Autowired
    private WeiXinPayService weiXinPayService;

    @ApiIgnore
    @ApiOperation(value = "微信支付异步回调")
    @PostMapping("wxPayNotify")
    public void wxPayNotify(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        try {

            BufferedReader reader = null;
            reader = request.getReader();
            String line = "";
            String xmlString = null;
            StringBuffer inputString = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            xmlString = inputString.toString();
            request.getReader().close();

            //xmlString = "<xml><appid><![CDATA[wxe721041429692efe]]></appid> <bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><device_info><![CDATA[WEB]]></device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1430982002]]></mch_id><nonce_str><![CDATA[skgq820bthkpj3vn490sdkp0889sam]]></nonce_str><openid><![CDATA[oZeKrxJN4Lbh6vRHnJINvHsuGNiI]]></openid><out_trade_no><![CDATA[Z201904241712218624]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[B3B1D7637E424799FED6D12A1827A119]]></sign><time_end><![CDATA[20190424171331]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[4200000310201904240092862812]]></transaction_id></xml>";
            if ("".equals(xmlString)) {
                return;
            }


            Map<String, String> map = WXPayUtil.xmlToMap(xmlString);

            Boolean result = weiXinPayService.wxPayNotify(map);
            if (result) {
                responseSuccess(response);
            } else {

            }
        } catch (Exception e) {
        }


    }


    @ApiOperation(value = "微信支付通知验证")
    @PostMapping("app/wxPayReturn")
    public Map<String, Object> wxPayReturn(
            @RequestParam(value = "rechargeCode", defaultValue = "", required = false) String rechargeCode,
            @RequestParam(value = "transaction_id", defaultValue = "", required = false) String transaction_id,
            HttpServletRequest request
    ) {


//        String clientIp = IpUtils.getIpAddr(request);
//        Integer userId = ConverterUtils.toInt(request.getAttribute("userId"));
//        Integer isLogin = ConverterUtils.toInt(request.getAttribute("isLogin"));


        if (org.apache.commons.lang3.StringUtils.isBlank(rechargeCode) && StringUtils.isBlank(transaction_id)) {
            return ResponseHandle.Error("参数传递有误！");
        }

        String logContent = ",请求参数如下rechargeCode:" + rechargeCode + ",transaction_id" + transaction_id + ",时间:" + new Date().getTime();

        try {
            Map result = weiXinPayService.weixinPayReturn(rechargeCode, transaction_id);
            if (result == null) {
                logContent = "验证出错" + logContent;
                return ResponseHandle.Error("验证出错");
            } else {
                if (result.get("msg").toString().equals("400")) {
                    logContent = "支付验证失败" + logContent;
                    return ResponseHandle.Error(result.get("msgBox").toString());
                } else if (result.get("msg").toString().equals("200")) {
                    logContent = "支付验证成功" + logContent;
                    Map map = result;// user_info_service.getUserAllInfoByUserId(userId);
                    if (map == null) {
                        return ResponseHandle.Success("充值成功", 3, null, null);
                    } else {
                        Map map1 = ResponseHandle.Success(map, 2, null, null);
                        map1.put("msgBox", "充值成功");
                        return map1;
                    }

                }
            }

        } catch (Exception e) {
        }
        logContent = "支付验证出错" + logContent;
        return ResponseHandle.Error("验证出错");


    }

    public void responseSuccess(HttpServletResponse response) {

        try {
            response.setContentType("text/xml");
            response.setCharacterEncoding("UTF-8");
            PrintWriter localPrintWriter = response.getWriter();
            String successStr = "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
            localPrintWriter.write(successStr);
            localPrintWriter.flush();
            localPrintWriter.close();
        } catch (Exception e) {
        }

    }

}
