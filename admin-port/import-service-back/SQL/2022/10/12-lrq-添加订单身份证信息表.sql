create table `customer_pack_identity` (
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

  `customer_pack_id` int default null comment '订单ID',
  `identity_code` varchar(100) default null comment '身份证号码',
  `identity_name` varchar(100) default null comment '身份证姓名',
  `images_id` varchar(100) default null comment '身份证图片id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '客户订单身份证信息';