create table `warehouse_address` (
	 `id` int NOT NULL AUTO_INCREMENT,
	 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	 `create_id` int DEFAULT NULL COMMENT '创建人',
   `update_time` datetime DEFAULT NULL COMMENT '修改时间',
   `update_id` int DEFAULT NULL COMMENT '修改人',
	 `status` int DEFAULT 1 COMMENT '状态 1、正常 -1、删除',
	 `version` int DEFAULT 1 COMMENT '版本号 每次修改+1',
	 `addressee` varchar(50) default null comment '收件人',
	 `address` varchar(250) default null comment '地址',
	 `phone_number` varchar(30) default null comment '电话',
	 `postcode` varchar(20) default null comment '邮编',
	 `abnormal_relation` varchar(30) default null comment '异常联系',
	 `company_name` varchar(200) default null comment '公司名称',
	 `is_enable` int default -1 comment '是否启用 1、启用 -1、停用',
	 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '仓库地址';