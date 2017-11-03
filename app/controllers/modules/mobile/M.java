package controllers.modules.mobile;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwArticle;
import models.modules.mobile.XjlDwGroupBuy;
import models.modules.mobile.XjlDwNotice;
import models.modules.mobile.XjlDwWxClass;
import play.Logger;
import play.cache.Cache;
import play.i18n.Messages;
import utils.SysParamUtil;
import controllers.comm.BaseController;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.bo.XjlDwWxClassBo;
import controllers.modules.mobile.filter.MobileFilter;
import controllers.modules.mobile.bo.WxUserBo;
/**
 * 我的 视图转换器
 * @author lilisheng
 *
 */
public class M extends MobileFilter {
	/**
	 * 到首页
	 */
	 public static void manager() {
		   render("modules/xjldw/mobile/main.html");
	 } 
	 public static void studentList() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
	    render("modules/xjldw/mobile/my/student_list.html");
	 }
	 /**
	  * 绑定学生页面
	  */
    public static void studentAdd() {
    	WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
        render("modules/xjldw/mobile/my/student_add.html");
    }
    public static void userEdit(){
    	WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
    	render("modules/xjldw/mobile/my/user_edit.html");
    }
    /**
     * 个人信息页面（菜单）
     */
    public static void myInfo() {
    	WxUser wxUser =  getWXUser();
    	Logger.debug("wxUser.nickName", wxUser.nickName);
    	Logger.debug("wxUser.className", wxUser.currentClass.className);
		renderArgs.put("wxUser",wxUser);
        render("modules/xjldw/mobile/my/person_info.html");
    }
    /**
     * 我的团购页面（菜单）
     */
    public static void myGroupBuy() {
    	WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
        render("modules/xjldw/mobile/my/group_buy.html");
    }
    /**
     * 我的成绩单（菜单）
     */
    public static void mySubject() {
    	WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
        render("modules/xjldw/mobile/my/subject.html");
    }

}

