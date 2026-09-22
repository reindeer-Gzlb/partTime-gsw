CREATE TABLE `check_real_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `carrier` varchar(255) DEFAULT NULL COMMENT 'carrier',
  `input_mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `input_name` varchar(255) DEFAULT NULL COMMENT '实名',
  `result_message` varchar(255) DEFAULT NULL COMMENT 'result_message',
  `result_code` varchar(255) DEFAULT NULL COMMENT 'result_code',
  `state` varchar(255) DEFAULT NULL COMMENT 'state',
  `request_id` varchar(255) DEFAULT NULL COMMENT 'request_id',
  `status` varchar(255) DEFAULT NULL COMMENT 'status',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实名记录';