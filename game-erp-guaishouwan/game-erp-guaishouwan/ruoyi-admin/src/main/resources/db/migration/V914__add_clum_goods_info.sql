alter table `goods_info` add recovery_first_time timestamp  NULL DEFAULT NULL COMMENT '一手回收时间';
alter table `goods_info` add game_third_id varchar(255)  NULL DEFAULT NULL COMMENT '三方游戏id';
alter table `goods_info` add to_platform varchar(255)  NULL DEFAULT NULL COMMENT '上架平台';

