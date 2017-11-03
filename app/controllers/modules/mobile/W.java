package controllers.modules.mobile;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwArticle;
import models.modules.mobile.XjlDwExam;
import models.modules.mobile.XjlDwGroupBuy;
import models.modules.mobile.XjlDwHomework;
import models.modules.mobile.XjlDwNotice;
import models.modules.mobile.XjlDwSubject;
import models.modules.mobile.XjlDwWxClass;
import play.Logger;
import play.cache.Cache;
import play.i18n.Messages;
import utils.SysParamUtil;

import java.util.HashMap;
import java.util.Map;

import controllers.comm.BaseController;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.bo.XjlDwWxClassBo;
import controllers.modules.mobile.filter.MobileFilter;
import controllers.modules.mobile.bo.WxUserBo;
/**
 * 作业 视图转换器
 * @author lilisheng
 *
 */
public class W extends MobileFilter {
	/**
	 * 到首页
	 */
	 public static void manager() {
		   render("modules/xjldw/mobile/main.html");
	 } 
	 //成绩单列表
	 public static void examList() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
	    render("modules/xjldw/mobile/work/exam_list.html");
	 }
	//成绩单发布
	 public static void examAdd() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
	    render("modules/xjldw/mobile/work/exam_add.html");
	 }
	//成绩单详情
	 public static void examDetail() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
		XjlDwExam exam = XjlDwExam.findById(Long.parseLong(params.get("examId")));
		renderArgs.put("exam", exam);
	    render("modules/xjldw/mobile/work/exam_detail.html");
	 }
	//作业列表
	 public static void homeworkList() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
	    render("modules/xjldw/mobile/work/homework_list.html");
	 }
	//作业发布
	 public static void homeworkAdd() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
	    render("modules/xjldw/mobile/work/homework_add.html");
	 }
	//作业详情
	 public static void homeworkDetail() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
		XjlDwHomework homework = XjlDwHomework.findById(Long.parseLong(params.get("homeworkId")));
		XjlDwSubject subject = XjlDwSubject.findById(homework.subjectId);
		renderArgs.put("subjectTitle", subject == null ? "" : subject.subjectTitle);
		renderArgs.put("homework", homework);
	    render("modules/xjldw/mobile/work/homework_detail.html");
	 }
	//作业发布标榜
	 public static void homeworkAddRemark() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
		renderArgs.put("homeworkId", params.get("homeworkId"));
	    render("modules/xjldw/mobile/work/homework_remark.html");
	 }
}

