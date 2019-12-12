package cn.gydata.zckb.zckbinterfaceorder.vo;

import cn.gydata.zckb.zckbinterfaceorder.entity.order.OrderInfo;
import lombok.Data;

/**
 * @ClassName ReturnOrderInfoVo
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-16 11:04
 * @VERSION 1.0
 **/
@Data
public class ReturnOrderInfoVo {
    private Integer code;
    private String msg;
    private OrderInfo orderInfo;
}
