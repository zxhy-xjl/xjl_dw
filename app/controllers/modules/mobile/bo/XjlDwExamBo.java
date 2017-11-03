package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwExam;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:09:41
 * @describe  类说明
*/
public class XjlDwExamBo {
	// 增加、编辑
	public static XjlDwExam save(XjlDwExam xjlDwExam) {
		if (xjlDwExam.examId != null) {
		}
		if (xjlDwExam.examId == null) {
			xjlDwExam.examId = SeqUtil.maxValue("xjl_dw_exam", "exam_id");
			xjlDwExam.status = "0AA";
			xjlDwExam.createTime = DateUtil.getNowDate();
		}
		xjlDwExam = xjlDwExam.save();
		return xjlDwExam;
	}
	// 删除
	public static XjlDwExam del(XjlDwExam xjlDwExam) {
		if (xjlDwExam != null) {
			xjlDwExam.status = "0XX";
			xjlDwExam = xjlDwExam.save();
			return xjlDwExam;
		}
		return null;
	}
	
}