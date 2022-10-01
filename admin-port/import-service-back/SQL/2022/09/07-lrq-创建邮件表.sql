-- 创建邮件记录
CREATE TABLE `email_record` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `create_name` varchar(100) default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `update_name` varchar(100) default null comment '更新人',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `version` int default 1 comment '版本号',
  `remarks` varchar(250) default null comment '备注',

  `is_send` int default -1 comment '是否已发送 -1：否 1：是',
  `sender_id` int default null comment '发送人Id',
  `sender_name` varchar(100) default null comment '发送人',
  `send_time` datetime default null comment '发送时间',
  `title` varchar(200) default null comment '邮件标题',
  `send_object_type` varchar(50) default null comment '发送对象类型; personal：个人 group：群发',
  `send_object_id` int default null comment '发送对象ID',
  `send_object_name` varchar(50) default null comment '发送对象名称',
  `content_type` varchar(50) default null comment '邮件内容类型：text：文字  html：页面',
  `content` text default null comment '邮件内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '邮件记录';

-- 创建邮件记录
CREATE TABLE `email_record_detail` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `create_name` varchar(100) default null comment '创建人',

  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `version` int default 1 comment '版本号',
  `remarks` varchar(250) default null comment '备注',

  `is_send` int default -1 comment '是否已发送 -1：否 1：是',
  `sender_id` int default null comment '发送人Id',
  `sender_name` varchar(100) default null comment '发送人',
  `send_time` datetime default null comment '发送时间',
  `title` varchar(200) default null comment '邮件标题',
  `send_object_type` varchar(50) default null comment '发送对象类型; personal：个人 group：群发',
  `send_object_id` int default null comment '发送对象ID',
  `send_object_name` varchar(50) default null comment '发送对象名称',
  `content_type` varchar(50) default null comment '邮件内容类型：text：文字  html：页面',
  `content` text default null comment '邮件内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '邮件记录';