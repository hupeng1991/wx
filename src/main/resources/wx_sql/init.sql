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

--用户备忘录
DROP TABLE IF EXISTS acc_myremark;
create table acc_myremark(
 remark_no int primary key auto_increment not null comment '主键',
 title varchar(200) not null comment '主题',
 description text,
 create_time datetime not null comment '创建时间',
 acc_no int not null comment '所属账户id',
 acc_name varchar(20) comment '所属账户名称',
 FOREIGN KEY(acc_no) REFERENCES acc_account(acc_no)
);