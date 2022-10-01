alter table `route_price` add column `upper_limit_weight` int(5) default null comment '空运重量上限';

alter table `route_price` add column `first_length` int(5) default null comment '第一边长上限';

alter table `route_price` add column `second_length` int(5) default null comment '第二边长上限';