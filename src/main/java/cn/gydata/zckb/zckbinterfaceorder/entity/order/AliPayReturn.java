package cn.gydata.zckb.zckbinterfaceorder.entity.order;

import javax.persistence.*;

@Table(name = "ali_pay_return")
public class AliPayReturn {
    @Id
    @Column(name = "ali_pay_return_id")
    @GeneratedValue(generator = "JDBC")
    private Long aliPayReturnId;

    private String code;

    private String msg;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "auth_app_id")
    private String authAppId;

    @Column(name = "out_trade_no")
    private String outTradeNo;

    @Column(name = "total_amount")
    private String totalAmount;

    @Column(name = "trade_no")
    private String tradeNo;

    @Column(name = "seller_id")
    private String sellerId;

    /**
     * @return ali_pay_return_id
     */
    public Long getAliPayReturnId() {
        return aliPayReturnId;
    }

    /**
     * @param aliPayReturnId
     */
    public void setAliPayReturnId(Long aliPayReturnId) {
        this.aliPayReturnId = aliPayReturnId;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    /**
     * @return app_id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * @return auth_app_id
     */
    public String getAuthAppId() {
        return authAppId;
    }

    /**
     * @param authAppId
     */
    public void setAuthAppId(String authAppId) {
        this.authAppId = authAppId == null ? null : authAppId.trim();
    }

    /**
     * @return out_trade_no
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * @param outTradeNo
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
     * @return total_amount
     */
    public String getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount
     */
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount == null ? null : totalAmount.trim();
    }

    /**
     * @return trade_no
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * @param tradeNo
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    /**
     * @return seller_id
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }
}