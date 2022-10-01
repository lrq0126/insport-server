-- 打包表新增 杂费字段
ALTER TABLE `customer_pack` ADD COLUMN `incidental` DECIMAL(8,2) DEFAULT null COMMENT '杂费；';


-- goods 新增 入包字段
alter table `goods` add column `in_packed` int(3) DEFAULT 0 comment '是否入包，0、未入包  1、已入包  （打包时使用的字段）';