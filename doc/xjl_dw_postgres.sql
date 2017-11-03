/*==============================================================*/
/* DBMS name:      PostgreSQL 9.3.6                             */
/* Created on:     9/25 星期一 14:41:58                            */
/*==============================================================*/


drop index INDEX_ROOT_PARAMS_MASK;

drop table ROOT_PARAMS;

drop table WX_SERVER;

drop table wx_user;

drop table xjl_dw_album;

drop table xjl_dw_album_image;

drop table xjl_dw_album_template;

drop table xjl_dw_article;

drop table xjl_dw_class;

drop table xjl_dw_class_teacher;

drop table xjl_dw_exam;

drop table xjl_dw_exam_grade;

drop table xjl_dw_exam_subject;

drop table xjl_dw_file;

drop table xjl_dw_group_buy;

drop table xjl_dw_group_buy_item;

drop table xjl_dw_group_buy_order;

drop table xjl_dw_homework;

drop table xjl_dw_homework_model;

drop table xjl_dw_notice;

drop table xjl_dw_role;

drop index INDEX_PORTAL_VNO_ID_STATE;

drop table xjl_dw_school;

drop table xjl_dw_student;

drop table xjl_dw_subject;

drop table xjl_dw_teacher;

drop table xjl_dw_user;

drop table xjl_dw_wx_class;

drop table xjl_dw_wx_role;

drop table xjl_dw_wx_student;

/*==============================================================*/
/* Table: ROOT_PARAMS                                           */
/*==============================================================*/
create table ROOT_PARAMS (
   PARAM                NUMERIC              null,
   COMMENTS             VARCHAR(200)         null,
   CURRENT_VALUE        VARCHAR(300)         null,
   MASK                 VARCHAR(20)          null,
   STATE                VARCHAR(3)           null,
   CREATE_TIME          TIMESTAMP            null,
   UPDATE_TIME          TIMESTAMP            null,
   VERSION              NUMERIC              null
);

comment on table ROOT_PARAMS is
'系统参数表';

comment on column ROOT_PARAMS.PARAM is
'编号';

comment on column ROOT_PARAMS.COMMENTS is
'参数描述';

comment on column ROOT_PARAMS.CURRENT_VALUE is
'参数值';

comment on column ROOT_PARAMS.MASK is
'参数标识码';

comment on column ROOT_PARAMS.STATE is
'状态';

comment on column ROOT_PARAMS.CREATE_TIME is
'添加时间';

comment on column ROOT_PARAMS.UPDATE_TIME is
'更新时间';

comment on column ROOT_PARAMS.VERSION is
'更新次数';

INSERT INTO ROOT_PARAMS(PARAM,COMMENTS,CURRENT_VALUE,MASK)
VALUES(20,'网站网址','http://wechat.airclub.xin','WEB_ROOT');
INSERT INTO ROOT_PARAMS(PARAM,COMMENTS,CURRENT_VALUE,MASK)
VALUES(23,'技术支撑','微信团队','TECHNICAL_SUPPORT');
INSERT INTO ROOT_PARAMS(PARAM,COMMENTS,CURRENT_VALUE,MASK)
VALUES(24,'客服在线支持QQ客服：340416553','340416553','ONLINE_QQ');
INSERT INTO ROOT_PARAMS(PARAM,COMMENTS,CURRENT_VALUE,MASK)
VALUES(25,'客服电话','176-0155-1522','ONLINE_TEL');
INSERT INTO ROOT_PARAMS(PARAM,COMMENTS,CURRENT_VALUE,MASK)
VALUES(26,'备案号，示例：苏ICP备05059565号-3','苏ICP备05059565号-3','ICP');
COMMIT;

/*==============================================================*/
/* Index: INDEX_ROOT_PARAMS_MASK                                */
/*==============================================================*/
create  index INDEX_ROOT_PARAMS_MASK on ROOT_PARAMS (
MASK
);

/*==============================================================*/
/* Table: WX_SERVER                                             */
/*==============================================================*/
create table WX_SERVER (
   WX_SERVER_ID         NUMERIC              not null,
   SCHOOL_ID            NUMERIC              null,
   WX_CODE              VARCHAR(15)          not null,
   WX_NAME              VARCHAR(100)         not null,
   WX_QR_CODE           VARCHAR(100)         null,
   APP_ID               VARCHAR(48)          null,
   APP_SECRET           VARCHAR(80)          null,
   ACCESS_TOKEN         VARCHAR(30)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_WX_SERVER primary key (WX_SERVER_ID)
);

comment on table WX_SERVER is
'企业公众号';

comment on column WX_SERVER.SCHOOL_ID is
'企业标识';

comment on column WX_SERVER.WX_CODE is
'原始ID';

comment on column WX_SERVER.WX_NAME is
'微信公众号名称';

comment on column WX_SERVER.WX_QR_CODE is
'公众号二维码';

comment on column WX_SERVER.APP_ID is
'应用ID';

comment on column WX_SERVER.APP_SECRET is
'应用密钥';

comment on column WX_SERVER.ACCESS_TOKEN is
'Access_Token';

comment on column WX_SERVER.STATUS is
'状态
0AA：有效
0XX：无效';

