package cn.gydata.zckb.zckbinterfaceorder.controller;

import cn.gydata.zckb.zckbinterfaceorder.handle.ResponseHandle;
import cn.gydata.zckb.zckbinterfaceorder.service.AliPayService;
import cn.gydata.zckb.zckbinterfaceorder.util.ConverterUtils;
import cn.gydata.zckb.zckbinterfaceorder.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName PayReturnController
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-17 09:50
 * @VERSION 1.0
 **/
@RestController
@Api(value = "支付宝回调", description = "支付宝回调")
@RequestMapping(value = "return")
public class AliPayReturnController {

    @Autowired
    private AliPayService aliPayService;

    @ApiIgnore
    @ApiOperation(value = "阿里支付异步回调")
    @PostMapping("aliPayNotify")
    public String aliPayNotify(
            HttpServletRequest request
    ) {

        try {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();


            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化//
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }


            boolean signVerified = aliPayService.rsaCheckV1(params);
            //——请在这里编写您的程序（以下代码仅作参考）——
            if (signVerified) {
                return aliPayService.aliPayNotify(request);
            } else {
                return "fail";
            }
            //——请在这里编写您的程序（以上代码仅作参考）——
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }


    @ApiOperation(value = "阿里支付通知验证")
    @PostMapping("app/aliPayReturn")
    public Map<String, Object> aliPayReturn(
            @ApiParam("支付结果") @RequestParam(value = "payresult") String payresult,
            HttpServletRequest request
    ) {
//        String clientIp = IpUtils.getIpAddr(request);
//        Integer userId = ConverterUtils.toInt(request.getAttribute("userId"));
//        Integer isLogin = ConverterUtils.toInt(request.getAttribute("isLogin"));

        try {
            Map result = aliPayService.PayReturn(payresult);
            if (result == null) {
                return ResponseHandle.Error("验证出错");
            } else {
                if (result.get("msg").toString().equals("400")) {
                    return ResponseHandle.Error(result.get("msgBox").toString());
                } else if (result.get("msg").toString().equals("200")) {
                    Map map = result;// user_info_service.getUserAllInfoByUserId(userId);
                    if (map != null) {
                        return ResponseHandle.Success(map, 2, null, null);
                    }
                }
            }

        } catch (Exception e) {
        }
        return ResponseHandle.Error("验证出错");


    }
}
