-- 用户表
INSERT INTO `user`(`id`, `username`, `password`, `usercode`, `last_login_time`, `last_logout_time`, `login_count`, `status`, `create_time`, `create_id`, `last_update_time`, `last_update_id`, `level`, `login_name`, `role_id`) VALUES (1, '超级管理员', 'superAdmin@123', 'USER2020000000000000000', NULL, NULL, '853', 1, '2020-03-10 08:00:41', NULL, NULL, NULL, 5, 'superAdmin', 1);
INSERT INTO `user`(`id`, `username`, `password`, `usercode`, `last_login_time`, `last_logout_time`, `login_count`, `status`, `create_time`, `create_id`, `last_update_time`, `last_update_id`, `level`, `login_name`, `role_id`) VALUES (2, '管理员', 'admin@001', 'USER2020000000000000001', NULL, NULL, '0',1, '2020-03-10 08:16:16', NULL, NULL, NULL, 4, 'admin', 2);
INSERT INTO `user`(`id`, `username`, `password`, `usercode`, `last_login_time`, `last_logout_time`, `login_count`, `status`, `create_time`, `create_id`, `last_update_time`, `last_update_id`, `level`, `login_name`, `role_id`) VALUES (3, '财务', 'finance@010', 'USER2020000000000000002', NULL, NULL, '0', 1, '2020-03-10 08:16:57', NULL, NULL, NULL, 3, 'finance', 3);


-- 角色表
INSERT INTO `user_role` VALUES (1, 'superAdmin', '超级管理员', 1, 1, 5, NULL);
INSERT INTO `user_role` VALUES (2, 'admin', '管理员', 1, 1, 4, NULL);
INSERT INTO `user_role` VALUES (3, 'finance', '财务', 1, 1, 3, NULL);
INSERT INTO `user_role` VALUES (4, 'staff', '普通用户', 1, 1, 1, NULL);


