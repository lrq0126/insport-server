alter table `sys_notice` add column `overdue_time` date default null comment '过期时间';

alter table `sys_notice` add column `is_initiative` int default 2 comment '是否主动发布 1是 2系统自动发送的通知';

alter table `sys_notice` add column `is_timing_task` int default 1 comment '是否定时发布任务 1、已发布 2、等待发布';
alter table `sys_notice` add column `release_time` datetime default null comment '发布时间';