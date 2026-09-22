alter table `game_info` add carrier_type varchar (255)  NULL DEFAULT '4' COMMENT '运营商类型  game_carrier表的id  1 腾讯 2网易  3米哈游 4其他';

alter table `qq_group` add column `game_id` int(4) DEFAULT NULL COMMENT '游戏id';
alter table `qq_group` add column `game_name` VARCHAR (255) DEFAULT NULL COMMENT '游戏name';
alter table `qq_group` add column `type_resource` int(4) DEFAULT 1  COMMENT '1 主站  2商户端';
alter table `qq_group` add column `sort` int(4) DEFAULT 0  COMMENT '排序';

alter table `sys_notice` add column `type_resource` int(4) DEFAULT 1  COMMENT '1 主站  2商户端';
alter table `sys_notice` add column `create_id` int(11) DEFAULT 1  COMMENT '创建人';

alter table `index_resources` add column `jump` varchar (255) DEFAULT ''  COMMENT '跳转地址';
alter table `index_resources` add column `type_resource` int(4) DEFAULT 1  COMMENT '1 主站  2商户端';

alter table `check_account` add column `type_resource` int(4) DEFAULT 1  COMMENT '1 主站  2商户端';
alter table `check_account` add column `sort` int(4) DEFAULT 0  COMMENT '排序';
