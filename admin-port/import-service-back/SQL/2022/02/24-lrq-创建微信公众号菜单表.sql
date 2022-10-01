create table `wechat_menu` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `update_time` datetime default null comment '更新时间',
  `update_id` int default null comment '更新人',
  `status` int default 1 comment '删除标识 -1删除， 1未删除',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注,',

  `menu_level` int default 1 comment '菜单等级 1：一级菜单 2：二级菜单',
  `menu_name` varchar(50) default null comment '菜单名称',
  `menu_ename` varchar(50) default null comment '英文名称',
  `parent_id` int default null comment '父级菜单ID',
  `menu_type` varchar(200) default null comment '菜单的响应动作类型',
  `redirect_url` varchar(200) default null comment '转跳网址',
  `wechat_key` varchar(200) default null comment '菜单KEY值，用于消息接口推送',
  `media_id` varchar(200) default null comment '调用新增永久素材接口返回的合法media_id',
  `article_id` varchar(200) default null comment '发布后获得的合法article_id',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '微信公众号菜单';