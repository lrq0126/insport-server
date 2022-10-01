create table `ping_main_audit`(
    `id` int not null AUTO_INCREMENT,
    `create_time` datetime default null comment '创建时间',
    `create_id` int default null comment '创建人',
    `update_time` datetime default null comment '更新时间',
    `update_id` int default null comment '更新人',
    `status` int default 1 comment '状态 1、正常 0、已删除',
    `version` int default 0 comment '版本号',
    `auditor_id` int default null comment '审核人id',
    `auditor_name` varchar(50) default null comment '审核人名称',
    `p_id` int default null comment '拼团id',
    `p_order` varchar(50) default null comment '拼团订单号',
    `audit_result` int default null comment '审核结果',
    `audit_opinion` varchar(255) default null comment '审核意见',
    primary key (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='拼团审核表';