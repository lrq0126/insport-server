-- 创建定时任务表
CREATE TABLE `sys_schedule_task` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '数据状态 1、正常 -1、失效',
  `version` int default 1 comment '版本号',
  `remarks` varchar(250) default null comment '备注',

  `is_enable` int default 1 comment '是否启用 1、是 -1、否',
  `task_name` varchar(80) default null comment '任务名称',
  `task_code` varchar(80) default null comment '任务编码',
  `manual_execution_time` datetime default null comment '手动执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '定时任务表';

insert into sys_schedule_task (create_time, status, version, is_enable, task_code, task_name)
values (now(), 1, 1, 1, 'updateSysToken', '更新token信息'),
        (now(), 1, 1, 1, 'updateOptimalShelves', '统计最优货架'),
        (now(), 1, 1, 1, 'deletePosterTask', '删除上月海报内容'),
        (now(), 1, 1, 1, 'checkCouponsTask', '删除过期优惠券'),
        (now(), 1, 1, 1, 'adviceCustomerForGoodsNotOutTask', '超时未发货订单信息'),
        (now(), 1, 1, 1, 'adviceCustomerToPayTask', '发送催款消息'),
        (now(), 1, 1, 1, 'warehouseStatisticalTask', '统计仓库数据');