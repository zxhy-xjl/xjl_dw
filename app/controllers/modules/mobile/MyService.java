package controllers.modules.mobile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwAlbum;
import models.modules.mobile.XjlDwAlbumTemplate;
import models.modules.mobile.XjlDwArticle;
import models.modules.mobile.XjlDwClass;
import models.modules.mobile.XjlDwGroupBuy;
import models.modules.mobile.XjlDwGroupBuyItem;
import models.modules.mobile.XjlDwNotice;
import models.modules.mobile.XjlDwWxClass;
import models.modules.mobile.XjlDwStudent;
import models.modules.mobile.XjlDwWxStudent;

import play.cache.Cache;
import play.i18n.Messages;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.bo.WxUserBo;
import controllers.modules.mobile.bo.XjlDwArticleBo;
import controllers.modules.mobile.bo.XjlDwGroupBuyBo;
import controllers.modules.mobile.bo.XjlDwGroupBuyItemBo;
import controllers.modules.mobile.bo.XjlDwWxStudentBo;
import controllers.modules.mobile.filter.MobileFilter;
import controllers.modules.mobile.bo.XjlDwNoticeBo;
import utils.DateUtil;
import utils.StringUtil;

/**
 * 我的控制器 个人信息/我的团购/我的成绩
 * 
 * @author lilisheng
 * 
 */
public class MyService extends MobileFilter {
	/**
	 * 查询个人信息
	 */
	public static void queryPersonInfo() {
		WxUser wxUser = getWXUser();
		ok(wxUser);
	}

	/**
	 * 保存绑定手机号码
	 */
	public static void saveBindPhone() {
		WxUser wxUser = getWXUser();
		if (StringUtil.isNotEmpty(params.get("wxPhone"))) {
			wxUser.wxPhone = params.get("wxPhone");
		}
		ok(WxUserBo.save(wxUser));
	}

	/**
	 * 查找已经绑定的学生，这个对家长适用
	 */
	public static void queryBindStudent() {
		WxUser wxUser = getWXUser();
		Map condition = params.allSimple();
		condition.put("wxOpenId", wxUser.wxOpenId);
		Map ret = XjlDwWxStudent.queryXjlDwWxStudentListByPage(condition, 1,100);
		ok(ret);
	}

	/**
	 * 查找某个学校的所有班级
	 */
	public static void queryClassBySchoolId() {
		Map condition = params.allSimple();
		if (StringUtil.isNotEmpty(params.get("schoolId"))) {
			condition.put("schoolId", params.get("schoolId"));
		}
		Map ret = XjlDwClass.queryXjlDwClassListByPage(condition, 1, 100);
		ok(ret);
	}

	/**
	 * 查找某个班级的所有学生
	 */
	public static void queryStudentByClassId() {
		Map condition = params.allSimple();
		if (StringUtil.isNotEmpty(params.get("classId"))) {
			condition.put("classId", params.get("classId"));
		}
		Map ret = XjlDwStudent.queryXjlDwStudentListByPage(condition, 1, 100);
		ok(ret);
	}

	/**
	 * 绑定学生
	 */
	public static void saveBindStudent() {
		WxUser wxUser = getWXUser();
		XjlDwWxStudent wxStudent = new XjlDwWxStudent();
		wxStudent.studentId = Long.valueOf(params.get("studentId"));
		wxStudent.wxOpenId = wxUser.wxOpenId;
		ok(XjlDwWxStudentBo.save(wxStudent));
	}

	// 家长根据微信号绑定学生
	public static void bindStudent() {
		WxUser wxUser = getWXUser();
		if (params.get("studentId") == null) {
			nok(Messages.get("paramsLose"));
		}
		XjlDwWxStudent xjlDwWxStudent = new XjlDwWxStudent();
		xjlDwWxStudent.studentId = StringUtil.getLong(params.get("studentId"));
		if (params.get("isDefault") != null) {
			xjlDwWxStudent.isDefault = params.get("isDefault");
		}
		xjlDwWxStudent.wxOpenId = wxUser.wxOpenId;
		xjlDwWxStudent = XjlDwWxStudentBo.save(xjlDwWxStudent);
		ok(xjlDwWxStudent);
	}
	/**
	 * 查询已经绑定的班级，这个对老师适用
	 */
	public static void queryBindClass(){
		WxUser wxUser = getWXUser();
		Map ret = XjlDwWxClass.getByOpenId(wxUser.wxOpenId);
		ok(ret);
	}

	// 家长绑定默认学生
	public static void setDefaultStudent() {
	  XjlDwWxStudent xjlDwWxStudent = new XjlDwWxStudent();
	  if (StringUtil.isNotEmpty(params.get("studentWxId"))) {
            long studentWxId = StringUtil.getLong(params.get("studentWxId"));
            xjlDwWxStudent = XjlDwWxStudent.findById(studentWxId);
            if (xjlDwWxStudent == null) {
                nok("查询不到该条记录 ，请查看");
            }
        }

	  Object [] paramObject = {xjlDwWxStudent.wxOpenId,xjlDwWxStudent.studentWxId};
	  List<XjlDwWxStudent> studentList=XjlDwWxStudent.find("from XjlDwWxStudent where status='0AA' and wxOpenId=? and studentWxId!=? ", paramObject).fetch();
	  XjlDwWxStudent xjlDwWxStudent2;
	  for(XjlDwWxStudent item : studentList){
		  xjlDwWxStudent2=XjlDwWxStudent.findById(item.studentWxId);
		  if(xjlDwWxStudent2!=null){
			  xjlDwWxStudent2.isDefault="N";
			  xjlDwWxStudent2=XjlDwWxStudentBo.save(xjlDwWxStudent2);  
			  if(xjlDwWxStudent2==null){
				  nok("绑定默认学生失败！");
			  }
		  }
	  }
	  
	    xjlDwWxStudent.isDefault="Y";
		xjlDwWxStudent = XjlDwWxStudentBo.save(xjlDwWxStudent);
		if(xjlDwWxStudent!=null){
			SessionInfo sessionInfo=MobileFilter.getSessionInfo();
	     	 WxUser wxUser =sessionInfo.getWxUser();
			 wxUser = WxUser.getFindByOpenId(wxUser.wxOpenId);
			 xjlDwWxStudent.student=wxUser.currentStudent;
	 		 xjlDwWxStudent.dwClass=wxUser.currentClass;
	 		 sessionInfo.setWxUser(wxUser);
	 		 Cache.add(MobileFilter.getSessionKey(), sessionInfo);
		}
		ok(xjlDwWxStudent);
	}

}
