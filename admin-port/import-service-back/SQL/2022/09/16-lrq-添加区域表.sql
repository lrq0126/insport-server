CREATE table `commercial_area`(
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `create_name` varchar(100) default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `update_name` varchar(100) default null comment '更新人',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `version` int default 1 comment '版本号',
  `remarks` varchar(250) default null comment '备注',

  `commercial_area_name` varchar(200) default null comment '商户区域名称',
  `commercial_area_no` varchar(200) default null comment '商户区域编码',
  `country_id` int default null comment '国家数据字典编码',
  `country` varchar(30) default null comment '所在国家',
  `is_enable` int default 1 comment '启停用 1：启用 -1：停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '商户区域表';