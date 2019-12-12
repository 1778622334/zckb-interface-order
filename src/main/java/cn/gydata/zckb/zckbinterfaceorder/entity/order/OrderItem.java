package cn.gydata.zckb.zckbinterfaceorder.entity.order;

import javax.persistence.*;

@Table(name = "order_item")
public class OrderItem {
    /**
     * 订单明细Id
     */
    @Id
    @Column(name = "item_id")
    @GeneratedValue(generator = "JDBC")
    private Long itemId;

    /**
     * 主订单Id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 订单编号（唯一）
     */
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * 订单状态
     * 1:以提交订单
     * 2:取消订单
     */
    @Column(name = "order_status")
    private Byte orderStatus;

    /**
     * 商品Id
     */
    @Column(name = "format_id")
    private Long formatId;

    /**
     * 购买方用户Id
     */
    @Column(name = "buy_user_id")
    private Long buyUserId;

    /**
     * 服务商用户Id
     */
    @Column(name = "seller_id")
    private Long sellerId;

    /**
     * 接收类型
     * 1:平台内接收通知
     * 2:邮箱接收通知
     * 3:手机电话接收通知
     * 4:手机短信接收通知
     */
    @Column(name = "delivery_type")
    private Byte deliveryType;

    /**
     * 服务商状态
     * 1:已接单
     * 2:项目服务中
     * 3:项目已完成
     */
    @Column(name = "delivery_status")
    private Byte deliveryStatus;

    /**
     * 买家状态
     * 1:已付款
     * 2:等待验收
     * 3:验收完成
     * 4:已评价
     */
    @Column(name = "buyer_status")
    private Byte buyerStatus;

    /**
     * 商品数量
     */
    @Column(name = "goods_number")
    private Integer goodsNumber;

    /**
     * 商品价格
     */
    @Column(name = "goods_price")
    private Integer goodsPrice;

    /**
     * 商品总价
     */
    @Column(name = "goods_amount")
    private Integer goodsAmount;

    /**
     * 订单创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * 服务商完成项目时间
     */
    @Column(name = "seller_finish_time")
    private Long sellerFinishTime;

    /**
     * 买家任务完成时间
     */
    @Column(name = "buyer_finish_time")
    private Long buyerFinishTime;

    /**
     * 特殊处理方式id
     */
    @Column(name = "method_id")
    private Integer methodId;

    /**
     * 订单修改时间
     */
    @Column(name = "update_time")
    private Long updateTime;

    /**
     * 应用key
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * 删除状态
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;


    /**
     * 订单支付成功后处理方式
     */
    @Column(name = "order_pay_success_handle")
    private String orderPaySuccessHandle;


    /**
     * 商品快照
     */
    @Column(name = "shop_snap_shot")
    private String shopSnapShot;

    /**
     * 获取订单明细Id
     *
     * @return item_id - 订单明细Id
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 设置订单明细Id
     *
     * @param itemId 订单明细Id
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取主订单Id
     *
     * @return order_id - 主订单Id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置主订单Id
     *
     * @param orderId 主订单Id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单编号（唯一）
     *
     * @return order_number - 订单编号（唯一）
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置订单编号（唯一）
     *
     * @param orderNumber 订单编号（唯一）
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    /**
     * 获取订单状态
     * 1:以提交订单
     * 2:取消订单
     *
     * @return order_status - 订单状态
     * 1:以提交订单
     * 2:取消订单
     */
    public Byte getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态
     * 1:以提交订单
     * 2:取消订单
     *
     * @param orderStatus 订单状态
     *                    1:以提交订单
     *                    2:取消订单
     */
    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取商品Id
     *
     * @return format_id - 商品Id
     */
    public Long getFormatId() {
        return formatId;
    }

    /**
     * 设置商品Id
     *
     * @param formatId 商品Id
     */
    public void setFormatId(Long formatId) {
        this.formatId = formatId;
    }

    /**
     * 获取购买方用户Id
     *
     * @return buy_user_id - 购买方用户Id
     */
    public Long getBuyUserId() {
        return buyUserId;
    }

    /**
     * 设置购买方用户Id
     *
     * @param buyUserId 购买方用户Id
     */
    public void setBuyUserId(Long buyUserId) {
        this.buyUserId = buyUserId;
    }

    /**
     * 获取服务商用户Id
     *
     * @return seller_id - 服务商用户Id
     */
    public Long getSellerId() {
        return sellerId;
    }

