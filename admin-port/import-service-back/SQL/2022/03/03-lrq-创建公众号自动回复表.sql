create table `wechat_reply` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '删除标识 -1删除， 1未删除',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注,',

  `reply_rule` varchar(50) default null comment '回复规则名称',
  `reply_type` varchar(50) default null comment '回复类型',
  `reply_content` varchar(500) default null comment '回复内容',
  `key_word` varchar(50) default null comment '关键字',
  `redirect_url` varchar(200) default null comment '网页',
  `media_id` varchar(200) default null comment '永久素材的合法media_id',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '微信公众号自动回复';

create table `wechat_reply_log` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',
  `open_id` varchar(150) default null comment '创建人',
  `key_word` varchar(100) default null comment '关键字',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '微信公众号自动回复日志记录';

insert into wechat_reply (status, version, reply_rule, reply_type, key_word) values (1, 1, '关注后自动回复', 'text', '关注后自动回复');
insert into wechat_reply (status, version, reply_rule, reply_type, key_word) values (1, 1, '扫码关注后自动回复', 'text', '扫码关注后自动回复');
insert into wechat_reply (status, version, reply_rule, reply_type, key_word) values (1, 1, '其他', 'text', '其他');
