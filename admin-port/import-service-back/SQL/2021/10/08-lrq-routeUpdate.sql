alter table `transportation_route` add column `is_per_ticket_pack` int  default 0 comment '是否一票一件 0、否；1、是';

alter table `transportation_route` add column `is_pin` int  default 0 comment '是否拼团渠道 0、否；1、是';

alter table `transportation_route` add column `side_length_limit` double(10, 2) default null comment '边长限制，超出这个长度则计算体积重';

alter table `transportation_route` add column `upper_limit_weight` double(10, 2) default null comment '空运重量上限';
alter table `transportation_route` add column `first_length` double(10, 2) default null comment '第一边长上限';
alter table `transportation_route` add column `second_length` double(10, 2) default null comment '第二边长上限';
alter table `transportation_route` add column `sensitive_price` decimal(10, 2) default null comment '敏感货物价格';

alter table `transportation_route` add column `greater_weight` double(8,2) DEFAULT NULL COMMENT '大于这个重量需要 叉车费';
alter table `transportation_route` add column `forklift_fee` double(6,2) DEFAULT NULL COMMENT '叉车费';

alter table `transportation_route` add column `before_weight_carry` double(8,2) default null comment '重量之前进位';
alter table `transportation_route` add column `before_weight_carry_type` int default null comment '重量进位类型 1、大于0进0.5   2、大于0进1';
alter table `transportation_route` add column `after_weight_carry` double(8,2) default null comment '重量之后进位';
alter table `transportation_route` add column `after_weight_carry_type` int default null comment '重量进位类型 1、大于0进0.5   2、大于0进1';

alter table `transportation_route` add column `cube_upper` double(8,2) default null comment '单位立方上限';
alter table `transportation_route` add column `vol_conversion`  double(8,2) default null comment '体积换算';

alter table `transportation_route` add column `customer_requirements` varchar(200) default null comment '客户须知';

