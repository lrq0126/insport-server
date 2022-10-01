alter table `activity_poster` add column `poster_type` varchar(50) default 'sharePoster' comment '海报类型';


insert into wechat_reply (status, version, reply_rule, reply_type, key_word) values (1, 1, '已经关注过的', 'text', '
Hi，尊敬的会员：LOGIN_NAME，您好，欢迎关注飞轮国际云仓物流~ 我们提供180天免费仓储寄存服务，您可在淘宝、京东、拼多多等电商平台直接下单邮寄到我们仓库。 亲爱的会员（LOGIN_NAME）您好，由于您关注过我们，为了提高您的使用体验，我们建议您以后下单使用下面的收货地址 您的收件地址： 收件人：飞轮转LOGIN_NAME; 电话：13434114290; 邮编：510000; 地址：广东省广州市白云区嘉禾街道新科下村贝底隆街6号-LOGIN_NAME国际仓转飞轮国际; LOGIN_NAME就是您的入仓号，请确保收件信息上有您的入仓号 如有疑问，请联系微信客服：17701963150');

INSERT INTO `sys_dict_detail`( `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (NULL, 'poster_type', 'POSTTYPE1653026559189', '分享海报', NULL, 'sharePoster', 1, 15, '2022-04-02 14:59:10', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`( `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (NULL, 'poster_type', 'POSTTYPE1653029206888', '微信绑定海报', NULL, 'bindingPoster', 1, 15, '2022-04-02 14:59:10', NULL, NULL, 1, NULL);
