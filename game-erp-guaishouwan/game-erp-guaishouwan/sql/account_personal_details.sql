drop table if exists account_personal_details;

/*==============================================================*/
/* Table: account_personal_details                              */
/*==============================================================*/
create table account_personal_details
(
   id                   int(11) not null AUTO_INCREMENT comment '主键',
   account_id           varchar(32) comment '游戏账号账号表id',
   name                 varchar(10) comment '姓名',
   gender               int comment '性别,0:男,1女',
   idcard               varchar(64) comment '身份证号',
   nation               varchar(10) comment '民族',
   birthday             datetime comment '出生日期',
   address              varchar(128) comment '家庭地址',
   phone                varchar(12) comment '手机号',
   father_phone         varchar(12) comment '父亲手机号',
   mother_phone         varchar(12) comment '母亲手机号',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   update_user          varchar(10) comment '更新人',
   reamrk               varchar(255) comment '备注',
   primary key (id)
)ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC comment '游戏账号审核包赔个人详情表';
