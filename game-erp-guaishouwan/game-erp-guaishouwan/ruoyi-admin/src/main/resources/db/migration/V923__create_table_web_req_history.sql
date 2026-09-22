CREATE TABLE `web_req_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '添加时间',
  `page_path` varchar(255) DEFAULT NULL COMMENT '页面路径',
  `resource` varchar(255) DEFAULT NULL COMMENT '来源地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户端请求记录';