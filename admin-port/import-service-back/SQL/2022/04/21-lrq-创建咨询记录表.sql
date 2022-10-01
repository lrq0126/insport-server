create table `customer_contact_log` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',

  `customer_name` varchar(200) default null comment '客户名称',
  `phone_number` varchar(50) default null comment '联系方式',
  `email` varchar(50) default null comment '邮箱',
  `txt_content` varchar(250) default null comment '咨询内容',

  `is_complete` int default 2 comment '是否处理 1是 2否',
  `complete_id` int default null comment '处理人ID',
  `complete_name` varchar(50) default null comment '处理人',
  `complete_time` datetime default null comment '处理时间',

  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '客户联系日志';