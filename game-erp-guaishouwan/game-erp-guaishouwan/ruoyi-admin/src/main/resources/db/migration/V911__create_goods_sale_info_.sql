CREATE TABLE `goods_sale_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `code` varchar(255) DEFAULT NULL COMMENT '编号',
  `sale_time` timestamp NULL DEFAULT NULL COMMENT '销售时间',
  `sale_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格',
  `after_email` varchar(255) DEFAULT NULL COMMENT '售后email',
  `after_phone` varchar(255) DEFAULT NULL COMMENT '售后手机号',
  `sale_name` varchar(255) DEFAULT NULL COMMENT '买家名字',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COMMENT='出售记录详情表';