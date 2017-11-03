package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwClass;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:07:26
 * @describe  类说明
*/
public class XjlDwClassBo {
	// 增加、编辑
	public static XjlDwClass save(XjlDwClass xjlDwClass) {
		if (xjlDwClass.classId != null) {
		}
		if (xjlDwClass.classId == null) {
			xjlDwClass.classId = SeqUtil.maxValue("xjl_dw_class", "class_id");
			xjlDwClass.status = "0AA";
			xjlDwClass.createTime = DateUtil.getNowDate();
		}
		xjlDwClass = xjlDwClass.save();
		return xjlDwClass;
	}
	// 删除
	public static XjlDwClass del(XjlDwClass xjlDwClass) {
		if (xjlDwClass != null) {
			xjlDwClass.status = "0XX";
			xjlDwClass = xjlDwClass.save();
			return xjlDwClass;
		}
		return null;
	}
}