-- 权限表
INSERT INTO `permission` VALUES (29, NULL, '', '首页', NULL, 1, 1, 1);
INSERT INTO `permission` VALUES (30, NULL, NULL, '系统管理', NULL, 1, 1, 2);
INSERT INTO `permission` VALUES (31, NULL, NULL, '管理员', NULL, 1, 1, 3);
INSERT INTO `permission` VALUES (32, NULL, NULL, '客户知资', NULL, 1, 1, 4);
INSERT INTO `permission` VALUES (33, NULL, NULL, '操作/客户管理', NULL, 1, 1, 5);
INSERT INTO `permission` VALUES (34, NULL, NULL, '仓库管理', NULL, 1, 1, 6);
INSERT INTO `permission` VALUES (35, NULL, NULL, '快递公司管理', NULL, 1, 1, 7);
INSERT INTO `permission` VALUES (36, NULL, NULL, '渠道管理', NULL, 1, 1, 8);
INSERT INTO `permission` VALUES (37, NULL, NULL, '打包任务管理', NULL, 1, 1, 9);
INSERT INTO `permission` VALUES (38, NULL, NULL, '财务管理', NULL, 1, 1, 10);
INSERT INTO `permission` VALUES (39, NULL, NULL, '菜单管理', 30, 1, 2, 1);
INSERT INTO `permission` VALUES (40, NULL, NULL, '角色管理', 30, 1, 2, 2);
INSERT INTO `permission` VALUES (41, NULL, NULL, '公告通知', 30, 1, 2, 3);
INSERT INTO `permission` VALUES (42, NULL, NULL, '操作员', 31, 1, 2, 1);
INSERT INTO `permission` VALUES (43, NULL, NULL, '客户列表', 32, 1, 2, 1);
INSERT INTO `permission` VALUES (44, NULL, NULL, '货物列表', 33, 1, 2, 1);
INSERT INTO `permission` VALUES (45, NULL, NULL, '仓库列表', 34, 1, 2, 1);
INSERT INTO `permission` VALUES (46, NULL, NULL, '快递公司列表', 35, 1, 2, 1);
INSERT INTO `permission` VALUES (47, NULL, NULL, '渠道列表', 36, 1, 2, 1);
INSERT INTO `permission` VALUES (48, NULL, NULL, '待打包任务', 37, 1, 2, 1);
INSERT INTO `permission` VALUES (49, NULL, NULL, '待发货区', 37, 1, 2, 2);
INSERT INTO `permission` VALUES (50, NULL, NULL, '已发货列表', 37, 1, 2, 3);
INSERT INTO `permission` VALUES (51, NULL, NULL, '账单列表', 38, 1, 2, 1);
INSERT INTO `permission` VALUES (52, NULL, NULL, '客户列表', 38, 1, 2, 2);
INSERT INTO `permission` VALUES (53, NULL, 'role:create', '创建角色', 40, 1, 3, 1);
INSERT INTO `permission` VALUES (54, NULL, 'role:update', '编辑角色/选择角色权限', 40, 1, 3, 2);
INSERT INTO `permission` VALUES (55, NULL, 'role:delete', '删除角色', 40, 1, 3, 3);
INSERT INTO `permission` VALUES (56, '/account/customerAccount/recharge', 'customerAccount:recharge', '客户充值按钮', 52, 1, 3, 1);
INSERT INTO `permission` VALUES (57, '/customerPack/update/packing', 'pack:packing', '确认打包', 48, 1, 3, 1);
INSERT INTO `permission` VALUES (58, '/customerPack/update/deliver', 'pack:deliver', '确认发货', 49, 1, 3, 1);
INSERT INTO `permission` VALUES (59, '/route/transportationRoute/add', 'transportationRoute:add', '新增路线', 47, 1, 3, 1);
INSERT INTO `permission` VALUES (60, '/route/transportationRoute/update', 'transportationRoute:update', '修改路线信息', 47, 1, 3, 2);
INSERT INTO `permission` VALUES (61, '/route/transportationRoute/delete', 'transportationRoute:delete', '删除路线信息', 47, 1, 3, 3);
INSERT INTO `permission` VALUES (62, '/customer/create', 'customer:create', '新增客户', 43, 1, 3, 1);
INSERT INTO `permission` VALUES (63, '/customer/update', 'customer:update', '修改客户信息', 43, 1, 3, 2);
INSERT INTO `permission` VALUES (64, '/customer/delete', 'customer:delete', '删除客户', 43, 1, 3, 3);
INSERT INTO `permission` VALUES (65, '/company/create', 'company:create', '新增快递公司', 46, 1, 3, 1);
INSERT INTO `permission` VALUES (66, '/company/update', 'company:update', '修改快递公司信息', 46, 1, 3, 2);
INSERT INTO `permission` VALUES (67, '/company/delete', 'company:delete', '删除快递公司', 46, 1, 3, 3);
INSERT INTO `permission` VALUES (68, '/goods/create', 'goods:create', '新增/登记货物信息', 44, 1, 3, 1);
INSERT INTO `permission` VALUES (69, '/goods/update', 'goods:update', '修改货物信息', 44, 1, 3, 2);
INSERT INTO `permission` VALUES (70, '/goods/delete', 'goods:delete', '删除货物信息', 44, 1, 3, 3);
INSERT INTO `permission` VALUES (71, '/goods/customer/in/warehouse', 'goods:inWarehouse', '入库', 44, 1, 3, 4);
INSERT INTO `permission` VALUES (72, '/storage/create', 'storage:create', '新增仓库区域', 45, 1, 3, 1);
INSERT INTO `permission` VALUES (73, '/storage/update', 'storage:update', '修改仓库区域', 45, 1, 3, 2);
INSERT INTO `permission` VALUES (74, '/storage/delete', 'storage:delete', '删除仓库区域', 45, 1, 3, 3);
INSERT INTO `permission` VALUES (75, '/user/create', 'user:create', '新建用户', 42, 1, 3, 1);
INSERT INTO `permission` VALUES (76, '/user/update', 'user:update', '修改用户信息', 42, 1, 3, 2);
INSERT INTO `permission` VALUES (77, '/user/delete', 'user:delete', '删除用户', 42, 1, 3, 3);
INSERT INTO `permission` VALUES (78, '/sys/sysNotice/publish', 'sysNotice:publish', '发布通知', 41, 1, 3, 1);
INSERT INTO `permission` VALUES (79, '/sys/sysNotice/delete', 'sysNotice:delete', '删除通知', 41, 1, 3, 2);
INSERT INTO `permission` VALUES (80, '/account/customerAccount/getSingleCustomerList', 'customerAccount:getSingleCustomerList', '查看客户历史', 52, 1, 3, 2);
INSERT INTO `permission` VALUES (81, '/customerPack/delete', 'pack:delete', '拆包', 48, 1, 3, 2);
INSERT INTO `permission` VALUES (82, NULL, NULL, '国家列表', 36, 1, 2, 2);
INSERT INTO `permission` VALUES (83, '/sysDictDetail/create/country', 'country:create', '新增国家', 82, 1, 3, 1);
INSERT INTO `permission` VALUES (84, '/sysDictDetail/update/country', 'country:update', '修改国家', 82, 1, 3, 2);
INSERT INTO `permission` VALUES (85, '/sysDictDetail/delete/country', 'country:delete', '删除国家', 82, 1, 3, 3);
INSERT INTO `permission` VALUES (86, '/customerPack/update', 'pack:update', '修改包裹（待打包列表）', 48, 1, 3, 3);
INSERT INTO `permission` VALUES (87, '/customerPack/update/customerPack/update', 'pack:update', '修改包裹（待发货列表）', 49, 1, 3, 3);
INSERT INTO `permission` VALUES (88, NULL, NULL, '签收管理', NULL, 1, 1, 11);
INSERT INTO `permission` VALUES (89, NULL, NULL, '单个签收', 88, 1, 2, 1);
INSERT INTO `permission` VALUES (90, NULL, NULL, '批量签收', 88, 1, 2, 2);
INSERT INTO `permission` VALUES (91, NULL, NULL, '签收列表', 88, 1, 2, 3);
INSERT INTO `permission` VALUES (92, '/dwReceipt/create', 'dwReceipt:create', '单件签收', 89, 1, 3, 1);
INSERT INTO `permission` VALUES (93, '/dwReceipt/create', 'dwReceipt:create', '批量签收', 90, 1, 3, 1);
INSERT INTO `permission` VALUES (94, '/dwReceipt/update', 'dwReceipt:update', '修改', 91, 1, 3, 1);
INSERT INTO `permission` VALUES (95, NULL, NULL, '财务充值记录', 38, 1, 2, 4);
INSERT INTO `permission` VALUES (96, '/account/customerAccount/getRechargeRecord', 'customerAccount:getRechargeRecord', '修改充值金额', 95, 1, 3, 1);
INSERT INTO `permission` VALUES (97, '/account/customerAccount/rollbackRacharge', 'customerAccount:rollbackRacharge', '撤销', 95, 1, 3, 2);
INSERT INTO `permission` VALUES (98, '/customer/reset/password', 'customer:reset', '重置客户密码', 43, 1, 3, 4);
INSERT INTO `permission` VALUES (99, '/container/list', 'container:list', '批次管理', 88, 1, 2, 4);
INSERT INTO `permission` VALUES (100, NULL, NULL, '代理申请打包', 37, 1, 2, 4);
INSERT INTO `permission` VALUES (103, NULL, NULL, '添加权限', 39, 1, 3, 1);
INSERT INTO `permission` VALUES (104, NULL, NULL, '拼团管理', NULL, 1, 1, 11);
INSERT INTO `permission` VALUES (105, NULL, NULL, '申请拼团', 104, 1, 2, 1);
INSERT INTO `permission` VALUES (106, NULL, NULL, '拼团中', 104, 1, 2, 2);
INSERT INTO `permission` VALUES (107, NULL, NULL, '已成团', 104, 1, 2, 3);
INSERT INTO `permission` VALUES (108, NULL, NULL, '已失效', 104, 1, 2, 4);
INSERT INTO `permission` VALUES (109, NULL, NULL, '优惠券管理', NULL, 1, 1, 12);
INSERT INTO `permission` VALUES (110, NULL, NULL, '数据字典', NULL, 1, 1, 4);
INSERT INTO `permission` VALUES (112, NULL, NULL, '运营视窗', NULL, 1, 1, 2);
INSERT INTO `permission` VALUES (113, NULL, NULL, '数据统计', 112, 1, 2, 1);
INSERT INTO `permission` VALUES (114, NULL, NULL, '图表统计', 112, 1, 2, 2);
INSERT INTO `permission` VALUES (115, NULL, NULL, '订单评价', 112, 1, 2, 3);
INSERT INTO `permission` VALUES (119, NULL, NULL, '效绩统计', 112, 1, 2, 4);


