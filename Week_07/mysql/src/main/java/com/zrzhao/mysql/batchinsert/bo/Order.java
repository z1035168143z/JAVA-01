package com.zrzhao.mysql.batchinsert.bo;

import java.math.BigDecimal;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
public class Order {

    /**
     *   `order_no` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '订单编号',
     *   `owner_id` int(11) NOT NULL COMMENT '下单人id',
     *   `product_id` int(11) NOT NULL COMMENT '商品id',
     *   `receipt_address_id` int(11) NOT NULL COMMENT '收货地址id',
     *   `order_amount` decimal(10,2) NOT NULL COMMENT '订单金额',
     *   `status` varchar(16) NOT NULL COMMENT '订单状态(待支付、已支付、已取消、已退款、已收货)',
     *   `order_pay_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '支付时间',
     *   `delete_status` varchar(16) NOT NULL COMMENT '删除状态(未删除、已删除、彻底删除)',
     *   `order_snapshot` int(11) NOT NULL COMMENT '订单快照',
     *   `version_id` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
     *   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     *   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     */

    private String orderNo;
    private Integer ownerId;
    private Integer productId;
    private Integer receiptAddressId;
    private BigDecimal orderAmount;
    private String status;
    private Integer orderSnapshot;

    public Order() {
    }

    public Order(String orderNo, Integer ownerId, Integer productId, Integer receiptAddressId, BigDecimal orderAmount, String status, Integer orderSnapshot) {
        this.orderNo = orderNo;
        this.ownerId = ownerId;
        this.productId = productId;
        this.receiptAddressId = receiptAddressId;
        this.orderAmount = orderAmount;
        this.status = status;
        this.orderSnapshot = orderSnapshot;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getReceiptAddressId() {
        return receiptAddressId;
    }

    public void setReceiptAddressId(Integer receiptAddressId) {
        this.receiptAddressId = receiptAddressId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrderSnapshot() {
        return orderSnapshot;
    }

    public void setOrderSnapshot(Integer orderSnapshot) {
        this.orderSnapshot = orderSnapshot;
    }
}
