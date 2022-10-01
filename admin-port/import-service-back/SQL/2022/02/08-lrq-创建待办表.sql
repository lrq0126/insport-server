create table `backlog`(
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '删除标识 -1删除， 1未删除',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注,',

  `title` varchar(80) default null comment '标题',
  `content` text default null comment '内容',
  `backlogger_id` int default null comment '待办人id',
  `backlogger` varchar(100) default null comment '待办人',
  `backlog_type` int default 0 comment '待办任务类型(数据字典关联)',
  `backlog_status` int default 0 comment '待办任务状态 0:新建；-1:拒绝；1:接受(进行中)；2:完成；3:超时',
  `overdue_time` datetime default null comment '过期时间',
  `associated_order` varchar(200) default null comment '关联订单,',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '待办任务表';

create table `backlog_detail`(
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '删除标识 -1删除， 1未删除',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注,',

  `backlog_id` int default null comment '待办id',
  `job_content` text default null comment '工作内容',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '待办任务工作明细表';