-- 角色权限表
INSERT INTO `role_permission` VALUES (1, 1, 29, 1);
INSERT INTO `role_permission` VALUES (2, 1, 30, 1);
INSERT INTO `role_permission` VALUES (3, 1, 31, 1);
INSERT INTO `role_permission` VALUES (4, 1, 32, 1);
INSERT INTO `role_permission` VALUES (5, 1, 33, 1);
INSERT INTO `role_permission` VALUES (6, 1, 34, 1);
INSERT INTO `role_permission` VALUES (7, 1, 35, 1);
INSERT INTO `role_permission` VALUES (8, 1, 36, 1);
INSERT INTO `role_permission` VALUES (9, 1, 37, 1);
INSERT INTO `role_permission` VALUES (10, 1, 38, 1);
INSERT INTO `role_permission` VALUES (11, 1, 39, 1);
INSERT INTO `role_permission` VALUES (12, 1, 40, 1);
INSERT INTO `role_permission` VALUES (13, 1, 41, 1);
INSERT INTO `role_permission` VALUES (14, 1, 42, 1);
INSERT INTO `role_permission` VALUES (15, 1, 43, 1);
INSERT INTO `role_permission` VALUES (16, 1, 44, 1);
INSERT INTO `role_permission` VALUES (17, 1, 45, 1);
INSERT INTO `role_permission` VALUES (18, 1, 46, 1);
INSERT INTO `role_permission` VALUES (19, 1, 47, 1);
INSERT INTO `role_permission` VALUES (20, 1, 48, 1);
INSERT INTO `role_permission` VALUES (21, 1, 49, 1);
INSERT INTO `role_permission` VALUES (22, 1, 50, 1);
INSERT INTO `role_permission` VALUES (23, 1, 51, 1);
INSERT INTO `role_permission` VALUES (24, 1, 52, 1);
INSERT INTO `role_permission` VALUES (25, 1, 53, 1);
INSERT INTO `role_permission` VALUES (26, 1, 54, 1);
INSERT INTO `role_permission` VALUES (27, 1, 55, 1);
INSERT INTO `role_permission` VALUES (28, 1, 56, 1);
INSERT INTO `role_permission` VALUES (29, 1, 57, 1);
INSERT INTO `role_permission` VALUES (30, 1, 58, 1);
INSERT INTO `role_permission` VALUES (31, 1, 59, 1);
INSERT INTO `role_permission` VALUES (32, 1, 60, 1);
INSERT INTO `role_permission` VALUES (33, 1, 61, 1);
INSERT INTO `role_permission` VALUES (34, 1, 62, 1);
INSERT INTO `role_permission` VALUES (35, 1, 63, 1);
INSERT INTO `role_permission` VALUES (36, 1, 64, 1);
INSERT INTO `role_permission` VALUES (37, 1, 65, 1);
INSERT INTO `role_permission` VALUES (38, 1, 66, 1);
INSERT INTO `role_permission` VALUES (39, 1, 67, 1);
INSERT INTO `role_permission` VALUES (40, 1, 68, 1);
INSERT INTO `role_permission` VALUES (41, 1, 69, 1);
INSERT INTO `role_permission` VALUES (42, 1, 70, 1);
INSERT INTO `role_permission` VALUES (43, 1, 71, 1);
INSERT INTO `role_permission` VALUES (44, 1, 72, 1);
INSERT INTO `role_permission` VALUES (45, 1, 73, 1);
INSERT INTO `role_permission` VALUES (46, 1, 74, 1);
INSERT INTO `role_permission` VALUES (47, 1, 75, 1);
INSERT INTO `role_permission` VALUES (48, 1, 76, 1);
INSERT INTO `role_permission` VALUES (49, 1, 77, 1);
INSERT INTO `role_permission` VALUES (50, 1, 78, 1);
INSERT INTO `role_permission` VALUES (51, 1, 79, 1);
INSERT INTO `role_permission` VALUES (52, 1, 80, 1);
INSERT INTO `role_permission` VALUES (53, 1, 81, 1);
INSERT INTO `role_permission` VALUES (54, 1, 82, 1);
INSERT INTO `role_permission` VALUES (55, 1, 83, 1);
INSERT INTO `role_permission` VALUES (56, 1, 84, 1);
INSERT INTO `role_permission` VALUES (57, 1, 85, 1);
INSERT INTO `role_permission` VALUES (58, 1, 86, 1);
INSERT INTO `role_permission` VALUES (59, 1, 87, 1);
INSERT INTO `role_permission` VALUES (60, 1, 88, 1);
INSERT INTO `role_permission` VALUES (61, 1, 89, 1);
INSERT INTO `role_permission` VALUES (62, 1, 90, 1);
INSERT INTO `role_permission` VALUES (63, 1, 91, 1);
INSERT INTO `role_permission` VALUES (64, 1, 92, 1);
INSERT INTO `role_permission` VALUES (65, 1, 93, 1);
INSERT INTO `role_permission` VALUES (66, 1, 94, 1);
INSERT INTO `role_permission` VALUES (67, 1, 95, 1);
INSERT INTO `role_permission` VALUES (68, 1, 96, 1);
INSERT INTO `role_permission` VALUES (69, 1, 97, 1);
INSERT INTO `role_permission` VALUES (70, 1, 98, 1);
INSERT INTO `role_permission` VALUES (71, 1, 99, 1);
INSERT INTO `role_permission` VALUES (72, 1, 100, 1);
INSERT INTO `role_permission` VALUES (73, 1, 103, 1);
INSERT INTO `role_permission` VALUES (74, 1, 104, 1);
INSERT INTO `role_permission` VALUES (75, 1, 105, 1);
INSERT INTO `role_permission` VALUES (76, 1, 106, 1);
INSERT INTO `role_permission` VALUES (77, 1, 107, 1);
INSERT INTO `role_permission` VALUES (78, 1, 108, 1);
INSERT INTO `role_permission` VALUES (79, 1, 110, 1);
INSERT INTO `role_permission` VALUES (80, 1, 112, 1);
INSERT INTO `role_permission` VALUES (81, 1, 113, 1);
INSERT INTO `role_permission` VALUES (82, 1, 114, 1);
INSERT INTO `role_permission` VALUES (83, 1, 115, 1);
INSERT INTO `role_permission` VALUES (84, 1, 119, 1);


