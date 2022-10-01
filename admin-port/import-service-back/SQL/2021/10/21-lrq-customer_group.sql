create table `customer_group`(
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `group_name` varchar(50) default null comment '分组名称',
   PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '客户组表';


create table `customer_group_link`(
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '使用状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `customer_group_id` int default null comment '组ID',
  `customer_id` int default null comment '客户ID',
   PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '客户分组关联表';