comment on column WX_SERVER.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: wx_user                                               */
/*==============================================================*/
create table wx_user (
   WX_OPEN_ID           VARCHAR(28)          not null,
   OPEN_ID_CHANNCEL     VARCHAR(20)          null,
   SCHOOL_ID            NUMERIC              null,
   TEACHER_ID           NUMERIC              null,
   NICK_NAME            VARCHAR(50)          not null,
   HEAD_IMG_URL         VARCHAR(255)         null,
   SEX                  VARCHAR(1)           null,
   LANGUAGE             VARCHAR(50)          null,
   COUNTRY              VARCHAR(60)          null,
   PROVINCE             VARCHAR(60)          null,
   CITY                 VARCHAR(60)          null,
   WX_PHONE             VARCHAR(20)          null,
   IS_CONCERNED         VARCHAR(1)           null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            null,
   constraint PK_WX_USER primary key (WX_OPEN_ID)
);

comment on table wx_user is
'微信账户表';

comment on column wx_user.OPEN_ID_CHANNCEL is
'获取openid的途径 webgrant：网页授权  subscribe：关注公众号';

comment on column wx_user.WX_PHONE is
'微信电话';

comment on column wx_user.IS_CONCERNED is
'Y:是,N:否';

comment on column wx_user.STATUS is
'状态';

comment on column wx_user.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_album                                          */
/*==============================================================*/
create table xjl_dw_album (
   ALBUM_ID             NUMERIC              not null,
   ALBUM_TEMPLATE_ID    NUMERIC              not null,
   CLASS_ID             NUMERIC              null,
   ALBUM_TITLE          VARCHAR(100)         not null,
   WX_OPEN_ID           VARCHAR(28)          not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_ALBUM primary key (ALBUM_ID)
);

comment on table xjl_dw_album is
'相册表';

comment on column xjl_dw_album.ALBUM_ID is
'相册ID';

comment on column xjl_dw_album.ALBUM_TEMPLATE_ID is
'模板id';

comment on column xjl_dw_album.CLASS_ID is
'班级标识';

comment on column xjl_dw_album.ALBUM_TITLE is
'相册标题';

comment on column xjl_dw_album.WX_OPEN_ID is
'相册创建者';

/*==============================================================*/
/* Table: xjl_dw_album_image                                    */
/*==============================================================*/
create table xjl_dw_album_image (
   ALBUM_IMAGE_ID       NUMERIC              not null,
   FILE_ID              NUMERIC              not null,
   ALBUM_ID             NUMERIC              not null,
   IMAGE_TITLE          VARCHAR(100)         not null,
   IMAGE_ORDER          NUMERIC              not null,
   WX_OPEN_ID           VARCHAR(28)          not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_ALBUM_IMAGE primary key (ALBUM_IMAGE_ID)
);

comment on table xjl_dw_album_image is
'相册图片表';

comment on column xjl_dw_album_image.ALBUM_IMAGE_ID is
'相册图片ID';

comment on column xjl_dw_album_image.FILE_ID is
'文件ID';

comment on column xjl_dw_album_image.ALBUM_ID is
'相册ID';

comment on column xjl_dw_album_image.IMAGE_TITLE is
'图片标题';

comment on column xjl_dw_album_image.IMAGE_ORDER is
'图片排序';

comment on column xjl_dw_album_image.WX_OPEN_ID is
'图片发布者';

/*==============================================================*/
/* Table: xjl_dw_album_template                                 */
/*==============================================================*/
create table xjl_dw_album_template (
   ALBUM_TEMPLATE_ID    NUMERIC              not null,
   SCHOOL_ID            NUMERIC              null,
   ALBUM_TEMPLATE_TITLE VARCHAR(100)         not null,
   ALBUM_TEMPLATE_IMG   VARCHAR(100)         null,
   ALBUM_TEMPLATE_STYLE VARCHAR(50)          null,
   ALBUM_TEMPLATE_IMG_NUM NUMERIC              null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_ALBUM_TEMPLATE primary key (ALBUM_TEMPLATE_ID)
);

comment on table xjl_dw_album_template is
'相册模板表';

comment on column xjl_dw_album_template.ALBUM_TEMPLATE_ID is
'模板id';

comment on column xjl_dw_album_template.SCHOOL_ID is
'企业标识';

comment on column xjl_dw_album_template.ALBUM_TEMPLATE_TITLE is
'模板标题';

comment on column xjl_dw_album_template.ALBUM_TEMPLATE_IMG is
'模板图片';

comment on column xjl_dw_album_template.ALBUM_TEMPLATE_STYLE is
'模板样式';

comment on column xjl_dw_album_template.ALBUM_TEMPLATE_IMG_NUM is
'模板封面图片数量';

comment on column xjl_dw_album_template.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_article                                        */
/*==============================================================*/
create table xjl_dw_article (
   ARTICLE_ID           NUMERIC              not null,
   WX_OPEN_ID           VARCHAR(28)          not null,
   ARTICLE_TITLE        VARCHAR(100)         not null,
   ARTICLE_CONTENT      TEXT                 not null,
   ARTICLE_AUTHOR       VARCHAR(50)          not null,
   ARTICLE_PUBLISH_DATE TIMESTAMP            not null,
   ARTICLE_STATE        VARCHAR(1)           not null,
   CLASS_ID             NUMERIC              null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_ARTICLE primary key (ARTICLE_ID)
);

