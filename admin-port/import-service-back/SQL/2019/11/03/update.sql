-- 新增打包时间字段
alter table `customer_pack` ADD COLUMN `pack_time` datetime DEFAULT NULL COMMENT '打包时间';

-- 新增打包时间字段
alter table `goods` ADD COLUMN `pack_time` datetime DEFAULT NULL COMMENT '打包时间';

-- 新增字段
ALTER table `goods` add column `version` INT(4) default 0 COMMENT '版本号,每次修改+1';