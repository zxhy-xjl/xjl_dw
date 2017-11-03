package controllers;
import java.util.Map;

import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwClass;
import models.modules.mobile.XjlDwStudent;
import models.modules.mobile.XjlDwUser;
import models.modules.mobile.XjlDwWxClass;
import models.modules.mobile.XjlDwWxStudent;
import play.Logger;
import play.cache.Cache;
import play.i18n.Messages;
import controllers.comm.BOResult;
import controllers.comm.BaseController;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.M;
import controllers.modules.mobile.S;
import controllers.modules.mobile.bo.XjlDwWxClassBo;
import controllers.modules.mobile.bo.XjlDwWxStudentBo;
import controllers.modules.mobile.filter.MobileFilter;
import controllers.modules.mobile.bo.WxUserBo;
import controllers.modules.mobile.bo.XjlDwUserBo;
import utils.CommonValidateUtil;
import utils.SecurityUtil;
import utils.StringUtil;
import utils.SysParamUtil;
import controllers.modules.mobile.filter.MobileFilter;

public class LoginService extends BaseController {

	public static void index() {
		// pc登录入口
		render("modules/xjldw/pc/login.html");
	}

	public static void mIndex() {
		  render("modules/xjldw/mobile/main.html");
	}
	public static void addStudent() {
		  render("modules/xjldw/mobile/my/student_bind.html");
	}
	
	public static void login() {
		String usercode = params.get("usercode");
		String passwd = params.get("passwd");
		String openid = params.get("openid");
		String deviceFlag = params.get("flag");
		BOResult boRet;
		boRet = WxUserBo.Login(usercode, passwd, openid);
		Logger.info("+++Login result = %s", boRet.isSuccess());
		if (boRet.isSuccess()) {
			String sessionuserKey=MobileFilter.getSessionKey();
			SessionInfo sessionInfo = boRet.getValue(SessionInfo.class);
			sessionInfo.setDeviceFlag(deviceFlag);
			Cache.delete(sessionuserKey);
			String roleCode ="system";
			Cache.add(sessionuserKey, sessionInfo, "1h");
			if ("PC".equals(sessionInfo.getDeviceFlag())) {
				if("system".equals(roleCode)){
					 // S.manager();
					render("modules/xjldw/pc/class.html");
				}
			}
			
		}else {
			//登录失败
			Logger.info("+++Login result = %s", boRet.getReturnInfo());
			flash.error(boRet.getReturnInfo());
			render("modules/xjldw/pc/login.html");
		}
	}
//	//微信绑定学生
// 	public static void mlogin() {
// 		String userKey=MobileFilter.getSessionKey();
//     	WxUser wxUser =MobileFilter.getWXUser();
//     	if(wxUser!=null&&wxUser.currentClass==null){
//     		XjlDwWxClass xjlDwWxClass=new XjlDwWxClass();
//     		xjlDwWxClass.classId=1l;
//     		xjlDwWxClass.isDefault="Y";
//     		xjlDwWxClass.wxOpenId=wxUser.wxOpenId;
//     		xjlDwWxClass=XjlDwWxClassBo.save(xjlDwWxClass);
//     	}
//     	wxUser = WxUser.getFindByOpenId(wxUser.wxOpenId);
//     	Cache.set(userKey,wxUser);
//     	M.manager();
// 	}
	public static void logout() {
		Cache.delete(MobileFilter.getSessionKey());
		index();
	}

	public static void mlogout() {
		Cache.delete(MobileFilter.getSessionKey());
		mIndex();
	}

	//忘记密码
	public static void forgetPwd(){
		String MOBILE_SYSTEM_NAME = SysParamUtil.getGlobalParamByMask("MOBILE_SYSTEM_NAME");
        renderArgs.put("MOBILE_SYSTEM_NAME", MOBILE_SYSTEM_NAME);
		String TECHNICAL_SUPPORT = SysParamUtil.getGlobalParamByMask("TECHNICAL_SUPPORT");
        renderArgs.put("TECHNICAL_SUPPORT", TECHNICAL_SUPPORT);
		render("modules/zzb/mobile/forgetPwd.html");
	}
	
	/**
	 * 查找某个需要的所有班级
	 */
	public static void queryClassBySchoolId(){
		Map condition = params.allSimple();
	    if (StringUtil.isNotEmpty(params.get("schoolId"))) {
        	condition.put("schoolId", params.get("schoolId"));
        }
		Map ret = XjlDwClass.queryXjlDwClassListByPage(condition,1,100);
		ok(ret);
	}
	/**
	 * 查找某个需要的所有班级
	 */
	public static void queryStudentByClassId(){
		Map condition = params.allSimple();
	    if (StringUtil.isNotEmpty(params.get("classId"))) {
        	condition.put("classId", params.get("classId"));
        }
		Map ret = XjlDwStudent.queryXjlDwStudentListByPage(condition,1,100);
		ok(ret);
	}
	
	//家长根据微信号绑定学生
 	public static void bindStudent() {
 		SessionInfo sessionInfo=MobileFilter.getSessionInfo();
     	WxUser wxUser =sessionInfo.getWxUser();
     	if(wxUser!=null&&wxUser.currentStudent==null){
	 		  if(params.get("studentId") == null) {
	 	        	nok(Messages.get("paramsLose"));
	 	       }
	 		  XjlDwWxStudent xjlDwWxStudent=new XjlDwWxStudent();
	 		  xjlDwWxStudent.studentId=StringUtil.getLong(params.get("studentId"));
	 		  if (params.get("isDefault") != null) {
	 			 xjlDwWxStudent.isDefault = params.get("isDefault");
	 		  }
	 		 xjlDwWxStudent.wxOpenId=wxUser.wxOpenId;
	 		 xjlDwWxStudent=XjlDwWxStudentBo.save(xjlDwWxStudent);
	 	 	 wxUser = WxUser.getFindByOpenId(wxUser.wxOpenId);
	 		 sessionInfo.setWxUser(wxUser);
	 		 Cache.add(MobileFilter.getSessionKey(), sessionInfo);
     	}
     	ok(wxUser);
 	}

}
