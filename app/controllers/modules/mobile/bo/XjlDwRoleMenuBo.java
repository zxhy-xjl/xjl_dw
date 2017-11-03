package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwRoleMenu;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-28 上午10:15:59
 * @describe  类说明
*/
public class XjlDwRoleMenuBo {
	// 增加、编辑
	public static XjlDwRoleMenu save(XjlDwRoleMenu xjlDwRoleMenu) {
		if (xjlDwRoleMenu.roleMenuId != null) {
		}
		if (xjlDwRoleMenu.roleMenuId == null) {
			xjlDwRoleMenu.roleMenuId = SeqUtil.maxValue("xjl_dw_role_menu", "role_menu_id");
			xjlDwRoleMenu.createTime = DateUtil.getNowDate();
		}
		xjlDwRoleMenu = xjlDwRoleMenu.save();
		return xjlDwRoleMenu;
	}
	// 删除
	public static XjlDwRoleMenu del(XjlDwRoleMenu xjlDwRoleMenu) {
		if (xjlDwRoleMenu != null) {
			xjlDwRoleMenu = xjlDwRoleMenu.save();
			return xjlDwRoleMenu;
		}
		return null;
	}
}