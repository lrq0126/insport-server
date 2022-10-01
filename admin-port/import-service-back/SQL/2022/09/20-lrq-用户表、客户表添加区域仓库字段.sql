alter table `user` add column `commercial_area_id` int default null comment '区域仓库ID';
alter table `user` add column `commercial_area_name` varchar(250) default null comment '区域仓库名称';

alter table `goods` add column `commercial_area_id` int default null comment '区域仓库ID';
alter table `goods` add column `commercial_area_name` varchar(250) default null comment '区域仓库名称';

alter table `customer_pack` add column `commercial_area_id` int default null comment '区域仓库ID';
alter table `customer_pack` add column `commercial_area_name` varchar(250) default null comment '区域仓库名称';