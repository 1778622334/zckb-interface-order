package cn.gydata.zckb.zckbinterfaceorder.enums;

import lombok.Getter;
import lombok.Setter;

public enum CodeEnum {
    SUCCESS(0,"成功"),
    ERROR(-1,"系统错误"),
    VALIDATEERROR(-2,"校验失败"),
    LOGINERROR(-10,"获取登录信息失败");

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String message;

    CodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
