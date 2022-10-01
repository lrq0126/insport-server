alter table `customer_integral_rule` add `order_amount` int DEFAULT NULL COMMENT '订单金额';

alter table `customer_integral_rule` modify `integrals` double(8,2) default null comment '积分';

alter table `customer_integral` modify `integrals` double(8,2) default null comment '积分';