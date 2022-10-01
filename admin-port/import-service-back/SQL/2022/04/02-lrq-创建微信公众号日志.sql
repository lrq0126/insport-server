create table `wechat_official_account_log` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',

  `from_user_name` varchar(200) default null comment '发送方帐号',
  `msg_type` varchar(50) default null comment '消息类型',
  `event` varchar(50) default null comment '事件类型',
  `event_key` varchar(250) default null comment '事件KEY值',

  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '微信公众号日志';