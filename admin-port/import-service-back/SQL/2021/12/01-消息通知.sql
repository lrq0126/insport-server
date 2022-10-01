
ALTER TABLE `sys_notice` DROP COLUMN `is_timing_task`;

alter table `sys_notice` add column `is_timing_task` int default 0 comment '是否定时发布任务 0、已发布 1、等待发布';

ALTER TABLE `sys_notice` DROP COLUMN `is_initiative`;

alter table `sys_notice` add column `is_initiative` int default 0 comment '是否主动发布 1是 0系统自动发送的通知';