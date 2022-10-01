-- customer 表字段变更情况
  -- 新增2个字段
alter table `customer` add column `login_name` varchar(50) default null comment '登录名';
alter table `customer` add column `password` varchar(50) default null comment '密码';
  -- 修改字段长度
alter table `customer` modify `phone_number` varchar(50) default null comment '手机号码';


-- customer_address  表字段变更情况
  -- 修改字段长度
alter table `customer_address` modify `phone_number`  varchar(50) default null comment '收件人手机号码';
alter table `customer_address` modify `call_number` varchar(50) default null comment '收件人座机';
alter table `customer_address` modify `status` int(2) default 1 comment '状态  1、默认   0、以删除';
alter table `customer_address` modify `create_id` int(11) default null comment '创建人id';
alter table `customer_address` modify `last_update_id` int(11) default null comment '修改人id';
  --新增字段
alter table `customer_address` add column `customer_id` int(11) default null comment '客户id';
alter table `customer_address` add column `version` int(5) default 1 comment '版本号，防止操作冲突，默认 1，每次操作+1';
alter table `customer_address` add column `addressee` varchar(150) default null comment '收件地址';
alter table `customer_address` add column `gender` int(2) default null comment '收件人性别 1、男 0、女';


-- delivery_company 表不变


-- goods  表字段变更情况
  --修改字段名称、长度、类型、备注等
alter table `goods` change column `delivery_no` `delivery_order_no` varchar(100) DEFAULT NULL comment '快递单号';
alter table `goods` change column `delivery_name` `delivery_no` varchar(100) DEFAULT NULL comment '快递公司代码';
alter table `goods` modify `goods_type` int(4) default null comment '货物状态--1入库(待打包)，2已出库，3预录入（客户下单），6、待打包， 7、已打包，8、待出库';
alter table `customer` modify `kg` double(10,2) default null comment '重量';
alter table `customer` modify `vol` double(10,4) default null comment '体积';


-- operator 表不变


-- storage 表不变


--  user表不变


-- 以下是新增的表
-- address 收货地址表   （条形码打印用）
CREATE TABLE `address` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(2) DEFAULT '1' COMMENT '0、已删除   1、未删除',
  `is_available` int(2) DEFAULT '1' COMMENT '0、禁用      1、启用',
  `addressee_id` bigint(11) DEFAULT NULL COMMENT '收件人id',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `telephone` varchar(30) DEFAULT NULL COMMENT '联系方式',
  `company` varchar(50) DEFAULT NULL COMMENT '公司',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- address 收货人表   （条形码打印用）
CREATE TABLE `addressee` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(2) DEFAULT '1' COMMENT '0、已删除   1、未删除',
  `is_available` int(2) DEFAULT NULL COMMENT '0、禁用      1、启用',
  `addressee` varchar(50) DEFAULT NULL COMMENT '收件人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


--  用户 角色表
CREATE TABLE `user_role` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `role_name` varchar(30) DEFAULT NULL COMMENT '权限名称',
  `role_comment` varchar(30) DEFAULT NULL COMMENT '权限别名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 权限表
CREATE TABLE `permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(100) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 角色权限表
CREATE TABLE `role_permission` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(10) DEFAULT NULL COMMENT '权限id',
  `p_id` int(10) DEFAULT NULL COMMENT '操作权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- customer_pack 打包表
CREATE TABLE `customer_pack` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(2) DEFAULT '1' COMMENT '状态 1、未删除 0、已删除',
  `version` int(5) DEFAULT '0' COMMENT '数据版本，用于乐观锁，每次操作就增加 1 ',
  `business_number` varchar(60) DEFAULT NULL COMMENT '业务号码',
  `order_number` varchar(60) DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '操作员id',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户id',
  `pack_type` int(3) DEFAULT '1' COMMENT '包裹打包状态   1、待打包  2、已打包  3、确认发货  4、已出库',
  `unpacking` int(3) DEFAULT '1' COMMENT '是否拆包   1、不用拆包  2、需要拆包',
  `goods_no` varchar(255) DEFAULT NULL COMMENT '仓库中的包裹代码',
  `transportation_route_id` int(11) DEFAULT NULL COMMENT '路线id',
  `pre_quoted_price` double(10,2) DEFAULT NULL COMMENT '预报价格',
  `length` int(10) DEFAULT NULL COMMENT '单位：cm',
  `width` int(10) DEFAULT NULL COMMENT '单位：cm',
  `height` int(10) DEFAULT NULL COMMENT '单位：cm',
  `actual_weight` double(10,2) DEFAULT NULL COMMENT '实际重量',
  `actual_vol` double(10,4) DEFAULT NULL COMMENT '实际体积',
  `actual_price` double(10,2) DEFAULT NULL COMMENT '实际价格',
  `address_id` int(11) DEFAULT NULL COMMENT '收货地址',
  `out_storage_time` datetime DEFAULT NULL COMMENT '出库时间/发货时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- 客户下单表
CREATE TABLE `order` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT '1' COMMENT '数据状态  0、已删除',
  `version` int(5) DEFAULT '0' COMMENT '版本号，用于乐观锁',
  `customer_id` int(12) DEFAULT NULL COMMENT '用户id',
  `delivery_no` varchar(100) DEFAULT NULL,
  `delivery_order_no` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `goods_name` varchar(150) DEFAULT NULL COMMENT '货物名称',
  `goods_number` int(8) DEFAULT NULL COMMENT '包裹数量',
  `goods_type` int(2) DEFAULT '1' COMMENT '订单状态 1、未入库  2、已入库 ',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `address_id` int(11) DEFAULT NULL COMMENT '收货地址id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


--  路线表
CREATE TABLE `transportation_route` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT '1' COMMENT '状态  1、正常   0、已删除',
  `is_available` int(2) DEFAULT '1' COMMENT '1、启用  0、 禁用',
  `version` int(4) DEFAULT '1' COMMENT '版本号',
  `route_name` varchar(100) DEFAULT NULL COMMENT '运输路线',
  `operation_id` int(11) DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 重量 区间表
CREATE TABLE `transportation_route_weight` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT '1' COMMENT '状态  1、正常   0、已删除',
  `is_available` int(2) DEFAULT '1' COMMENT '1、启用  0、 禁用',
  `version` int(4) DEFAULT '1' COMMENT '版本号',
  `weight_interval` varchar(30) DEFAULT NULL COMMENT '重量区间',
  `operation_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `start_weight` int(6) DEFAULT NULL COMMENT '起始重量 （按照大于计算）',
  `end_weight` int(6) DEFAULT NULL COMMENT '结尾重量（按照小于等于）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 价格表
CREATE TABLE `transportation_route_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT '1' COMMENT '状态  1、正常   0、已删除',
  `is_available` int(2) DEFAULT '1' COMMENT '1、启用  0、 禁用',
  `version` int(4) DEFAULT '1' COMMENT '版本号',
  `route_id` varchar(100) DEFAULT NULL COMMENT '运输路线id',
  `weight_id` varchar(30) DEFAULT NULL COMMENT '重量区间id',
  `price` double(8,2) DEFAULT NULL COMMENT '价格',
  `operation_id` int(11) DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
