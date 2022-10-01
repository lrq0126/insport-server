-- 新增字段
ALTER TABLE `customer_pack_number` ADD COLUMN `goods_nos` TEXT DEFAULT NULL COMMENT '快递包裹 goods_no  集合';

ALTER TABLE `customer_pack_number` ADD COLUMN `pack_order` VARCHAR(50) DEFAULT NULL COMMENT '订单号';