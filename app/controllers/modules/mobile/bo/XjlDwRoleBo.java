package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwRole;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-25 下午02:47:16
 * @describe  类说明
*/
public class XjlDwRoleBo {
	// 增加、编辑
	public static XjlDwRole save(XjlDwRole xjlDwRole) {
		if (xjlDwRole.roleId != null) {
		}
		if (xjlDwRole.roleId == null) {
			xjlDwRole.roleId = SeqUtil.maxValue("xjl_dw_role", "role_id");
			xjlDwRole.status = "0AA";
			xjlDwRole.createTime = DateUtil.getNowDate();
		}
		xjlDwRole = xjlDwRole.save();
		return xjlDwRole;
	}
	// 删除
	public static XjlDwRole del(XjlDwRole xjlDwRole) {
		if (xjlDwRole != null) {
			xjlDwRole.status = "0XX";
			xjlDwRole = xjlDwRole.save();
			return xjlDwRole;
		}
		return null;
	}
}