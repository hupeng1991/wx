create database wx;
use wx;

--账号表
DROP TABLE IF EXISTS acc_account;
create table acc_account(
   acc_no int primary key  AUTO_INCREMENT not null comment '主键',
   acc_name varchar(20) not null comment '用户名',
   acc_password varchar(32) comment'账号密码',
   email varchar(20) not null comment '邮箱',
   head_img varchar(50) not null comment '用户头像',
   type_code varchar(4) not null comment '账号类型',
   isdeleted varchar(1) not null default 'N' comment '是否删除'
);