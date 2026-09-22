ALTER TABLE account_info ADD recovery_name VARCHAR(30) DEFAULT NULL  COMMENT '回收客服名称';

ALTER TABLE account_info ADD verify_name VARCHAR(30)  DEFAULT NULL COMMENT '审核包赔客服名称';