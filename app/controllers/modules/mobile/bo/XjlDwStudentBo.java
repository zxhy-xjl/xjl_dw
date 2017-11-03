package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwStudent;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:12:43
 * @describe  类说明
*/
public class XjlDwStudentBo {
	// 增加、编辑
	public static XjlDwStudent save(XjlDwStudent xjlDwStudent) {
		if (xjlDwStudent.studentId != null) {
		}
		if (xjlDwStudent.studentId == null) {
			xjlDwStudent.studentId = SeqUtil.maxValue("xjl_dw_student", "student_id");
			xjlDwStudent.status = "0AA";
			xjlDwStudent.createTime = DateUtil.getNowDate();
		}
		xjlDwStudent = xjlDwStudent.save();
		return xjlDwStudent;
	}
	// 删除
	public static XjlDwStudent del(XjlDwStudent xjlDwStudent) {
		if (xjlDwStudent != null) {
			xjlDwStudent.status = "0XX";
			xjlDwStudent = xjlDwStudent.save();
			return xjlDwStudent;
		}
		return null;
	}
}