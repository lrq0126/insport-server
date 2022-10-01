ALTER TABLE `goods` ADD COLUMN `is_sensitive` int(2) default 0 comment '是否敏感货物';   -- 货物表添加敏感货物标记

ALTER TABLE `customer_pack` ADD COLUMN `is_sensitive` int(2) default 0 comment '是否敏感货物';   -- 包裹表添加敏感货物标记

ALTER TABLE `customer_pack_number` ADD COLUMN `is_sensitive` int(2) default 0 comment '是否敏感货物';   -- 子包裹表添加敏感货物标记


ALTER TABLE `ping_main_order` ADD COLUMN `goods_number` int(4) default 0 comment '货物数量';   -- 拼团子订单包裹表添加货物数量