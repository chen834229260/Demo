CREATE TABLE `menu_permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
  `name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '菜单名称',
  `href` varchar(256) DEFAULT NULL COMMENT '菜单连接',
  `permission` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '权限',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单',
  `child_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


CREATE TABLE `a_permission` (
  `id` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO a_permission VALUES ('1', '/user', 'user:user');
INSERT INTO a_permission VALUES ('2', '/user/add', 'user:add');
INSERT INTO a_permission VALUES ('3', '/user/delete', 'user:delete');

CREATE TABLE `a_role` (
  `id` int(11) NOT NULL,
  `name` varchar(32) CHARACTER SET latin1 DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

a_role."NAME" IS '角色名称';
a_role."MEMO" IS '角色描述';

INSERT INTO a_role VALUES ('1', 'admin', '超级管理员');
INSERT INTO a_role VALUES ('2', 'test', '测试账户');


CREATE TABLE `a_role_permission` (
  `rid` int(10) DEFAULT NULL,
  `pid` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

a_role_permission."RID" IS '角色id';
a_role_permission."PID" IS '权限id';


INSERT INTO a_role_permission VALUES ('1', '2');
INSERT INTO a_role_permission VALUES ('1', '3');
INSERT INTO a_role_permission VALUES ('2', '1');
INSERT INTO a_role_permission VALUES ('1', '1');


CREATE TABLE `a_user` (
  `id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `passwd` varchar(128) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


a_user."USERNAME" IS '用户名';
a_user."PASSWD" IS '密码';
a_user."picture" IS '头像url',
a_user."CREATE_TIME" IS '创建时间';
a_user."STATUS" IS '是否有效 1：有效  0：锁定';


INSERT INTO a_user VALUES ('2', 'tester', '243e29429b340192700677d48c09d992',pictureurl, TO_DATE('2017-12-11 17:20:21', 'YYYY-MM-DD HH24:MI:SS'), '1');
INSERT INTO a_user VALUES ('1', 'admin', '42ee25d1e43e9f57119a00d0a39e5250','pictureurl', TO_DATE('2017-12-11 10:52:48', 'YYYY-MM-DD HH24:MI:SS'), '1');


CREATE TABLE `a_user_role` (
  `USER_ID` int(10) DEFAULT NULL,
  `rid` int(10) DEFAULT NULL	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

a_user_role."USER_ID" IS '用户id';
a_user_role IS '角色id';


INSERT INTO a_user_role VALUES ('1', '1');
INSERT INTO a_user_role VALUES ('2', '2');