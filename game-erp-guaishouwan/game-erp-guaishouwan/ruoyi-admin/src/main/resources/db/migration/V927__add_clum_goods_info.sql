alter table `goods_info` add column `bind_email` varchar(255) DEFAULT ''  COMMENT '绑定邮箱';
alter table `goods_info` add column `is_on_special_offer` int(3) DEFAULT 0  COMMENT '是否特价 0 默认  1 特价';
alter table `goods_info` add column `is_guarantee` int(3) DEFAULT 0  COMMENT '是否需要包赔 0不需要 1 需要';
