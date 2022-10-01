-- 渠道添加货值货币字段
alter table transportation_route add column `currency` varchar(50) default 'CENY000000001' comment '货值货币';