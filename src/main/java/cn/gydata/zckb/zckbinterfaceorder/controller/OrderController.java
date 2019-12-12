package cn.gydata.zckb.zckbinterfaceorder.controller;

import cn.gydata.zckb.zckbinterfaceorder.handle.RequestHandle;
import cn.gydata.zckb.zckbinterfaceorder.handle.ResponseHandle;
import cn.gydata.zckb.zckbinterfaceorder.service.AliPayService;
import cn.gydata.zckb.zckbinterfaceorder.service.WeiXinPayService;
import cn.gydata.zckb.zckbinterfaceorder.util.ConverterUtils;
import cn.gydata.zckb.zckbinterfaceorder.util.IpUtils;
import cn.gydata.zckb.zckbinterfaceorder.util.MapObjUtil;
import cn.gydata.zckb.zckbinterfaceorder.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ShopController
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-14 09:30
 * @VERSION 1.0
 **/
@Api(value = "订单", description = "订单")
@RestController
public class OrderController {

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private WeiXinPayService weiXinPayService;

    @ApiOperation(value = "发起阿里支付")
    @PostMapping("app/aliPayGetInfo")
    public Map<String, Object> aliPayGetInfo(
            @ApiParam("商品Id（多个商品逗号分割）") @RequestParam(value = "shopId") Long shopId,
            @ApiParam("设备类型（1、android，2、ios）") @RequestParam(value = "deviceType") Integer deviceType,
            @ApiParam("会员类型（1、个人会员，2、企业会员）") @RequestParam(value = "vipType") Integer vipType,
            @ApiParam("需开通的省份，逗号分割") @RequestParam(value = "memberGroup") String memberGroup,
            @ApiParam("是否升级") @RequestParam(value = "isUpgrade") Boolean isUpgrade,
            HttpServletRequest request
    ) {
        Map mapAppParms = RequestHandle.getPubParm(request);
        Long userId = ConverterUtils.toLong(mapAppParms.get("userId"));
        Integer isLogin = ConverterUtils.toInt(mapAppParms.get("isLogin"));

        if (isLogin == 0)
            return ResponseHandle.Error("登陆失效，请重新登陆");

        String clientIp = IpUtils.getIpAddr(request);
        try{
            Map result = MapObjUtil.entityToMap(aliPayService.getPayInfo(userId,vipType,memberGroup,shopId,deviceType,clientIp,isUpgrade));
            return ResponseHandle.Success(result,2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "发起微信支付")
    @PostMapping("app/wxPayGetInfo")
    public Map<String, Object> wxPayGetInfo(
            @ApiParam("商品Id（多个商品逗号分割）") @RequestParam(value = "shopId") Long shopId,
            @ApiParam("设备类型（1、android，2、ios）") @RequestParam(value = "deviceType") Integer deviceType,
            @ApiParam("会员类型（1、个人会员，2、企业会员）") @RequestParam(value = "vipType") Integer vipType,
            @ApiParam("需开通的省份，逗号分割") @RequestParam(value = "memberGroup") String memberGroup,
            @ApiParam("是否升级") @RequestParam(value = "isUpgrade") Boolean isUpgrade,
            HttpServletRequest request

    ) {
        Map mapAppParms = RequestHandle.getPubParm(request);
        Long userId = ConverterUtils.toLong(mapAppParms.get("userId"));
        Integer isLogin = ConverterUtils.toInt(mapAppParms.get("isLogin"));

        if (isLogin == 0)
            return ResponseHandle.Error("登陆失效，请重新登陆");

        String clientIp = IpUtils.getIpAddr(request);
        try{
            return ResponseHandle.Success(MapObjUtil.entityToMap(weiXinPayService.getPayInfo(userId,vipType,memberGroup,shopId,deviceType,clientIp, isUpgrade)),2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new HashMap<>();
    }



}
