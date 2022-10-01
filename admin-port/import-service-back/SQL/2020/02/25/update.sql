ALTER TABLE `user_role` ADD COLUMN `is_available` int(2) default 1 comment '起停用状态，默认为1； 1、启用  0、禁用';
ALTER TABLE `user_role` ADD COLUMN `level` int(3) default 1 comment '角色等级 默认：1';