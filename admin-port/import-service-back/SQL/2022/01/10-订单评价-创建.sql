create table `order_evaluate`(
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default null comment '创建时间/即评价时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',

  `pack_id` int default null comment '订单id',
  `order_number` varchar(50) default null comment '订单号',
  `customer_id` int default null comment '客户id',
  `customer_name` varchar(50) default null comment '客户名称',
  `packing_speed` int default null comment '打包速度',
  `packing_quality` int default null comment '打包质量',
  `logistics_speed` int default null comment '物流速度',
  `customer_server_attitude` int default null comment '客服态度',
  `comprehensive_server` int default null comment '综合服务',
   PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '订单评价';


insert into `permission` (`describe`, `status`, `level`, `sort_no`)
value('运营视窗', 1, 1, 2);

select id as parent_id from permission where `status` = 1 and `describe` = '运营视窗';

insert into `permission` (`describe`, `status`, `level`, `sort_no`, parent_id)
value('数据统计', 1, 1, 1, );

insert into `permission` (`describe`, `status`, `level`, `sort_no`, parent_id)
value('图表统计', 1, 1, 2, );

insert into `permission` (`describe`, `status`, `level`, `sort_no`, parent_id)
value('订单评价', 1, 1, 3, );