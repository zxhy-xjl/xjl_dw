package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwSubject;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:12:57
 * @describe  类说明
*/
public class XjlDwSubjectBo {
	// 增加、编辑
	public static XjlDwSubject save(XjlDwSubject xjlDwSubject) {
		if (xjlDwSubject.subjectId != null) {
		}
		if (xjlDwSubject.subjectId == null) {
			xjlDwSubject.subjectId = SeqUtil.maxValue("xjl_dw_subject", "subject_id");
			xjlDwSubject.status = "0AA";
			xjlDwSubject.createTime = DateUtil.getNowDate();
		}
		xjlDwSubject = xjlDwSubject.save();
		return xjlDwSubject;
	}
	// 删除
	public static XjlDwSubject del(XjlDwSubject xjlDwSubject) {
		if (xjlDwSubject != null) {
			xjlDwSubject.status = "0XX";
			xjlDwSubject = xjlDwSubject.save();
			return xjlDwSubject;
		}
		return null;
	}
}