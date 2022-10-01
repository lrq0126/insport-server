-- 创建系统密匙表
create table `sys_token` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `sys_token` varchar(250) default null comment '系统token',
  `time_limit` int default 7200 comment '有效时长',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '系统密匙表';

-- 创建仓库货架表
CREATE TABLE `shelves_area` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `version` int default 1 comment '版本号',
  `remarks` varchar(250) default null comment '备注',

  `shelves_area` varchar(50) default null comment '货架号',
  `shelves_type` varchar(50) default 'genera' comment '货架类型；general：常规；big：大货； abnormal：异常件',
  `sorting_export` varchar(50) default null comment '分拣出口',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '仓库货架-区';

CREATE TABLE `shelves_row` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `version` int default 1 comment '版本号',
  `remarks` varchar(250) default null comment '备注',

  `shelves_row` varchar(50) default null comment '货架排号',
  `shelves_limit` int default null comment '货架阀值，超过这个数量则是货架即将空间不足',
  `parent_id` int default null comment '货架的父类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '仓库货架-排';

-- 创建货物货架关联表
CREATE TABLE `goods_shelves_row` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `version` int default 1 comment '版本号',
  `remarks` varchar(250) default null comment '备注',

  `shelves_area_id` int default null comment '货架区ID',
  `shelves_row_id` int default null comment '货架排号ID',
  `goods_id` int default null comment '货物ID',
  `is_picking` int default 2 comment '是否已拣货， 1、已拣货； 2、未拣货',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '货物所在货架信息表';

-- 创建货架货物容量表
CREATE TABLE `shelves_goods_capacity` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',

  `shelves_area_id` int default null comment '货架区ID',
  `shelves_area` varchar(255) default null comment '货架区',
  `shelves_row_id` int default null comment '货架排号ID',
  `shelves_row` varchar(255) default null comment '货架排号',
  `goods_sum` int default 0 comment '货物数量',
  `shelves_limit` int default 20 comment '可放置数量',
  `capacity` double(8, 2) default 0 comment '容量比',
  `sorting_export` varchar(50) default null comment '分拣出口',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '货架货物容量表';

