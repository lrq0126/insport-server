create table `activity_reward` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '删除标识 -1删除， 1未删除',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',
  `is_enable` int default 1 comment '是否启用 1、启用；-1停用',

  `activity_name` varchar(200) default null comment '活动名称',
  `activity_type` varchar(200) default null comment '活动类型',
  `activity_time_type` int default null comment '活动时间类型 1、长期活动 2、限时活动 ',
  `start_time` datetime default null comment '开始时间',
  `end_time` datetime default null comment '结束时间',
  `reward_type` varchar(200) default null comment '奖励类型  coupons：优惠券；integral：积分',
  `reward_content_id` int default null comment '奖励内容相关ID',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '活动奖励';

insert into `sys_dict_detail` (status, is_enable, sdm_code, sdd_code, sdd_name, sdd_remark) values(1, 1, 'activity_type', 'ACTVTYPE00000000001', '推广返利', 'toPromote');
insert into `sys_dict_detail` (status, is_enable, sdm_code, sdd_code, sdd_name, sdd_remark) values(1, 1, 'activity_type', 'ACTVTYPE00000000002', '扫码注册', 'scanCode');