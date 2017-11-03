package controllers.modules.mobile;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwArticle;
import models.modules.mobile.XjlDwGroupBuy;
import models.modules.mobile.XjlDwNotice;
import models.modules.mobile.XjlDwWxClass;
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
public class S extends MobileFilter {
	/**
	 * 到首页
	 */
	 public static void manager() {
		   render("modules/xjldw/pc/class.html");
	 } 

}