comment on table xjl_dw_article is
'美文';

comment on column xjl_dw_article.ARTICLE_ID is
'文章id';

comment on column xjl_dw_article.WX_OPEN_ID is
'文章发布人';

comment on column xjl_dw_article.ARTICLE_TITLE is
'文章标题';

comment on column xjl_dw_article.ARTICLE_CONTENT is
'文章内容';

comment on column xjl_dw_article.ARTICLE_AUTHOR is
'文章作者带入微信昵称，可修改';

comment on column xjl_dw_article.ARTICLE_PUBLISH_DATE is
'系统时间
只是记录谁发布的这个
消息）不显示在通知上';

comment on column xjl_dw_article.ARTICLE_STATE is
'0是待审批，1是审批通
过（已发布）2是审批不通过（暂不发布）';

comment on column xjl_dw_article.CLASS_ID is
'班级标识';

comment on column xjl_dw_article.STATUS is
'状态';

comment on column xjl_dw_article.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_class                                          */
/*==============================================================*/
create table xjl_dw_class (
   CLASS_ID             NUMERIC              not null,
   SCHOOL_ID            NUMERIC              null,
   CLASS_NAME           VARCHAR(50)          not null,
   CLASS_CODE           VARCHAR(50)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_CLASS primary key (CLASS_ID)
);

comment on table xjl_dw_class is
'班级表';

comment on column xjl_dw_class.CLASS_ID is
'班级标识';

comment on column xjl_dw_class.SCHOOL_ID is
'企业标识';

comment on column xjl_dw_class.CLASS_NAME is
'班级名称';

comment on column xjl_dw_class.CLASS_CODE is
'班级编码';

comment on column xjl_dw_class.STATUS is
'状态';

comment on column xjl_dw_class.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_class_teacher                                  */
/*==============================================================*/
create table xjl_dw_class_teacher (
   CLASS_TEACHER_ID     NUMERIC              not null,
   TEACHER_ID           NUMERIC              not null,
   CLASS_ID             NUMERIC              not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_CLASS_TEACHER primary key (CLASS_TEACHER_ID)
);

comment on table xjl_dw_class_teacher is
'班级老师关系表';

comment on column xjl_dw_class_teacher.CLASS_ID is
'班级标识';

comment on column xjl_dw_class_teacher.STATUS is
'状态';

comment on column xjl_dw_class_teacher.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_exam                                           */
/*==============================================================*/
create table xjl_dw_exam (
   EXAM_ID              NUMERIC              not null,
   EXAM_TITLE           VARCHAR(100)         not null,
   EXAM_DATE            TIMESTAMP            not null,
   CLASS_ID             NUMERIC              null,
   WX_OPEN_ID           VARCHAR(28)          null,
   UP_CHANNCEL          VARCHAR(20)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_EXAM primary key (EXAM_ID)
);

comment on table xjl_dw_exam is
'考试表';

comment on column xjl_dw_exam.EXAM_ID is
'考试ID';

comment on column xjl_dw_exam.EXAM_TITLE is
'考试标题';

comment on column xjl_dw_exam.EXAM_DATE is
'考试时间';

comment on column xjl_dw_exam.CLASS_ID is
'班级标识';

comment on column xjl_dw_exam.WX_OPEN_ID is
'发布人';

comment on column xjl_dw_exam.UP_CHANNCEL is
'admin：pc后台发布
wechat：微信上发布';

/*==============================================================*/
/* Table: xjl_dw_exam_grade                                     */
/*==============================================================*/
create table xjl_dw_exam_grade (
   EXAM_GRADE_ID        NUMERIC              not null,
   EXAM_ID              NUMERIC              not null,
   SUBJECT_ID           NUMERIC              not null,
   STUDENT_ID           NUMERIC              not null,
   EXAM_GRADE           NUMERIC(10,2)        not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_EXAM_GRADE primary key (EXAM_GRADE_ID)
);

comment on table xjl_dw_exam_grade is
'考试科目成绩表';

comment on column xjl_dw_exam_grade.EXAM_GRADE_ID is
'考试成绩ID';

comment on column xjl_dw_exam_grade.EXAM_ID is
'考试ID';

comment on column xjl_dw_exam_grade.SUBJECT_ID is
'科目id';

comment on column xjl_dw_exam_grade.STUDENT_ID is
'学生标识';

comment on column xjl_dw_exam_grade.EXAM_GRADE is
'考试成绩';

/*==============================================================*/
/* Table: xjl_dw_exam_subject                                   */
/*==============================================================*/
create table xjl_dw_exam_subject (
   EXAM_SUBJECT_ID      NUMERIC              not null,
   EXAM_ID              NUMERIC              not null,
   SUBJECT_ID           NUMERIC              not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_EXAM_SUBJECT primary key (EXAM_SUBJECT_ID)
);

comment on table xjl_dw_exam_subject is
'考试科目表';

comment on column xjl_dw_exam_subject.EXAM_SUBJECT_ID is
'考试科目ID';

