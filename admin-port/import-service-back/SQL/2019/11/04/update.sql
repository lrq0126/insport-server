ALTER TABLE `transportation_route` ADD COLUMN `originating_place` VARCHAR(150) DEFAULT NULL COMMENT '始发地';

ALTER TABLE `transportation_route` ADD COLUMN `destination` VARCHAR(150) DEFAULT NULL COMMENT '目的地';

ALTER TABLE `transportation_route` ADD COLUMN `english_name` VARCHAR(100) DEFAULT NULL COMMENT '英文名称';

ALTER TABLE `transportation_route` ADD COLUMN `note_type` VARCHAR(4) DEFAULT 1 COMMENT '1、人民币' ;

ALTER TABLE `transportation_route` ADD COLUMN `transportation_time` VARCHAR(30) DEFAULT NULL COMMENT '预计运送时间';

ALTER TABLE `transportation_route` ADD COLUMN `remote_fee` DOUBLE(6,2) DEFAULT NULL COMMENT '偏远费用';

ALTER TABLE `transportation_route` ADD COLUMN `remarks` VARCHAR(100) DEFAULT NULL COMMENT '备注';


-- 创建新的价格表
CREATE TABLE `route_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_name` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `version` int(4) DEFAULT '0' COMMENT '版本号，每次修改+1',
  `is_available` int(2) DEFAULT '1' COMMENT '启禁用  1启用  0禁用',
  `status` int(2) DEFAULT '1' COMMENT '状态 1、正常 0、删除',
  `route_id` int(11) DEFAULT NULL COMMENT '路线id',
  `route_type` int(2) DEFAULT NULL COMMENT '路线类型 1、空运  2、海运',
  `count_type` int(2) DEFAULT NULL COMMENT '计算类型  1、重量  2、体积',
  `start_weight` int(6) DEFAULT NULL COMMENT '起始重量 （按照大于计算）',
  `end_weight` int(6) DEFAULT NULL COMMENT '结尾重量（按照小于等于）',
  `start_volume` double(10,4) DEFAULT NULL COMMENT '起始体积（按照大于计算）',
  `end_volume` double(10,4) DEFAULT NULL COMMENT '结束体积（按照小于等于）',
  `price` double(8,2) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
