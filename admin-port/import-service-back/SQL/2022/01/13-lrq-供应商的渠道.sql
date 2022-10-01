create table `agent_channel` (
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',

  `agent_code` varchar(50) default null comment '代理商代码',
  `inside_code` varchar(50) default null comment '自己系统内部渠道代码',
  `channel_code` varchar(50) default null comment '代理商系统渠道代码',
  `channel_type` varchar(50) default null comment '代理商系统渠道类型',
  `channel_ename` varchar(50) default null comment '渠道别名',
  `channel_name` varchar(50) default null comment '代理商渠道名称',
  `max_weight` double(8,2) default null comment '最大重量',
  `message` varchar(200) default null comment '备注信息',
  `is_enable` int default 1 comment '是否启用 1是 2否',
  `product_name` varchar(250) default null comment '品名',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '供应商渠道';