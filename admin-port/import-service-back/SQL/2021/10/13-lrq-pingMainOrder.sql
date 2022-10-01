-- 添加附加费字段
alter table `ping_main_order` add column `incidental` decimal(10,2) default 0 comment '附加费';


alter table `ping_main_order` add column `actual_weight` double(10,2) default 0 comment '实际重量';
alter table `ping_main_order` add column `actual_vol` double(10,2) default 0 comment '实际体积';