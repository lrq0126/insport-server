create table `annual_report` (
  `id` int not null AUTO_INCREMENT,
  `create_id` int default null comment '创建人',
  `create_time` datetime default DEFAULT NOW() comment '创建时间',
  `update_id` int default null comment '更新人',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '数据状态',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',

  `customer_id` int default null comment '客户id',
  `openid` varchar(100) default null comment '微信用户openid',
  `year` int default null comment '年份',
  `order_sum` int default null comment '订单数量',
  `in_warehouse_sum` int default null comment '入库数量',
  `consumption_amount_count` decimal(10,2) default null comment '总消费金额',
  `registration_time` datetime default null comment '注册时间',
  `stranded_goods_sum` int default null comment '滞留包裹数量',
  `content` varchar(255) default null comment '内容',
   PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '年度报告';





