package cn.gydata.zckb.zckbinterfaceorder.enums;


public enum PayTypeEnum {
    AliPay(1,"支付宝"),
    WxPay(2,"微信"),
    PingAnAliPay(31,"平安支付宝"),
    PingAnWxPay(32,"平安微信"),
    CashierQuickPay(41,"平安快捷支付");

    private Integer code;

    private String title;

    public void setCode(Integer code) {
        this.code = code;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    PayTypeEnum(Integer code, String title){
        this.code = code;
        this.title = title;
    }
}
