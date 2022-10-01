-- 权限添加数据字典菜单
insert into permission (`describe`, `level`, sort_no, `status`) values ('数据字典', 1, 4, 1);

-- 数据字典添加备用字段 alternate_field
alter table sys_dict_detail add column `alternate_field` varchar(50) default null comment '备用字段 此用来存储货币的汇率';

-- 数据字典添加必备的人民币数据
INSERT INTO sys_dict_detail (sdm_code, sdd_code, sdd_name, sdd_remark, alternate_field, `status`, is_enable)
values ('currency', 'CENY000000001', '人民币', 'CNY', '1', 1, 1)