comment on column xjl_dw_exam_subject.EXAM_ID is
'考试ID';

comment on column xjl_dw_exam_subject.SUBJECT_ID is
'科目id';

/*==============================================================*/
/* Table: xjl_dw_file                                           */
/*==============================================================*/
create table xjl_dw_file (
   FILE_ID              NUMERIC              not null,
   FILE_NAME            VARCHAR(50)          null,
   FILE_URL             VARCHAR(100)         not null,
   WX_OPEN_ID           VARCHAR(28)          not null,
   FILE_TYPE            VARCHAR(10)          not null,
   SAVE_FOLDER          VARCHAR(50)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   DELETE_TIME          TIMESTAMP            null,
   UP_CHANNEL           VARCHAR(10)          null,
   constraint PK_XJL_DW_FILE primary key (FILE_ID)
);

comment on table xjl_dw_file is
'文件表';

comment on column xjl_dw_file.FILE_ID is
'文件ID';

comment on column xjl_dw_file.FILE_NAME is
'文件名称';

comment on column xjl_dw_file.FILE_URL is
'文件地址';

comment on column xjl_dw_file.WX_OPEN_ID is
'上传人';

comment on column xjl_dw_file.FILE_TYPE is
'文件类型1图片，2视频，3pdf，
4word
5其他';

comment on column xjl_dw_file.SAVE_FOLDER is
'存放的文件夹';

comment on column xjl_dw_file.STATUS is
'状态';

comment on column xjl_dw_file.CREATE_TIME is
'添加时间';

comment on column xjl_dw_file.DELETE_TIME is
'删除时间';

comment on column xjl_dw_file.UP_CHANNEL is
'上传途径';

/*==============================================================*/
/* Table: xjl_dw_group_buy                                      */
/*==============================================================*/
create table xjl_dw_group_buy (
   GROUP_BUY_ID         NUMERIC              not null,
   GROUP_BUY_TITLE      VARCHAR(100)         not null,
   WX_OPEN_ID           VARCHAR(28)          not null,
   CLASS_ID             NUMERIC              null,
   GROUP_BUY_BEGIN_TIME TIMESTAMP            null,
   GROUP_BUY_END_TIME   TIMESTAMP            null,
   GROUP_BUY_STATE      VARCHAR(1)           null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_GROUP_BUY primary key (GROUP_BUY_ID)
);

comment on table xjl_dw_group_buy is
'团购表';

comment on column xjl_dw_group_buy.GROUP_BUY_ID is
'团购id';

comment on column xjl_dw_group_buy.GROUP_BUY_TITLE is
'团购标题';

comment on column xjl_dw_group_buy.WX_OPEN_ID is
'发布人';

comment on column xjl_dw_group_buy.CLASS_ID is
'班级标识';

comment on column xjl_dw_group_buy.GROUP_BUY_BEGIN_TIME is
'开始时间';

comment on column xjl_dw_group_buy.GROUP_BUY_END_TIME is
'截止时间';

comment on column xjl_dw_group_buy.GROUP_BUY_STATE is
'1正常2关闭';

comment on column xjl_dw_group_buy.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_group_buy_item                                 */
/*==============================================================*/
create table xjl_dw_group_buy_item (
   GROUP_ITEM_ID        NUMERIC              not null,
   GROUP_BUY_ID         NUMERIC              not null,
   GROUP_ITEM_TITLE     VARCHAR(100)         not null,
   GROUP_ITEM_PRICE     NUMERIC(10,2)        not null,
   GROUP_ITEM_CONTENT   VARCHAR(200)         null,
   GROUP_ITEM_IMAGE     VARCHAR(100)         null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_GROUP_BUY_ITEM primary key (GROUP_ITEM_ID)
);

comment on table xjl_dw_group_buy_item is
'团购表';

comment on column xjl_dw_group_buy_item.GROUP_ITEM_ID is
'团购条目id';

comment on column xjl_dw_group_buy_item.GROUP_BUY_ID is
'团购id';

comment on column xjl_dw_group_buy_item.GROUP_ITEM_TITLE is
'团购条目标题';

comment on column xjl_dw_group_buy_item.GROUP_ITEM_PRICE is
'团购条目价格';

comment on column xjl_dw_group_buy_item.GROUP_ITEM_CONTENT is
'团购条目内容';

comment on column xjl_dw_group_buy_item.GROUP_ITEM_IMAGE is
'管理录入，图片url地址，
非图片内容';

comment on column xjl_dw_group_buy_item.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_group_buy_order                                */
/*==============================================================*/
create table xjl_dw_group_buy_order (
   GROUP_ORDER_ID       NUMERIC              not null,
   GROUP_BUY_ID         NUMERIC              not null,
   BUY_ITEM_ID          NUMERIC              not null,
   WX_OPEN_ID           VARCHAR(28)          not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_GROUP_BUY_ORDER primary key (GROUP_ORDER_ID)
);

comment on table xjl_dw_group_buy_order is
'订单表';

comment on column xjl_dw_group_buy_order.GROUP_ORDER_ID is
'订单id';

comment on column xjl_dw_group_buy_order.GROUP_BUY_ID is
'团购id';

comment on column xjl_dw_group_buy_order.BUY_ITEM_ID is
'团购条目id';

