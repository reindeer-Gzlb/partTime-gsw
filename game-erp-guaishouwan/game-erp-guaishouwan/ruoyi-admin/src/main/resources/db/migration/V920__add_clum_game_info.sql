alter table `check_account` add column `game_names` varchar(255) DEFAULT ''  COMMENT '游戏';
alter table `check_account` add column `worktype` int(11) DEFAULT 1  COMMENT ' 工作类型  1、白班 2、夜班';
alter table `check_account` add column `work_state` int (11) DEFAULT 2  COMMENT '1上班 2下班';
