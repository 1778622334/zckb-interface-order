package cn.gydata.zckb.zckbinterfaceorder.vo;

import lombok.Data;

/**
 * @ClassName ReturnPayInfoVo
 * @Description TODO
 * @Author yangqiyi
 * @Date 2019-11-15 15:41
 * @VERSION 1.0
 **/
@Data
public class ReturnPayInfoVo {
    private Integer code;
    private String msg;
    private String data;
    private String appId;
    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String timestamp;
    private String packages;
    private String sign;
    private String rechargeCode;
}
