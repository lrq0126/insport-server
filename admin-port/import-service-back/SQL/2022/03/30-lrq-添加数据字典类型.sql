insert into `sys_dict_detail` (status, is_enable, sdm_code, sdd_code, sdd_name, sdd_remark)
    values(1, 1, 'activity_type', 'ACTVTYPE00000000003', '分享链接', 'shareLink');
insert into `sys_dict_detail` (status, is_enable, sdm_code, sdd_code, sdd_name, sdd_remark)
    values(1, 1, 'activity_type', 'ACTVTYPE00000000004', '分享链接注册', 'shareLinkRegister');

alter table `sys_coupons` add column `coupons_code` varchar(30) default null comment '优惠券编码';