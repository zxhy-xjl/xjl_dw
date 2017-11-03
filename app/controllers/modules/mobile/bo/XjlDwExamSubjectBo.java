package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwExamSubject;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:18:22
 * @describe  类说明
*/
public class XjlDwExamSubjectBo {
	// 增加、编辑
	public static XjlDwExamSubject save(XjlDwExamSubject xjlDwExamSubject) {
		if (xjlDwExamSubject.examSubjectId != null) {
		}
		if (xjlDwExamSubject.examSubjectId == null) {
			xjlDwExamSubject.examSubjectId = SeqUtil.maxValue("xjl_dw_exam_subject", "EXAM_SUBJECT_ID");
			xjlDwExamSubject.status = "0AA";
			xjlDwExamSubject.createTime = DateUtil.getNowDate();
		}
		xjlDwExamSubject = xjlDwExamSubject.save();
		return xjlDwExamSubject;
	}
	// 删除
	public static XjlDwExamSubject del(XjlDwExamSubject xjlDwExamSubject) {
		if (xjlDwExamSubject != null) {
			xjlDwExamSubject.status = "0XX";
			xjlDwExamSubject = xjlDwExamSubject.save();
			return xjlDwExamSubject;
		}
		return null;
	}
	/**
	 * 删除考试科目
	 * @param examId
	 * @return
	 */
	public static int delByExam(Long examId){
		return XjlDwExamSubject.deleteByExam(examId);
	}
	/**
	 * 添加一个新的考试科目
	 * @param examId
	 * @param subjectId
	 * @return
	 */
	public static XjlDwExamSubject add(Long examId, Long subjectId){
		XjlDwExamSubject examSubject = new XjlDwExamSubject();
		examSubject.examId = examId;
		examSubject.subjectId = subjectId;
		return save(examSubject);
	}
}