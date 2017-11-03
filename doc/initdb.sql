INSERT INTO xjl_dw_school(school_id,school_name,eff_date,exp_date,school_domain,status,create_time ) 
VALUES (1, '信息工程学校', now(),now()+interval '30 years','shopmall.airclub.xin','0AA',now());

INSERT INTO wx_server(wx_server_id,school_id,wx_code,wx_name,app_id,app_secret,status,create_time ) 
VALUES (1, 1,'gh_00e09da4fab0','德诺商城','wx5f29a981bf8c03c2','706c26dab5d53f81517c4414cbfff565','0AA',now());

INSERT INTO xjl_dw_role (role_id, role_name, role_code,school_id, status, create_time) 
VALUES (1, '班级管理员', 'class_admin',1, '0AA', now());
INSERT INTO xjl_dw_role (role_id, role_name, role_code,school_id, status, create_time) 
VALUES (2, '老师', 'teacher',1, '0AA', now());
INSERT INTO xjl_dw_role (role_id, role_name, role_code, school_id,status, create_time) 
VALUES (3, '家长', 'parent',1, '0AA', now());
INSERT INTO xjl_dw_role (role_id, role_name, role_code, school_id,status, create_time) 
VALUES (4, '系统管理员', 'system',1, '0AA', now());

insert into xjl_dw_wx_role(role_wx_id,role_id,wx_open_id,status,create_time)
values(1,1,'oxh64jkHZeWtbUYc2AMqDc0HiJZg','0AA',now());


INSERT INTO xjl_dw_menu (menu_id, menu_name, menu_url, menu_level,menu_order, parent_menu_id, status, create_time, menu_logo) 
VALUES (1, '用户管理', null, 1, 1, null, '0AA', NULL, 'm1');
INSERT INTO xjl_dw_menu (menu_id, menu_name, menu_url, menu_level,menu_order, parent_menu_id, status, create_time, menu_logo) 
VALUES (2, '用户管理', 'wxuser_list', 2, 1, 1, '0AA', NULL,'m3');


INSERT INTO xjl_dw_role_menu (role_menu_id, menu_id, role_id, create_time) 
VALUES (1, 1, 4, NULL);
INSERT INTO xjl_dw_role_menu (role_menu_id, menu_id, role_id, create_time) 
VALUES (2, 2, 4, NULL);


INSERT INTO xjl_dw_menu (menu_id, menu_name, menu_url, menu_level,menu_order, parent_menu_id, status, create_time, menu_logo) 
VALUES (3, '活动管理', null, 1, 1, null, '0AA', NULL, 'm1');
INSERT INTO xjl_dw_menu (menu_id, menu_name, menu_url, menu_level,menu_order, parent_menu_id, status, create_time, menu_logo) 
VALUES (4, '活动管理', 'wxuser_list', 2, 1, 6, '0AA', NULL,'m3');

INSERT INTO xjl_dw_role_menu (role_menu_id, menu_id, role_id, create_time) 
VALUES (3, 3, 1, NULL);
INSERT INTO xjl_dw_role_menu (role_menu_id, menu_id, role_id, create_time) 
VALUES (4, 4, 1, NULL);
insert into xjl_dw_subject(subject_id, subject_title, status,create_time)
values(1,'语文','0AA',now());
insert into xjl_dw_subject(subject_id, subject_title, status,create_time)
values(2,'数学','0AA',now());
insert into xjl_dw_subject(subject_id, subject_title, status,create_time)
values(3,'英语','0AA',now());