comment on column xjl_dw_group_buy_order.WX_OPEN_ID is
'团购人id';

comment on column xjl_dw_group_buy_order.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_homework                                       */
/*==============================================================*/
create table xjl_dw_homework (
   HOMEWORK_ID          NUMERIC              not null,
   CLASS_ID             NUMERIC              null,
   HOMEWORK_TITLE       VARCHAR(100)         not null,
   HOMEWORK_CONTENT     VARCHAR(100)         null,
   WX_OPEN_ID           VARCHAR(28)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_HOMEWORK primary key (HOMEWORK_ID)
);

comment on table xjl_dw_homework is
'作业表';

comment on column xjl_dw_homework.HOMEWORK_ID is
'作业id';

comment on column xjl_dw_homework.CLASS_ID is
'班级标识';

comment on column xjl_dw_homework.HOMEWORK_TITLE is
'作业标题';

comment on column xjl_dw_homework.HOMEWORK_CONTENT is
'作业内容';

comment on column xjl_dw_homework.WX_OPEN_ID is
'发布人';

comment on column xjl_dw_homework.STATUS is
'状态';

comment on column xjl_dw_homework.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_homework_model                                 */
/*==============================================================*/
create table xjl_dw_homework_model (
   MODEL_ID             NUMERIC              not null,
   MODEL_TITLE          VARCHAR(100)         not null,
   MODEL_CONTENT        VARCHAR(200)         null,
   HOMEWORK_ID          NUMERIC              not null,
   STUDENT_ID           NUMERIC              not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_HOMEWORK_MODEL primary key (MODEL_ID)
);

comment on table xjl_dw_homework_model is
'作业标榜';

comment on column xjl_dw_homework_model.MODEL_ID is
'标榜id';

comment on column xjl_dw_homework_model.MODEL_TITLE is
'标榜图片';

comment on column xjl_dw_homework_model.MODEL_CONTENT is
'评语';

comment on column xjl_dw_homework_model.HOMEWORK_ID is
'作业标识';

comment on column xjl_dw_homework_model.STUDENT_ID is
'学生标识';

/*==============================================================*/
/* Table: xjl_dw_notice                                         */
/*==============================================================*/
create table xjl_dw_notice (
   NOTICE_ID            NUMERIC              not null,
   NOTICE_TITLE         VARCHAR(100)         not null,
   NOTICE_CONTENT       TEXT                 not null,
   NOTICE_DATE          TIMESTAMP            not null,
   WX_OPEN_ID           VARCHAR(28)          not null,
   CLASS_ID             NUMERIC              null,
   USER_ID              NUMERIC              null,
   UP_CHANNEL           VARCHAR(10)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_NOTICE primary key (NOTICE_ID)
);

comment on table xjl_dw_notice is
'通知表';

comment on column xjl_dw_notice.NOTICE_ID is
'通知id';

comment on column xjl_dw_notice.NOTICE_TITLE is
'通知标题';

comment on column xjl_dw_notice.NOTICE_CONTENT is
'通知内容';

comment on column xjl_dw_notice.NOTICE_DATE is
'通知时间';

comment on column xjl_dw_notice.WX_OPEN_ID is
'发布人
微信的openId,自动生成，
只是记录谁发布的这个
消息）不显示在通知上';

comment on column xjl_dw_notice.CLASS_ID is
'班级标识';

comment on column xjl_dw_notice.USER_ID is
'用户标识';

comment on column xjl_dw_notice.UP_CHANNEL is
'发布途径：后台：admin  微信：wechat';

comment on column xjl_dw_notice.STATUS is
'状态';

comment on column xjl_dw_notice.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_role                                           */
/*==============================================================*/
create table xjl_dw_role (
   ROLE_ID              NUMERIC              not null,
   ROLE_NAME            VARCHAR(50)          not null,
   ROLE_CODE            VARCHAR(50)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_ROLE primary key (ROLE_ID)
);

comment on table xjl_dw_role is
'角色表';

comment on column xjl_dw_role.ROLE_ID is
'角色标识';

comment on column xjl_dw_role.ROLE_NAME is
'角色名称';

comment on column xjl_dw_role.ROLE_CODE is
'角色编码';

comment on column xjl_dw_role.STATUS is
'状态';

comment on column xjl_dw_role.CREATE_TIME is
'添加时间';

INSERT INTO xjl_dw_role (role_id, role_name, role_code, status, create_time) 
VALUES (1, '班级管理员', 'class_admin', '0AA', now());
INSERT INTO xjl_dw_role (role_id, role_name, role_code, status, create_time) 
VALUES (2, '老师', 'teacher', '0AA', now());
INSERT INTO xjl_dw_role (role_id, role_name, role_code, status, create_time) 
VALUES (3, '学生', 'student', '0AA', now());

