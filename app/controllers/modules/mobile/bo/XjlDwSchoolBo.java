package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwSchool;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 下午03:09:09
 * @describe  类说明
*/
public class XjlDwSchoolBo {
	// 增加、编辑
	public static XjlDwSchool save(XjlDwSchool xjlDwSchool) {
		if (xjlDwSchool.schoolId != null) {
		}
		if (xjlDwSchool.schoolId == null) {
			xjlDwSchool.schoolId = SeqUtil.maxValue("xjl_dw_school", "school_id");
			xjlDwSchool.status = "0AA";
			xjlDwSchool.createTime = DateUtil.getNowDate();
		}
		xjlDwSchool = xjlDwSchool.save();
		return xjlDwSchool;
	}
	// 删除
	public static XjlDwSchool del(XjlDwSchool xjlDwSchool) {
		if (xjlDwSchool != null) {
			xjlDwSchool.status = "0XX";
			xjlDwSchool = xjlDwSchool.save();
			return xjlDwSchool;
		}
		return null;
	}
}