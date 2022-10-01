insert into permission(`describe`, `parent_id`, `level`, `sort_no`) values ('代理申请打包',  37, 2, 4);


-- 添加拼团管理权限开始
  -- 第一步，插入一级菜单
insert into permission(`describe`, `level`, `sort_no`) values ('拼团管理', 1, 11);

  -- 第二步， 获取拼团一级菜单的ID
  insert into permission(`describe`, `parent_id`, `level`, `sort_no`) values ('申请拼团',  __, 2, 1);
  insert into permission(`describe`, `parent_id`, `level`, `sort_no`) values ('拼团中',  __, 2, 2);
  insert into permission(`describe`, `parent_id`, `level`, `sort_no`) values ('已成团',  __, 2, 3);
  insert into permission(`describe`, `parent_id`, `level`, `sort_no`) values ('已失效',  __, 2, 4);
-- 添加拼团管理权限结束