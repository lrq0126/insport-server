create table `customer_from` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '删除标识 -1删除， 1未删除',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注,',

  `customer_id` int default null comment '客户ID',
  `from_customer_id` int default null comment '来源客户ID',
  `from_type` int default null comment '来源类型',
  `from_user_id` int default null comment '来源用户ID',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '客户来源表';