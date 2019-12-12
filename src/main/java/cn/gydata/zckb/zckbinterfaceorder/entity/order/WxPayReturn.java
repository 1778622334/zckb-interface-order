package cn.gydata.zckb.zckbinterfaceorder.entity.order;

import javax.persistence.*;

@Table(name = "wx_pay_return")
public class WxPayReturn {
    @Id
    @Column(name = "wx_pay_return_id")
    @GeneratedValue(generator = "JDBC")
    private Long wxPayReturnId;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "mch_id")
    private String mchId;

    @Column(name = "result_code")
    private String resultCode;

    @Column(name = "trade_state")
    private String tradeState;

    @Column(name = "total_fee")
    private String totalFee;

    @Column(name = "out_trade_no")
    private String outTradeNo;

    @Column(name = "trade_state_desc")
    private String tradeStateDesc;

    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * @return wx_pay_return_id
     */
    public Long getWxPayReturnId() {
        return wxPayReturnId;
    }

    /**
     * @param wxPayReturnId
     */
    public void setWxPayReturnId(Long wxPayReturnId) {
        this.wxPayReturnId = wxPayReturnId;
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
     * @return mch_id
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * @param mchId
     */
    public void setMchId(String mchId) {
        this.mchId = mchId == null ? null : mchId.trim();
    }

    /**
     * @return result_code
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    /**
     * @return trade_state
     */
    public String getTradeState() {
        return tradeState;
    }

    /**
     * @param tradeState
     */
    public void setTradeState(String tradeState) {
        this.tradeState = tradeState == null ? null : tradeState.trim();
    }

    /**
     * @return total_fee
     */
    public String getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalFee
     */
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee == null ? null : totalFee.trim();
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
     * @return trade_state_desc
     */
    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    /**
     * @param tradeStateDesc
     */
    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc == null ? null : tradeStateDesc.trim();
    }

    /**
     * @return transaction_id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }
}