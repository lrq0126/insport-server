create table `pin_spell_mail` (
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

  `order_name` varchar(100) default null comment '拼邮名称',
  `order_number` varchar(100) default null comment '订单号',
  `pin_type` int default null comment '状态 1、待发货 2、已发货 3、到站',
  `route_id` int default null comment '渠道ID',
  `route_name` varchar(100) default null comment '渠道名称',
  `country` varchar(100) default null comment '国家',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '拼邮订单表';

create table `pin_spell_mail_customer_pack` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',

  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `pin_spell_mail_id` int default 1 comment '拼邮的订单ID',
  `customer_pack_id` int default 1 comment '订单ID',

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '拼邮&订单关联表';