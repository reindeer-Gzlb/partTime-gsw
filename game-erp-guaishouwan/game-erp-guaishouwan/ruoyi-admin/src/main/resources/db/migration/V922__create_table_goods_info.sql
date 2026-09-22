alter table `goods_info` add column `down_price` decimal (10,2) DEFAULT null  COMMENT '最低处售价';
alter table `goods_info` add column `quanqiu_account` varchar (255) DEFAULT null  COMMENT '全球账户';
alter table `goods_info` add column `quanqiu_password` varchar (255) DEFAULT null  COMMENT '全球密码';
alter table `goods_info` add column `quanqiu_email` varchar (255) DEFAULT null  COMMENT '全球邮箱';
alter table `goods_info` add column `quanqiu_email_password` varchar (255) DEFAULT null  COMMENT '全球邮箱密码';
alter table `goods_info` add column `quanqiu_remark` varchar (255) DEFAULT null  COMMENT '全球备注';
alter table `goods_info` add column `look_count` int (11) DEFAULT null  COMMENT '浏览量';

