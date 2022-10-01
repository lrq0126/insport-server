alter table `goods` modify `length` double(10,2) DEFAULT NULL comment '长度，单位：cm';
alter table goods modify `width` double(10,2) DEFAULT NULL comment '宽度，单位：cm';
alter table goods modify `height` double(10,2) DEFAULT NULL comment '高度，单位：cm';

alter table `customer_pack` modify `length` double(10,2) DEFAULT NULL comment '长度，单位：cm';
alter table customer_pack modify `width` double(10,2) DEFAULT NULL comment '宽度，单位：cm';
alter table customer_pack modify `height` double(10,2) DEFAULT NULL comment '高度，单位：cm';