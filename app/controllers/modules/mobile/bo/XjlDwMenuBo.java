package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwMenu;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-28 上午10:15:34
 * @describe  类说明
*/
public class XjlDwMenuBo {
	// 增加、编辑
	public static XjlDwMenu save(XjlDwMenu xjlDwMenu) {
		if (xjlDwMenu.menuId != null) {
		}
		if (xjlDwMenu.menuId == null) {
			xjlDwMenu.menuId = SeqUtil.maxValue("xjl_dw_menu", "menu_id");
			xjlDwMenu.status = "0AA";
			xjlDwMenu.createTime = DateUtil.getNowDate();
		}
		xjlDwMenu = xjlDwMenu.save();
		return xjlDwMenu;
	}
	// 删除
	public static XjlDwMenu del(XjlDwMenu xjlDwMenu) {
		if (xjlDwMenu != null) {
			xjlDwMenu.status = "0XX";
			xjlDwMenu = xjlDwMenu.save();
			return xjlDwMenu;
		}
		return null;
	}
}