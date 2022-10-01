alter table `goods` add column `sort_type` int(3) default 0 comment '分拣状态 0、未分拣 1、已分拣';

alter table `route_price` add column `small_package_weight` double(8,2) default null comment '小包裹重量';

alter table `route_price` add column `remarks` varchar(100) default null comment '备注';

alter table `route_price` add column `weight_carry_type` int(3) default null comment '重量进位类型 1、大于0进0.5   2、大于0进1';

alter table `route_price` add column `greater_weight` double(8,2) default null comment '大于这个重量需要 叉车费';

alter table `route_price` add column `forklift_fee` double(6,2) default null comment '叉车费';

alter table `route_price` add column `package_type` int(3) default null comment '包裹体积，1、小包裹  2、大包裹';

alter table `customer_pack` ADD COLUMN `settlement_weight` double(10,2) default null comment '结算重量';

alter table `customer_pack` add column `settlement_vol` double(10,4) default null comment '结算体积';