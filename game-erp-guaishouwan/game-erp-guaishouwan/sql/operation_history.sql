drop table if exists operation_history;

/*==============================================================*/
/* Table: operation_history                                     */
/*==============================================================*/
CREATE TABLE `operation_history` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint(22) DEFAULT NULL COMMENT '游戏id,account表主键',
  `operator` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作人',
  `operat_time` datetime DEFAULT NULL COMMENT '操作时间',
  `status` int(2) DEFAULT NULL COMMENT '操作类型:1、审核包赔,2、上架审核',
  `reason` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='操作历史记录表';