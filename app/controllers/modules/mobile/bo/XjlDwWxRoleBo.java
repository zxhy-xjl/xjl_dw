package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwWxRole;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-25 下午02:46:49
 * @describe  类说明
*/
public class XjlDwWxRoleBo {
	// 增加、编辑
	public static XjlDwWxRole save(XjlDwWxRole xjlDwWxRole) {
		if (xjlDwWxRole.roleWxId != null) {
		}
		if (xjlDwWxRole.roleWxId == null) {
			xjlDwWxRole.roleWxId = SeqUtil.maxValue("xjl_dw_wx_role", "role_wx_id");
			xjlDwWxRole.status = "0AA";
			xjlDwWxRole.createTime = DateUtil.getNowDate();
		}
		xjlDwWxRole = xjlDwWxRole.save();
		return xjlDwWxRole;
	}
	// 删除
	public static XjlDwWxRole del(XjlDwWxRole xjlDwWxRole) {
		if (xjlDwWxRole != null) {
			xjlDwWxRole.status = "0XX";
			xjlDwWxRole = xjlDwWxRole.save();
			return xjlDwWxRole;
		}
		return null;
	}
}