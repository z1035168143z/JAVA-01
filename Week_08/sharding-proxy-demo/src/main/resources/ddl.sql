CREATE TABLE `t_order_1` (
  `order_no` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '订单编号',
  `owner_id` int(11) NOT NULL COMMENT '下单人id',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `receipt_address_id` int(11) NOT NULL COMMENT '收货地址id',
  `order_amount` decimal(10,2) NOT NULL COMMENT '订单金额',
  `status` varchar(16) NOT NULL COMMENT '订单状态(待支付、已支付、已取消、已退款、已收货)',
  `order_pay_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '支付时间',
  `delete_status` varchar(16) NOT NULL COMMENT '删除状态(未删除、已删除、彻底删除)',
  `order_snapshot` int(11) NOT NULL COMMENT '订单快照',
  `version_id` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_no`),
  KEY `idx_owner_id` (`owner_id`)
);
create table `t_order_2` like `t_order_1`;
create table `t_order_3` like `t_order_1`;
create table `t_order_4` like `t_order_1`;
create table `t_order_5` like `t_order_1`;
create table `t_order_6` like `t_order_1`;
create table `t_order_7` like `t_order_1`;
create table `t_order_8` like `t_order_1`;
create table `t_order_9` like `t_order_1`;
create table `t_order_10` like `t_order_1`;
create table `t_order_11` like `t_order_1`;
create table `t_order_12` like `t_order_1`;
create table `t_order_13` like `t_order_1`;
create table `t_order_14` like `t_order_1`;
create table `t_order_15` like `t_order_1`;
create table `t_order_16` like `t_order_1`;