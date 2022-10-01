create table `sys_statistical` (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime default null comment '更新时间',
  `status` int default 1 comment '使用状态',
  `version` int default 1 comment '版本号',
  `new_customer_num` int default 0 comment '新增客户数量',
  `recharge_num` int default 0 comment '充值次数',
  `recharge_amount_sum` decimal(10,2) default 0 comment '充值总金额',
  `ware_customer_sum` int default 0 comment '仓库内的包裹客户数量',
  `in_ware_num` int default 0 comment '货物入库数量',
  `re_in_ware_num` int default 0 comment '预录入数量',
  `apply_pack_num` int default 0 comment '申请打包数量',
  `goods_num` int default 0 comment '仓库内货物数量(入库)',
  `goods_weight_sum` double(10,2) default 0 comment '仓库内货物重量(入库)',
  `goods_vol_sum` double(10,2) default 0 comment '仓库内货物立方数(入库)',
  `out_ware_weight_sum` double(10,2) default 0 comment '出库实际重量',
  `out_ware_num` int default 0 comment '出库数量',
  `receipt_num` int default 0 comment '签收数量',
  PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC comment '系统统计表';

-- 添加字段  2021/09/18
ALTER TABLE `sys_statistical` ADD COLUMN `pay_order_amount_sum` decimal(10,2) default 0 comment '支付订单金额';   -- 支付订单金额