/*==============================================================*/
/* Table: xjl_dw_school                                         */
/*==============================================================*/
create table xjl_dw_school (
   SCHOOL_ID            NUMERIC              not null,
   SCHOOL_NAME          VARCHAR(100)         not null,
   SCHOOL_CODE          VARCHAR(50)          null,
   SCHOOL_LOGO          VARCHAR(200)         null,
   SCHOOL_TEL           VARCHAR(20)          null,
   SCHOOL_AREA          VARCHAR(100)         null,
   SCHOOL_ADDRESS       VARCHAR(100)         null,
   EFF_DATE             TIMESTAMP            not null,
   EXP_DATE             TIMESTAMP            not null,
   SCHOOL_DOMAIN        VARCHAR(50)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_SCHOOL primary key (SCHOOL_ID)
);

comment on table xjl_dw_school is
'学校表';

comment on column xjl_dw_school.SCHOOL_ID is
'企业标识';

comment on column xjl_dw_school.SCHOOL_NAME is
'企业名称';

comment on column xjl_dw_school.SCHOOL_CODE is
'企业编码';

comment on column xjl_dw_school.SCHOOL_LOGO is
'企业Logo';

comment on column xjl_dw_school.SCHOOL_TEL is
'企业电话';

comment on column xjl_dw_school.SCHOOL_AREA is
'企业所在区域';

comment on column xjl_dw_school.SCHOOL_ADDRESS is
'企业地址';

comment on column xjl_dw_school.EFF_DATE is
'生效时间';

comment on column xjl_dw_school.EXP_DATE is
'失效时间';

comment on column xjl_dw_school.SCHOOL_DOMAIN is
'域名：示例：wechat.airclub.xin';

comment on column xjl_dw_school.STATUS is
'状态
0AA：有效，0XX：失效';

comment on column xjl_dw_school.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Index: INDEX_PORTAL_VNO_ID_STATE                             */
/*==============================================================*/
create  index INDEX_PORTAL_VNO_ID_STATE on xjl_dw_school (
SCHOOL_ID,
STATUS
);

/*==============================================================*/
/* Table: xjl_dw_student                                        */
/*==============================================================*/
create table xjl_dw_student (
   STUDENT_ID           NUMERIC              not null,
   CLASS_ID             NUMERIC              not null,
   STUDENT_NAME         VARCHAR(50)          not null,
   STUDENT_NO           VARCHAR(50)          not null,
   STUDENT_SEX          VARCHAR(1)           not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_TIME          TIMESTAMP            null,
   VERSION              NUMERIC              not null,
   constraint PK_XJL_DW_STUDENT primary key (STUDENT_ID)
);

comment on table xjl_dw_student is
'学生表';

comment on column xjl_dw_student.STUDENT_ID is
'学生标识';

comment on column xjl_dw_student.CLASS_ID is
'班级标识';

comment on column xjl_dw_student.STUDENT_NAME is
'学生姓名';

comment on column xjl_dw_student.STUDENT_NO is
'学生学号';

comment on column xjl_dw_student.STUDENT_SEX is
'男：M
女：F';

comment on column xjl_dw_student.STATUS is
'状态';

comment on column xjl_dw_student.CREATE_TIME is
'添加时间';

comment on column xjl_dw_student.UPDATE_TIME is
'更新时间';

comment on column xjl_dw_student.VERSION is
'更新次数';

/*==============================================================*/
/* Table: xjl_dw_subject                                        */
/*==============================================================*/
create table xjl_dw_subject (
   SUBJECT_ID           NUMERIC              not null,
   SUBJECT_TITLE        VARCHAR(50)          not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_SUBJECT primary key (SUBJECT_ID)
);

comment on table xjl_dw_subject is
'科目表';

comment on column xjl_dw_subject.SUBJECT_ID is
'科目id';

comment on column xjl_dw_subject.SUBJECT_TITLE is
'科目标题';

/*==============================================================*/
/* Table: xjl_dw_teacher                                        */
/*==============================================================*/
create table xjl_dw_teacher (
   TEACHER_ID           NUMERIC              not null,
   TEACHER_NAME         VARCHAR(50)          not null,
   TEACHER_CODE         VARCHAR(50)          not null,
   TEACHER_SEX          VARCHAR(1)           not null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_TEACHER primary key (TEACHER_ID)
);

comment on table xjl_dw_teacher is
'老师表';

comment on column xjl_dw_teacher.TEACHER_SEX is
'男：M
女：F';

comment on column xjl_dw_teacher.STATUS is
'状态';

comment on column xjl_dw_teacher.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_user                                           */
/*==============================================================*/
create table xjl_dw_user (
   USER_ID              NUMERIC              not null,
   USER_NAME            VARCHAR(50)          not null,
   USER_CODE            VARCHAR(50)          not null,
   USER_PWD             VARCHAR(50)          not null,
   USER_ROLE            VARCHAR(1)           null,
   SCHOOL_ID            NUMERIC              null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_USER primary key (USER_ID)
);

comment on table xjl_dw_user is
'后台用户表';

comment on column xjl_dw_user.USER_ID is
'用户标识';

comment on column xjl_dw_user.USER_NAME is
'用户姓名';

comment on column xjl_dw_user.USER_CODE is
'登录账号';

comment on column xjl_dw_user.USER_PWD is
'登录密码';

comment on column xjl_dw_user.USER_ROLE is
'角色：0未知，1家长，2管理员，3老师，S:超级管理员';

comment on column xjl_dw_user.SCHOOL_ID is
'学校标识';

comment on column xjl_dw_user.STATUS is
'状态';

