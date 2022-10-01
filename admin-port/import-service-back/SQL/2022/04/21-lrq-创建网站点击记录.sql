create table `web_click_log` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',

  `ip_location` varchar(200) default null comment 'IP地址',
  `open_url` varchar(200) default null comment '打开网页',
  `country` varchar(200) default null comment '国家',
  `count_num` int default 1 comment '次数',

  `time_long` int default 0 comment '时长',
  `from_source` varchar(50) default null comment '来源',
  `system_name` varchar(50) default null comment '系统',
  `click_event` varchar(250) default null comment '事件',
  `is_contact` varchar(250) default null comment '是否有联系咨询',

  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '官网点击日志';