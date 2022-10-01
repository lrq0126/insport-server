alter table `customer_address` add column `provinces` varchar(250) default null comment '省份/城市';

alter table `customer_pack_receiver_address` add column `provinces` varchar(250) default null comment '省份/城市';