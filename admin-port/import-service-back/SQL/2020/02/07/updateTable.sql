alter TABLE `role_permission` ADD COLUMN `status` int(3) DEFAULT '1' COMMENT '状态  1、正常  0、已删除';
alter TABLE `permission` ADD COLUMN `status` int(3) DEFAULT '1' COMMENT '状态  1、正常  0、已删除';
alter TABLE `user_role` ADD COLUMN `status` int(3) DEFAULT '1' COMMENT '状态  1、正常  0、已删除';