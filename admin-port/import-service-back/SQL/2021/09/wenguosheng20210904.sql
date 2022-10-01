-- 轨迹添加内部外部标识符
ALTER TABLE `trajectory_log`
ADD COLUMN `is_inside`  int(2) ZEROFILL NULL COMMENT '是否内部轨迹：0否，1是' AFTER `create_time`;
