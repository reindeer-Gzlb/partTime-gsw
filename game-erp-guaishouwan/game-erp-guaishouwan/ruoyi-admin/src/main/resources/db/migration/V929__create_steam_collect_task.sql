CREATE TABLE `steam_collect_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `steam_account` varchar(255) DEFAULT NULL COMMENT 'steam帐号',
  `steam_email_req` varchar(1024) DEFAULT NULL COMMENT 'steam邮箱链接',
  `steam_email` varchar(255) DEFAULT NULL COMMENT 'steam邮箱',
  `steam_phone` varchar(255) DEFAULT NULL COMMENT 'steam手机号',
  `remark` varchar(255) DEFAULT NULL COMMENT 'steam备注',
  `status` int(3) DEFAULT '1' COMMENT '任务状态 1 正常 2 异常 3 回收',
  `email_status` int(3) DEFAULT '1' COMMENT '邮箱状态 1 正常 2 邮箱为空 3 邮箱变化',
  `phone_status` int(3) DEFAULT '1' COMMENT '手机状态 1 正常 2 手机为空 3 手机变化',
  `last_time` timestamp NULL DEFAULT NULL COMMENT '最后执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='steam任务执行表';