create table `customer_integral` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',
  `status` int default null comment '状态',
  `remark` varchar(100) default null comment '备注',

  `customer_id` int default null comment '客户id',
  `integral_type` varchar(100) default null comment '积分类型；推广积分、下单积分、我的客户积分、使用积分',
  `from_id` int default null comment '来源客户',
  `integrals` int default null comment '积分数',

  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '客户积分';

create table `customer_integral_rule` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '删除标识 -1删除， 1未删除',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',

  `integral_type` varchar(200) default null comment '积分类型',
  `integrals` int default null comment '积分数',
  `deductible_amount` int default null comment '抵扣金额',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '积分规则';

insert into sys_dict_detail (sdm_code, sdd_code, sdd_name, sdd_remark, status, is_enable) values('integral_type', 'ITGTYPE0000000001', '推广积分', 'popularize', 1, 1);
insert into sys_dict_detail (sdm_code, sdd_code, sdd_name, sdd_remark, status, is_enable) values('integral_type', 'ITGTYPE0000000002', '下单积分', 'order', 1, 1);
insert into sys_dict_detail (sdm_code, sdd_code, sdd_name, sdd_remark, status, is_enable) values('integral_type', 'ITGTYPE0000000003', '积分抵扣', 'deductible', 1, 1);