comment on column xjl_dw_user.CREATE_TIME is
'添加时间';

/*==============================================================*/
/* Table: xjl_dw_wx_class                                       */
/*==============================================================*/
create table xjl_dw_wx_class (
   CLASS_WX_ID          NUMERIC              not null,
   CLASS_ID             NUMERIC              null,
   WX_OPEN_ID           VARCHAR(28)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_WX_CLASS primary key (CLASS_WX_ID)
);

comment on table xjl_dw_wx_class is
'微信班级关系表';

comment on column xjl_dw_wx_class.CLASS_WX_ID is
'班级微信标识';

comment on column xjl_dw_wx_class.CLASS_ID is
'班级标识';

comment on column xjl_dw_wx_class.WX_OPEN_ID is
'微信标识';

/*==============================================================*/
/* Table: xjl_dw_wx_role                                        */
/*==============================================================*/
create table xjl_dw_wx_role (
   ROLE_WX_ID           NUMERIC              not null,
   ROLE_ID              NUMERIC              null,
   WX_OPEN_ID           VARCHAR(28)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_WX_ROLE primary key (ROLE_WX_ID)
);

comment on table xjl_dw_wx_role is
'微信角色关系表';

comment on column xjl_dw_wx_role.ROLE_WX_ID is
'角色微信标识';

comment on column xjl_dw_wx_role.ROLE_ID is
'角色标识';

comment on column xjl_dw_wx_role.WX_OPEN_ID is
'微信标识';

/*==============================================================*/
/* Table: xjl_dw_wx_student                                     */
/*==============================================================*/
create table xjl_dw_wx_student (
   STUDENT_WX_ID        NUMERIC              not null,
   STUDENT_ID           NUMERIC              null,
   WX_OPEN_ID           VARCHAR(28)          null,
   STATUS               VARCHAR(3)           not null,
   CREATE_TIME          TIMESTAMP            not null,
   constraint PK_XJL_DW_WX_STUDENT primary key (STUDENT_WX_ID)
);

comment on table xjl_dw_wx_student is
'微信学生关系表';

comment on column xjl_dw_wx_student.STUDENT_WX_ID is
'学生微信标识';

comment on column xjl_dw_wx_student.STUDENT_ID is
'学生标识';

comment on column xjl_dw_wx_student.WX_OPEN_ID is
'微信标识';

alter table WX_SERVER
   add constraint FK_WX_SERVE_REFERENCE_XJL_DW_S foreign key (SCHOOL_ID)
      references xjl_dw_school (SCHOOL_ID)
      on delete restrict on update restrict;

alter table wx_user
   add constraint FK_WX_USER_REFERENCE_XJL_DW_T foreign key (TEACHER_ID)
      references xjl_dw_teacher (TEACHER_ID)
      on delete restrict on update restrict;

alter table wx_user
   add constraint FK_WX_USER_REFERENCE_XJL_DW_S foreign key (SCHOOL_ID)
      references xjl_dw_school (SCHOOL_ID)
      on delete restrict on update restrict;

alter table xjl_dw_album
   add constraint FK_XJL_DW_A_REFERENCE_XJL_DW_A foreign key (ALBUM_TEMPLATE_ID)
      references xjl_dw_album_template (ALBUM_TEMPLATE_ID)
      on delete restrict on update restrict;

alter table xjl_dw_album
   add constraint FK_XJL_DW_A_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_album_image
   add constraint FK_XJL_DW_A_REFERENCE_XJL_DW_F foreign key (FILE_ID)
      references xjl_dw_file (FILE_ID)
      on delete restrict on update restrict;

alter table xjl_dw_album_image
   add constraint FK_XJL_DW_A_REFERENCE_XJL_DW_A foreign key (ALBUM_ID)
      references xjl_dw_album (ALBUM_ID)
      on delete restrict on update restrict;

alter table xjl_dw_album_template
   add constraint FK_XJL_DW_A_REFERENCE_XJL_DW_S foreign key (SCHOOL_ID)
      references xjl_dw_school (SCHOOL_ID)
      on delete restrict on update restrict;

alter table xjl_dw_article
   add constraint FK_XJL_DW_A_REFERENCE_WX_USER foreign key (WX_OPEN_ID)
      references wx_user (WX_OPEN_ID)
      on delete restrict on update restrict;

alter table xjl_dw_article
   add constraint FK_XJL_DW_A_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_class
   add constraint FK_XJL_DW_C_REFERENCE_XJL_DW_S foreign key (SCHOOL_ID)
      references xjl_dw_school (SCHOOL_ID)
      on delete restrict on update restrict;

alter table xjl_dw_class_teacher
   add constraint FK_XJL_DW_C_REFERENCE_XJL_DW_T foreign key (TEACHER_ID)
      references xjl_dw_teacher (TEACHER_ID)
      on delete restrict on update restrict;

alter table xjl_dw_class_teacher
   add constraint FK_XJL_DW_C_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_exam
   add constraint FK_XJL_DW_E_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_exam_grade
   add constraint FK_XJL_DW_E_REFERENCE_XJL_DW_E foreign key (EXAM_ID)
      references xjl_dw_exam (EXAM_ID)
      on delete restrict on update restrict;

