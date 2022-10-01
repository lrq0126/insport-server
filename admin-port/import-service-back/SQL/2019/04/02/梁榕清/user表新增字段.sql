alter table `user` add column `level` int(2) default null comment '管理员等级'
alter table `user` add column `login_name` varchar(30) default null comment '登陆名'