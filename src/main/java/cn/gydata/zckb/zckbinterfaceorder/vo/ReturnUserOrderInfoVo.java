package cn.gydata.zckb.zckbinterfaceorder.vo;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderInfo;
import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ReturnUserOrderInfoVo
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-21 16:39
 * @VERSION 1.0
 **/
@Data
public class ReturnUserOrderInfoVo {


    private OrderInfo orderInfo;

    private List<OrderItem> orderItems;

}