-- 数据字典
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (1, 1, 'trans_type', '1', '空运', NULL, NULL, 1, 1, '2019-11-14 18:00:26', NULL, '2019-11-14 18:00:32', 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (2, 1, 'trans_type', '2', '海运', NULL, '', 1, 1, '2019-11-14 18:00:26', 15, '2022-01-05 11:57:27', 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (3, 2, 'weight_rule', '1', '大于0进0.5KG', NULL, NULL, 1, 1, '2019-11-14 18:00:26', NULL, '2019-11-14 18:00:32', 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (4, 2, 'weight_rule', '2', '大于0进1KG', NULL, NULL, 1, 1, '2019-11-14 18:00:26', NULL, '2019-11-14 18:00:32', 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (5, 2, 'weight_rule', '3', '不进位', NULL, NULL, 1, 1, '2019-12-12 23:51:10', NULL, '2019-12-12 23:51:59', 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (6, NULL, 'country', 'CTRY202001010000000000', '马来西亚', 1, NULL, 1, 1, '2020-03-15 19:07:05', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (7, NULL, 'country', 'CTRY1584873020250', '美国', 3, '', 1, NULL, '2020-03-22 18:30:22', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (8, NULL, 'country', 'CTRY1584873068925', '新加坡', 4, '', 1, NULL, '2020-03-22 18:31:10', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (9, NULL, 'country', 'CTRY1587547787817', '加拿大', 4, '', 1, NULL, '2020-04-22 17:29:47', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (10, NULL, 'country', 'CTRY1589180564554', '澳大利亚', 10, '', 1, NULL, '2020-01-11 15:02:44', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (11, NULL, 'country', 'CTRY1591510246361', '新西兰', 9, '', 1, NULL, '2020-06-07 14:10:46', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (12, NULL, 'country', 'CTRY1591510249480', '英国', 10, '', 1, NULL, '2020-06-07 14:10:49', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (13, NULL, 'country', 'CTRY1615108118526', '马来西亚-东马', 25, '', 1, NULL, '2021-03-07 17:08:38', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (14, NULL, 'country', 'CTRY1616137449130', '芬兰', 26, '', 1, NULL, '2021-03-19 15:04:09', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (15, NULL, 'currency', 'CENY000000001', '人民币', NULL, 'CNY', 1, NULL, NULL, 15, '2022-04-02 14:56:54', 1, '1');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (16, NULL, 'currency', 'CENY1641350187668', '美元', NULL, 'USD', 1, 15, '2022-01-05 10:36:27', 15, '2022-01-05 16:43:39', 1, '6.08');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (17, NULL, 'currency', 'CENY1641350350002', '新加坡币', NULL, 'SGD', 1, 15, '2022-01-05 10:39:10', 15, '2022-02-09 10:04:53', 1, '3.332');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (18, NULL, 'currency', 'CENY1641348452821', '马币', NULL, 'MRY', 1, 15, '2022-01-05 10:07:32', 15, '2022-01-05 16:43:47', 1, '5.11');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (19, NULL, 'currency', 'CENY1641959634810', '英镑', NULL, 'GBP', 1, 15, '2022-01-12 11:53:54', NULL, NULL, 1, '8.85');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (20, NULL, 'backlog_type', 'BLTYPE1644389347663', '业务待办', NULL, '', 1, 15, '2022-02-09 14:49:07', NULL, NULL, 1, '1');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (21, NULL, 'backlog_type', 'BLTYPE1644389588612', '开发待办', NULL, '', 1, 15, '2022-02-09 14:53:08', NULL, NULL, 1, '2');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (22, NULL, 'backlog_type', 'BLTYPE1644401165544', '订单跟踪', NULL, '', 1, 15, '2022-02-09 18:06:05', NULL, NULL, 1, '3');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (23, NULL, 'backlog_type', 'BLTYPE1644457720969', '售后跟踪', NULL, '', 1, 15, '2022-02-10 09:48:40', NULL, NULL, 1, '4');
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (24, NULL, 'integral_type', 'ITGTYPE0000000001', '推广积分', NULL, 'popularize', 1, NULL, NULL, 15, '2022-04-02 14:40:06', 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (25, NULL, 'integral_type', 'ITGTYPE0000000003', '积分抵扣', NULL, 'deductible', 1, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (26, NULL, 'activity_type', 'ACTVTYPE00000000001', '推广返利', NULL, 'toPromote', 1, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (27, NULL, 'activity_type', 'ACTVTYPE00000000002', '扫码注册', NULL, 'scanCode', 1, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (27, NULL, 'activity_type', 'ACTVTYPE00000000003', '关注赠送', NULL, 'subscribeGiving', 1, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (28, NULL, 'integral_type', 'ITGTYPE0000000004', '成员下单', NULL, 'childOrder', 1, NULL, NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (29, NULL, 'activity_type', 'ACTVTYPE1648627590818', '分享链接', NULL, 'shareLink', 1, 15, '2022-03-30 16:06:30', 15, '2022-04-02 11:33:20', 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (30, NULL, 'activity_type', 'ACTVTYPE1648627651150', '分享链接注册', NULL, 'shareLinkRegister', 1, 15, '2022-03-30 16:07:31', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (31, NULL, 'integral_type', 'ITGTYPE1648717128490', '注册积分', NULL, 'registerIntegrals', 1, 15, '2022-03-31 16:58:48', 15, '2022-04-02 11:33:25', 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (32, NULL, 'activity_type', 'ACTVTYPE1648882750915', '618活动', NULL, 'activity618', 1, 15, '2022-04-02 14:59:10', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (33, NULL, 'poster_type', 'POSTTYPE1653026559189', '分享海报', NULL, 'sharePoster', 1, 15, '2022-04-02 14:59:10', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`id`, `sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (34, NULL, 'poster_type', 'POSTTYPE1653029206888', '微信绑定海报', NULL, 'bindingPoster', 1, 15, '2022-04-02 14:59:10', NULL, NULL, 1, NULL);
INSERT INTO `sys_dict_detail`(`sdm_id`, `sdm_code`, `sdd_code`, `sdd_name`, `sdd_seq`, `sdd_remark`, `status`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `is_enable`, `alternate_field`) VALUES (NULL, 'poster_type', 'POSTTYPE1653029206888', '轨迹广告', NULL, 'trajectoryAdvertising', 1, 15, '2022-04-02 14:59:10', NULL, NULL, 1, NULL);

-- 微信自动回复
insert into wechat_reply (status, version, reply_rule, reply_type, reply_content, key_word) values (1, 1, '关注后自动回复', 'text', 'Hi，尊敬的会员：LOGIN_NAME，您好，欢迎关注飞轮国际云仓物流~', '关注后自动回复');
insert into wechat_reply (status, version, reply_rule, reply_type, reply_content, key_word) values (1, 1, '扫码关注后自动回复', 'text', 'Hi，尊敬的会员：LOGIN_NAME，您好，欢迎关注飞轮国际云仓物流~', '扫码关注后自动回复');
insert into wechat_reply (status, version, reply_rule, reply_type, reply_content, key_word) values (1, 1, '已经关注过的', 'text', '
亲爱的会员（LOGIN_NAME）您好，您关注过我们', '已经关注过的');
insert into wechat_reply (status, version, reply_rule, reply_type, reply_content, key_word) values (1, 1, '其他', 'text', '其他', '其他');

-- 插入异常件客服用户
INSERT INTO customer (customer_no, customer_name, gender, phone_number, `status`, login_name, `password`)
VALUES ('CUM2022010100000000000', '异常件客服', 1, '000000', 1, '000000', 'MDAwMDAw')
-- 插入商户/区域仓库
INSERT INTO commercial_area (create_id, create_name, create_time, `status`, version, commercial_area_name, commercial_area_no, country, is_enable)
VALUES (1, '超级管理员', '2022-01-01 00:00:00', 1, 1, '中国总部', 'CA0000000000', '中国', 1)