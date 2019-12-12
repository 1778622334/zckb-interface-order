package cn.gydata.zckb.zckbinterfaceorder.entity.order;

import javax.persistence.*;

@Table(name = "order_info")
public class OrderInfo {
    /**
     * 订单Id
     */
    @Id
    @Column(name = "order_id")
    @GeneratedValue(generator = "JDBC")
    private Long orderId;

    /**
     * 订单号（唯一）
     */
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * 购买者Id
     */
    @Column(name = "buy_user_id")
    private Long buyUserId;

    /**
     * 交易状态
0:进行中
1:已完成
2:取消交易
3:结算

     */
    @Column(name = "tarde_status")
    private Byte tardeStatus;

    /**
     * 支付状态
1:未付款
2:已付款
3:线下付款
4:线下付款已付款

     */
    @Column(name = "pay_status")
    private Byte payStatus;

    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private Integer orderAmount;

    /**
     * 付款金额
     */
    @Column(name = "pay_amount")
    private Integer payAmount;

    /**
     * 实际付款金额
     */
    @Column(name = "total_amount")
    private Integer totalAmount;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Long payTime;

    /**
     * 支付类型
11:app支付宝
12:app微信
21:web支付宝
22:web微信
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 第三方交易单号
     */
    @Column(name = "outer_trade_no")
    private String outerTradeNo;

    /**
     * 订单创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * 订单最后修改时间
     */
    @Column(name = "update_time")
    private Long updateTime;

    /**
     * 删除状态
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    @Column(name = "app_key")
    private String appKey;

    /**
     * 备注
     */
    private String remark;

    @Column(name = "invoice_id")
    private Long invoiceId;

    /**
     * 获取订单Id
     *
     * @return order_id - 订单Id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单Id
     *
     * @param orderId 订单Id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单号（唯一）
     *
     * @return order_number - 订单号（唯一）
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置订单号（唯一）
     *
     * @param orderNumber 订单号（唯一）
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    /**
     * 获取购买者Id
     *
     * @return buy_user_id - 购买者Id
     */
    public Long getBuyUserId() {
        return buyUserId;
    }

    /**
     * 设置购买者Id
     *
     * @param buyUserId 购买者Id
     */
    public void setBuyUserId(Long buyUserId) {
        this.buyUserId = buyUserId;
    }

    /**
     * 获取交易状态
0:进行中
1:已完成
2:取消交易
3:结算

     *
     * @return tarde_status - 交易状态
0:进行中
1:已完成
2:取消交易
3:结算

     */
    public Byte getTardeStatus() {
        return tardeStatus;
    }

    /**
     * 设置交易状态
0:进行中
1:已完成
2:取消交易
3:结算

     *
     * @param tardeStatus 交易状态
0:进行中
1:已完成
2:取消交易
3:结算

     */
    public void setTardeStatus(Byte tardeStatus) {
        this.tardeStatus = tardeStatus;
    }

    /**
     * 获取支付状态
1:未付款
2:已付款
3:线下付款
4:线下付款已付款

     *
     * @return pay_status - 支付状态
1:未付款
2:已付款
3:线下付款
4:线下付款已付款

     */
    public Byte getPayStatus() {
        return payStatus;
    }

    /**
     * 设置支付状态
1:未付款
2:已付款
3:线下付款
4:线下付款已付款

     *
     * @param payStatus 支付状态
1:未付款
2:已付款
3:线下付款
4:线下付款已付款

     */
    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取订单金额
     *
     * @return order_amount - 订单金额
     */
    public Integer getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置订单金额
     *
     * @param orderAmount 订单金额
     */
    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * 获取付款金额
     *
     * @return pay_amount - 付款金额
     */
    public Integer getPayAmount() {
        return payAmount;
    }

    /**
     * 设置付款金额
     *
     * @param payAmount 付款金额
     */
    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 获取实际付款金额
     *
     * @return total_amount - 实际付款金额
     */
    public Integer getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置实际付款金额
     *
     * @param totalAmount 实际付款金额
     */
    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Long getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取支付类型
11:app支付宝
12:app微信
21:web支付宝
22:web微信
     *
     * @return pay_type - 支付类型
11:app支付宝
12:app微信
21:web支付宝
22:web微信
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * 设置支付类型
11:app支付宝
12:app微信
21:web支付宝
22:web微信
     *
     * @param payType 支付类型
11:app支付宝
12:app微信
21:web支付宝
22:web微信
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * 获取第三方交易单号
     *
     * @return outer_trade_no - 第三方交易单号
     */
    public String getOuterTradeNo() {
        return outerTradeNo;
    }

    /**
     * 设置第三方交易单号
     *
     * @param outerTradeNo 第三方交易单号
     */
    public void setOuterTradeNo(String outerTradeNo) {
        this.outerTradeNo = outerTradeNo == null ? null : outerTradeNo.trim();
    }

    /**
     * 获取订单创建时间
     *
     * @return create_time - 订单创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 设置订单创建时间
     *
     * @param createTime 订单创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取订单最后修改时间
     *
     * @return update_time - 订单最后修改时间
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置订单最后修改时间
     *
     * @param updateTime 订单最后修改时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取删除状态
     *
     * @return is_delete - 删除状态
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置删除状态
     *
     * @param isDelete 删除状态
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * @return app_key
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }
}