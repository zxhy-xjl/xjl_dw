package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwExamGrade;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:10:00
 * @describe  类说明
*/
public class XjlDwExamGradeBo {
	// 增加、编辑
	public static XjlDwExamGrade save(XjlDwExamGrade xjlDwExamGrade) {
		if (xjlDwExamGrade.examGradeId != null) {
		}
		if (xjlDwExamGrade.examGradeId == null) {
			xjlDwExamGrade.examGradeId = SeqUtil.maxValue("xjl_dw_exam_grade", "exam_grade_id");
			xjlDwExamGrade.status = "0AA";
			xjlDwExamGrade.createTime = DateUtil.getNowDate();
		}
		xjlDwExamGrade = xjlDwExamGrade.save();
		return xjlDwExamGrade;
	}
	// 删除
	public static XjlDwExamGrade del(XjlDwExamGrade xjlDwExamGrade) {
		if (xjlDwExamGrade != null) {
			xjlDwExamGrade.status = "0XX";
			xjlDwExamGrade = xjlDwExamGrade.save();
			return xjlDwExamGrade;
		}
		return null;
	}
}