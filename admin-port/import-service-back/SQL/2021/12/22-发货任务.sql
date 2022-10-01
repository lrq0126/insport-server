create table `delivery_task`(
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `task_name` varchar(50) default null comment '任务名称',
  `task_order` varchar(50) default null comment '任务编号',
  `task_number` int default null comment '任务数量',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '发货任务中心';

create table `delivery_task_detail`(
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `delivery_task_id` int default null comment '发货任务ID',
  `task_order` varchar(50) default null comment '任务编号',
  `order_number` varchar(50) default null comment '订单号',
  `pack_number` int default null comment '包裹/箱子数量',
  `route_name` varchar(100) default null comment '渠道名称',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '发货任务明细';