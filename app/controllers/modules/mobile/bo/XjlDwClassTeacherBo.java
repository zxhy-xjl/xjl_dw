package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwClassTeacher;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:09:20
 * @describe  类说明
*/
public class XjlDwClassTeacherBo {
	// 增加、编辑
	public static XjlDwClassTeacher save(XjlDwClassTeacher xjlDwClassTeacher) {
		if (xjlDwClassTeacher.classTeacherId != null) {
		}
		if (xjlDwClassTeacher.classTeacherId == null) {
			xjlDwClassTeacher.classTeacherId = SeqUtil.maxValue("xjl_dw_class_teacher", "class_teacher_id");
			xjlDwClassTeacher.status = "0AA";
			xjlDwClassTeacher.createTime = DateUtil.getNowDate();
		}
		xjlDwClassTeacher = xjlDwClassTeacher.save();
		return xjlDwClassTeacher;
	}
	// 删除
	public static XjlDwClassTeacher del(XjlDwClassTeacher xjlDwClassTeacher) {
		if (xjlDwClassTeacher != null) {
			xjlDwClassTeacher.status = "0XX";
			xjlDwClassTeacher = xjlDwClassTeacher.save();
			return xjlDwClassTeacher;
		}
		return null;
	}
}