alter table xjl_dw_exam_grade
   add constraint FK_XJL_DW_E_REFERENCE_XJL_DW_S2 foreign key (SUBJECT_ID)
      references xjl_dw_subject (SUBJECT_ID)
      on delete restrict on update restrict;

alter table xjl_dw_exam_grade
   add constraint FK_XJL_DW_E_REFERENCE_XJL_DW_S foreign key (STUDENT_ID)
      references xjl_dw_student (STUDENT_ID)
      on delete restrict on update restrict;

alter table xjl_dw_exam_subject
   add constraint FK_XJL_DW_E_REFERENCE_XJL_DW_E foreign key (EXAM_ID)
      references xjl_dw_exam (EXAM_ID)
      on delete restrict on update restrict;

alter table xjl_dw_exam_subject
   add constraint FK_XJL_DW_E_REFERENCE_XJL_DW_S foreign key (SUBJECT_ID)
      references xjl_dw_subject (SUBJECT_ID)
      on delete restrict on update restrict;

alter table xjl_dw_group_buy
   add constraint FK_XJL_DW_G_REFERENCE_WX_USER foreign key (WX_OPEN_ID)
      references wx_user (WX_OPEN_ID)
      on delete restrict on update restrict;

alter table xjl_dw_group_buy
   add constraint FK_XJL_DW_G_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_group_buy_item
   add constraint FK_XJL_DW_G_REFERENCE_XJL_DW_G foreign key (GROUP_BUY_ID)
      references xjl_dw_group_buy (GROUP_BUY_ID)
      on delete restrict on update restrict;

alter table xjl_dw_group_buy_order
   add constraint FK_XJL_DW_G_REFERENCE_XJL_DW_G2 foreign key (GROUP_BUY_ID)
      references xjl_dw_group_buy (GROUP_BUY_ID)
      on delete restrict on update restrict;

alter table xjl_dw_group_buy_order
   add constraint FK_XJL_DW_G_REFERENCE_XJL_DW_G foreign key (BUY_ITEM_ID)
      references xjl_dw_group_buy_item (GROUP_ITEM_ID)
      on delete restrict on update restrict;

alter table xjl_dw_group_buy_order
   add constraint FK_XJL_DW_G_REFERENCE_WX_USER foreign key (WX_OPEN_ID)
      references wx_user (WX_OPEN_ID)
      on delete restrict on update restrict;

alter table xjl_dw_homework
   add constraint FK_XJL_DW_H_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_homework_model
   add constraint FK_XJL_DW_H_REFERENCE_XJL_DW_H foreign key (HOMEWORK_ID)
      references xjl_dw_homework (HOMEWORK_ID)
      on delete restrict on update restrict;

alter table xjl_dw_homework_model
   add constraint FK_XJL_DW_H_REFERENCE_XJL_DW_S foreign key (STUDENT_ID)
      references xjl_dw_student (STUDENT_ID)
      on delete restrict on update restrict;

alter table xjl_dw_notice
   add constraint FK_XJL_DW_N_REFERENCE_WX_USER foreign key (WX_OPEN_ID)
      references wx_user (WX_OPEN_ID)
      on delete restrict on update restrict;

alter table xjl_dw_notice
   add constraint FK_XJL_DW_N_REFERENCE_XJL_DW_U foreign key (USER_ID)
      references xjl_dw_user (USER_ID)
      on delete restrict on update restrict;

alter table xjl_dw_notice
   add constraint FK_XJL_DW_N_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_student
   add constraint FK_XJL_DW_S_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_user
   add constraint FK_XJL_DW_U_REFERENCE_XJL_DW_S foreign key (SCHOOL_ID)
      references xjl_dw_school (SCHOOL_ID)
      on delete restrict on update restrict;

alter table xjl_dw_wx_class
   add constraint FK_XJL_DW_W_REFERENCE_WX_USER foreign key (WX_OPEN_ID)
      references wx_user (WX_OPEN_ID)
      on delete restrict on update restrict;

alter table xjl_dw_wx_class
   add constraint FK_XJL_DW_W_REFERENCE_XJL_DW_C foreign key (CLASS_ID)
      references xjl_dw_class (CLASS_ID)
      on delete restrict on update restrict;

alter table xjl_dw_wx_role
   add constraint FK_XJL_DW_W_REFERENCE_WX_USER foreign key (WX_OPEN_ID)
      references wx_user (WX_OPEN_ID)
      on delete restrict on update restrict;

alter table xjl_dw_wx_role
   add constraint FK_XJL_DW_W_REFERENCE_XJL_DW_R foreign key (ROLE_ID)
      references xjl_dw_role (ROLE_ID)
      on delete restrict on update restrict;

alter table xjl_dw_wx_student
   add constraint FK_XJL_DW_W_REFERENCE_WX_USER foreign key (WX_OPEN_ID)
      references wx_user (WX_OPEN_ID)
      on delete restrict on update restrict;

alter table xjl_dw_wx_student
   add constraint FK_XJL_DW_W_REFERENCE_XJL_DW_S foreign key (STUDENT_ID)
      references xjl_dw_student (STUDENT_ID)
      on delete restrict on update restrict;

