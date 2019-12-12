package cn.gydata.zckb.zckbinterfaceorder.controller;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderInfo;
import cn.gydata.zckb.zckbinterfaceorder.handle.RequestHandle;
import cn.gydata.zckb.zckbinterfaceorder.handle.ResponseHandle;
import cn.gydata.zckb.zckbinterfaceorder.service.OrderInfoService;
import cn.gydata.zckb.zckbinterfaceorder.util.ConverterUtils;
import cn.gydata.zckb.zckbinterfaceorder.util.IpUtils;
import cn.gydata.zckb.zckbinterfaceorder.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserOrderController
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-21 17:06
 * @VERSION 1.0
 **/
@Api(value = "订单", description = "订单")
@RestController
public class UserOrderController {

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "获取用户订单")
    @PostMapping("/getUserOrder")
    public Map<String, Object> getUserOrder(
            @ApiParam("页数") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam("每页数量") @RequestParam(value = "pageSize") Integer pageSize,
            HttpServletRequest request

    ) {
        Map mapAppParms = RequestHandle.getPubParm(request);
        Long userId = ConverterUtils.toLong(mapAppParms.get("userId"));
        Integer isLogin = ConverterUtils.toInt(mapAppParms.get("isLogin"));

        if (isLogin == 0)
            ResponseHandle.Error("登陆失效，请重新登陆");

        try {
            return ResponseHandle.Success(orderInfoService.getUserOrder(userId, pageNum, pageSize), 1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new HashMap<>();
    }


    @ApiIgnore
    @ApiOperation(value = "获取用户最后一笔订单")
    @PostMapping("/server/order/getUserLastOrder")
    public Long getUserLastOrder(
            @ApiParam("页数") @RequestParam(value = "userId") Long userId
    ) {
        return orderInfoService.getUserLastOrder(userId);
    }


    @ApiIgnore
    @ApiOperation(value = "获取用户未开票的订单")
    @PostMapping("/server/order/getNoInvoiceOrder")
    public List<OrderInfo> getNoInvoiceOrder(
            @RequestParam(value = "userId") Long userId
    ) {
        return orderInfoService.getNoInvoiceOrder(userId);
    }


    @ApiIgnore
    @ApiOperation(value = "修改用户订单开票状态")
    @PostMapping("/server/order/updateOrderInvoice")
    public boolean updateOrderInvoice(
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "invoiceId") String invoiceId
    ) {
        return orderInfoService.updateOrderInvoiceId(orderId,invoiceId);
    }
}
