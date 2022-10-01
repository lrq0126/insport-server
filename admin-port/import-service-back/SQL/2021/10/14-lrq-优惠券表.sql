-- 系统优惠券
create table `sys_coupons`(
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` int default 0 comment '创建人id',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default 0 comment '修改人id',
  `status` int default 1 comment '使用状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `is_enable` int default 1 comment '是否启用 1是 0否',
  `coupons_name` varchar(50) default null comment '优惠券名称',
  `coupons_type` int default null comment '优惠券类型 1、新人券 2、满减券 3、无门槛',
  `coupons_amount` decimal(10, 2) default null comment '金额',
  `minimum_amount` decimal(10, 2) default 0 comment '超过多少可用',
  `is_exchange` int default 0 comment '是否可兑换 1、是 0、否',
  `exchange_num` int default 0 comment '兑换所需积分',
  `overdue_type` int default 1 comment '过期类型， 1、固定日期过期， 2、固定天数过期',
  `time_limit` int default null comment '有效期限/天',
  `time_overdue` date default null comment '过期时间',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '优惠券表';

-- 客户拥有的优惠券
create table `customer_coupons`(
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '使用状态',
  `version` int default 1 comment '版本号',
  `give_id` int default -1 comment '分发人id',
  `customer_id` int default null comment '客户id',
  `sys_coupons_id` int default null comment '优惠券ID',
  `center_coupons_id` int default null comment '优惠券中心ID',
  `customer_group_id` int default null comment '客户分组ID',
  `coupons_name` varchar(50) default null comment '优惠券名称',
  `coupons_type` int default null comment '优惠券类型 1、新人券 2、满减券 3、无门槛',
  `coupons_amount` decimal(10, 2) default null comment '金额',
  `minimum_amount` decimal(10, 2) default 0 comment '超过多少可用',
  `coupons_status` int default 0 comment '优惠券状态 0、未使用 1、已使用 2、已过期',
  `used_time` datetime default null comment '使用时间',
  `used_order` varchar(50) default null comment '使用的订单',
  `time_overdue` date default null comment '过期时间',
  `coupons_img_url` varchar(200) default null comment '优惠券图片地址',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '客户优惠券表';

create table `customer_coupons_center`(
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` int default 0 comment '创建人id',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default 0 comment '修改人id',
  `status` int default 1 comment '使用状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `sys_coupons_id` int default null comment '系统优惠券id',
  `customer_group_id` int default null comment '客户分组的id',

  `coupons_name` varchar(50) default null comment '优惠券名称',
  `coupons_type` int default null comment '优惠券类型 1、新人券 2、满减券 3、无门槛',
  `coupons_amount` decimal(10, 2) default null comment '金额',
  `minimum_amount` decimal(10, 2) default 0 comment '超过多少可用',
  `overdue_type` int default 1 comment '过期类型， 1、固定日期过期， 2、固定天数过期',
  `time_limit` int default null comment '有效期限/天',
  `time_overdue` date default null comment '过期时间',

  `customer_get_num` int default null comment '领取人数',
  `customer_used_num` int default null comment '使用人数',
  `coupons_img_url` varchar(200) default null comment '优惠券图片地址',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '客户优惠券领券中心表';

insert into permission(`describe`, `level`, `sort_no`) values ('优惠券管理', 1, 12);