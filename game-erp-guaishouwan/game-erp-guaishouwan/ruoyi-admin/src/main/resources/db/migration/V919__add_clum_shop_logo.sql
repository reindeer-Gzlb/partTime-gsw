CREATE TABLE `sys_shop_logo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `logo_img` varchar(255) DEFAULT NULL COMMENT 'logo',
  `banner_img` varchar(255) DEFAULT NULL COMMENT 'banner',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='logo';