create table `customer_pack_price_detail`(
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',

  `pack_id` int default null comment '订单id',
  `order_number` varchar(50) default null comment '订单号',

  `freight` decimal(10, 2) default null comment '运费',
  `freight_discount` DOUBLE(8, 2) default null comment '运费折扣(只针对运费)',
  `discount` decimal(10, 2) default null comment '优惠价格(管理员修改)',
  `incidental` decimal(10, 2) default null comment '附加费用',
  `remote_fee` decimal(10, 2) default null comment '偏远费',
  `forklift_fee` decimal(10, 2) default null comment '叉车费',
  `sensitive_price` decimal(10, 2) default null comment '敏感货物附加费',
  `tariffs_price` decimal(10, 2) default null comment '关税金额',
  `insurance_price` decimal(10, 2) default null comment '保险费用',
  `coupons_price` decimal(10, 2) default null comment '优惠券金额',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '包裹费用明细表';