    /**
     * 设置服务商用户Id
     *
     * @param sellerId 服务商用户Id
     */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 获取接收类型
     * 1:平台内接收通知
     * 2:邮箱接收通知
     * 3:手机电话接收通知
     * 4:手机短信接收通知
     *
     * @return delivery_type - 接收类型
     * 1:平台内接收通知
     * 2:邮箱接收通知
     * 3:手机电话接收通知
     * 4:手机短信接收通知
     */
    public Byte getDeliveryType() {
        return deliveryType;
    }

    /**
     * 设置接收类型
     * 1:平台内接收通知
     * 2:邮箱接收通知
     * 3:手机电话接收通知
     * 4:手机短信接收通知
     *
     * @param deliveryType 接收类型
     *                     1:平台内接收通知
     *                     2:邮箱接收通知
     *                     3:手机电话接收通知
     *                     4:手机短信接收通知
     */
    public void setDeliveryType(Byte deliveryType) {
        this.deliveryType = deliveryType;
    }

    /**
     * 获取服务商状态
     * 1:已接单
     * 2:项目服务中
     * 3:项目已完成
     *
     * @return delivery_status - 服务商状态
     * 1:已接单
     * 2:项目服务中
     * 3:项目已完成
     */
    public Byte getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * 设置服务商状态
     * 1:已接单
     * 2:项目服务中
     * 3:项目已完成
     *
     * @param deliveryStatus 服务商状态
     *                       1:已接单
     *                       2:项目服务中
     *                       3:项目已完成
     */
    public void setDeliveryStatus(Byte deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * 获取买家状态
     * 1:已付款
     * 2:等待验收
     * 3:验收完成
     * 4:已评价
     *
     * @return buyer_status - 买家状态
     * 1:已付款
     * 2:等待验收
     * 3:验收完成
     * 4:已评价
     */
    public Byte getBuyerStatus() {
        return buyerStatus;
    }

    /**
     * 设置买家状态
     * 1:已付款
     * 2:等待验收
     * 3:验收完成
     * 4:已评价
     *
     * @param buyerStatus 买家状态
     *                    1:已付款
     *                    2:等待验收
     *                    3:验收完成
     *                    4:已评价
     */
    public void setBuyerStatus(Byte buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    /**
     * 获取商品数量
     *
     * @return goods_number - 商品数量
     */
    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * 设置商品数量
     *
     * @param goodsNumber 商品数量
     */
    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * 获取商品价格
     *
     * @return goods_price - 商品价格
     */
    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * 设置商品价格
     *
     * @param goodsPrice 商品价格
     */
    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * 获取商品总价
     *
     * @return goods_amount - 商品总价
     */
    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    /**
     * 设置商品总价
     *
     * @param goodsAmount 商品总价
     */
    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
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
     * 获取服务商完成项目时间
     *
     * @return seller_finish_time - 服务商完成项目时间
     */
    public Long getSellerFinishTime() {
        return sellerFinishTime;
    }

    /**
     * 设置服务商完成项目时间
     *
     * @param sellerFinishTime 服务商完成项目时间
     */
    public void setSellerFinishTime(Long sellerFinishTime) {
        this.sellerFinishTime = sellerFinishTime;
    }

    /**
     * 获取买家任务完成时间
     *
     * @return buyer_finish_time - 买家任务完成时间
     */
    public Long getBuyerFinishTime() {
        return buyerFinishTime;
    }

    /**
     * 设置买家任务完成时间
     *
     * @param buyerFinishTime 买家任务完成时间
     */
    public void setBuyerFinishTime(Long buyerFinishTime) {
        this.buyerFinishTime = buyerFinishTime;
    }

    /**
     * 获取特殊处理方式id
     *
     * @return method_id - 特殊处理方式id
     */
    public Integer getMethodId() {
        return methodId;
    }

    /**
     * 设置特殊处理方式id
     *
     * @param methodId 特殊处理方式id
     */
    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }

    /**
     * 获取订单修改时间
     *
     * @return update_time - 订单修改时间
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置订单修改时间
     *
     * @param updateTime 订单修改时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取应用key
     *
     * @return app_key - 应用key
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 设置应用key
     *
     * @param appKey 应用key
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
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

    public void setOrderPaySuccessHandle(String orderPaySuccessHandle) {
        this.orderPaySuccessHandle = orderPaySuccessHandle;
    }

    public String getOrderPaySuccessHandle() {
        return orderPaySuccessHandle;
    }

    public void setShopSnapShot(String shopSnapShot) {
        this.shopSnapShot = shopSnapShot;
    }

    public String getShopSnapShot() {
        return shopSnapShot;
    }
}