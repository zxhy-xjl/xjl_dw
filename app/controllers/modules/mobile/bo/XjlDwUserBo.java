package controllers.modules.mobile.bo;

import play.Logger;
import controllers.comm.BOResult;
import controllers.comm.SessionInfo;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwUser;
import utils.DateUtil;
import utils.SecurityUtil;
import utils.SeqUtil;
import utils.StringUtil;
import utils.UrlHelper;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:13:27
 * @describe  类说明
*/
public class XjlDwUserBo {
	// 增加、编辑
	public static XjlDwUser save(XjlDwUser xjlDwUser) {
		if (xjlDwUser.userId != null) {
		}
		if (xjlDwUser.userId == null) {
			xjlDwUser.userId = SeqUtil.maxValue("xjl_dw_user", "user_id");
			xjlDwUser.status = "0AA";
			xjlDwUser.createTime = DateUtil.getNowDate();
		}
		xjlDwUser = xjlDwUser.save();
		return xjlDwUser;
	}
	// 删除
	public static XjlDwUser del(XjlDwUser xjlDwUser) {
		if (xjlDwUser != null) {
			xjlDwUser.status = "0XX";
			xjlDwUser = xjlDwUser.save();
			return xjlDwUser;
		}
		return null;
	}
	
}