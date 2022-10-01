-- 渠道表
-- 是否需要关税
alter table `transportation_route` add column `is_tariffs` int default 2 comment '是否需要关税  1、是 0、否';
-- 关税比例
alter table `transportation_route` add column `tariffs` double(8,2) default null comment '关税%';
-- 是否有保险
alter table `transportation_route` add column `is_insurance` int default 2 comment '是否有保险 1、是 0、否';
-- 保险价格id
alter table `transportation_route` add column `insurance_id` int default null comment '保险id';

-- 保险基础信息表
create table `insurance`(
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `insurance_no` varchar(30) default null comment '保险编号',
  `insurance_name` varchar(50) default null comment '保险名称',
  `claims_detail` text default null comment '理赔详情',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '保险基础信息表';

create table `insurance_price`(
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
	`insurance_id` int DEFAULT NULL comment '保险基础信息id',
  `price` decimal(10,2) default null comment '保险费用',
  `ginseng_price` decimal(10,2) default null comment '参保价格',
  `claims_detail` text default null comment '理赔详情',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '保险表价格表';


-- TODO 待保险内容添加完成再执行
-- 订单表
-- 保价产品明细
create table `pack_valuation`(
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `pack_id` int default null comment '订单包裹id',
  `order_number` varchar(50) default null comment '订单号',
  `product_name` varchar(50) default null comment '产品名称',
  `unit_price` decimal(10,2) default null comment '单价',
  `number` double(8,2) default null comment '数量',
  `goods_value` decimal(10,2) default null comment '货值',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '保价产品明细';

-- 关税价格
alter table `customer_pack` add column `tariffs_price` decimal(10,2) default null comment '关税价格';

alter table `customer_pack` add column `insurance_price` decimal(10,2) default null comment '保险价格';

-- 订单保险费用明细
create table `pack_insurance_price`(
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `pack_id` int default null comment '订单包裹id',
  `order_number` varchar(50) default null comment '订单号',
  `insurance_id` int default null comment '保险Id',
  `insurance_name` varchar(50) default null comment '保险名称',
  `insurance_no` varchar(50) default null comment '保险编号',
  `claims_detail` text default null comment '理赔详情',
  `insurance_price_id` int default null comment '保险价格Id',
  `insurance_price` decimal(10,2) default null comment '保险价格',
  `price_claims_detail` text default null comment '价格理赔详情',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '订单保险费用明细';

alter table `pack_insurance_price` add column `ginseng_price` decimal(10,2) default null comment '参保价格';
