CREATE TABLE `user_packed` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `create_name` varchar(50) default null comment '创建人名称',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `version` int default 1 comment '版本号',
  `remarks` varchar(250) default null comment '备注',
  `pack_id` int default null comment '订单ID',
  `order_number` varchar(80) default null comment '订单号',
  `pack_num` int default 1 comment '箱子数量',
  `actual_weight` double(10,2) default 0 comment '实际重量',
  `actual_vol` double(10, 4) default 0 comment '实际体积',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '用户打包明细表';