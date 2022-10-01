-- 新建角色表
CREATE TABLE `user_role` (
	`id` INT (4) NOT NULL AUTO_INCREMENT COMMENT '权限id',
	`role_name` VARCHAR (30) DEFAULT NULL COMMENT '权限名称',
	`role_comment` VARCHAR (30) DEFAULT NULL COMMENT '权限别名',
	PRIMARY KEY (`id`)
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

-- 用户表新增 role_id 字段
alter table `user` add column role_id int(11) comment '角色权限id';

-- 新增角色__权限 表
CREATE TABLE `role_permission` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `p_id` int(10) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 新增权限描述
CREATE TABLE `permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(100) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;