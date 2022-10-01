create table `activity_poster` (
  `id` int not null AUTO_INCREMENT comment 'id',
  `create_time` datetime default null comment '创建时间',
  `create_id` int default null comment '创建人',
  `status` int default 1 comment '删除标识 -1删除， 1未删除',
  `version` int default 1 comment '版本号',
  `remarks` varchar(200) default null comment '备注',

  `poster_name` varchar(80) default null comment '海报名称',
  `location_path` varchar(250) default null comment '本机地址',
  `poster_url` varchar(250) default null comment '图片地址',
  `is_place_qrcode` int default null comment '是否放置二维码',
  `qrcode_scaling` double(8,2) default null comment '二维码缩放比例',
  `width_position` int default null comment '二维码水平位置',
  `height_position` int default null comment '二维码高度位置',

  `is_enable` int default 1 comment '是否启用 1、启用；-1停用',

  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '活动海报';