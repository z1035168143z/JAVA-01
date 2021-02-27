/** 用户信息表 **/
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL,
  `open_id` varchar(32) CHARACTER SET latin1 NOT NULL COMMENT '对外id',
  `user_name` varchar(24) CHARACTER SET latin1 NOT NULL COMMENT '用户名称',
  `id_no_encrypt` varchar(18) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '用户身份证',
  `login_name` varchar(255) CHARACTER SET latin1 NOT NULL COMMENT '登录名称',
  `password_encrypt` varchar(255) CHARACTER SET latin1 NOT NULL COMMENT '登录密码',
  `phone_encrypt` varchar(11) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '邮箱',
  `version_id` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_open_id` (`open_id`),
  KEY `idx_login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


/** 商品表 **/
CREATE TABLE `t_product` (
  `id` int(11) NOT NULL COMMENT '商品id',
  `product_name` varchar(64) CHARACTER SET latin1 NOT NULL COMMENT '商品名称',
  `product_desc` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '商品详情',
  `stock_num` int(11) NOT NULL COMMENT '商品数量',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `is_in_stock` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否在售',
  `product_category` int(11) NOT NULL COMMENT '商品类别',
  `shipping` decimal(10,2) NOT NULL COMMENT '运费',
  `is_create_snapshot` tinyint(1) NOT NULL COMMENT '是否生成快照',
  `version_id` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
  `is_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/** 订单表 **/
CREATE TABLE `t_order` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;