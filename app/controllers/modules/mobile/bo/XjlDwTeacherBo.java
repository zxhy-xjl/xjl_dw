package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwTeacher;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:13:15
 * @describe  类说明
*/
public class XjlDwTeacherBo {
	// 增加、编辑
	public static XjlDwTeacher save(XjlDwTeacher xjlDwTeacher) {
		if (xjlDwTeacher.teacherId != null) {
		}
		if (xjlDwTeacher.teacherId == null) {
			xjlDwTeacher.teacherId = SeqUtil.maxValue("xjl_dw_teacher", "teacher_id");
			xjlDwTeacher.status = "0AA";
			xjlDwTeacher.createTime = DateUtil.getNowDate();
		}
		xjlDwTeacher = xjlDwTeacher.save();
		return xjlDwTeacher;
	}
	// 删除
	public static XjlDwTeacher del(XjlDwTeacher xjlDwTeacher) {
		if (xjlDwTeacher != null) {
			xjlDwTeacher.status = "0XX";
			xjlDwTeacher = xjlDwTeacher.save();
			return xjlDwTeacher;
		}
		return null;
	}
}