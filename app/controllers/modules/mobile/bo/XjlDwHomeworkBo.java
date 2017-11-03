package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwHomework;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:11:56
 * @describe  类说明
*/
public class XjlDwHomeworkBo {
	// 增加、编辑
	public static XjlDwHomework save(XjlDwHomework xjlDwHomework) {
		if (xjlDwHomework.homeworkId != null) {
		}
		if (xjlDwHomework.homeworkId == null) {
			xjlDwHomework.homeworkId = SeqUtil.maxValue("xjl_dw_homework", "homework_id");
			xjlDwHomework.status = "0AA";
			xjlDwHomework.createTime = DateUtil.getNowDate();
		}
		xjlDwHomework = xjlDwHomework.save();
		return xjlDwHomework;
	}
	// 删除
	public static XjlDwHomework del(XjlDwHomework xjlDwHomework) {
		if (xjlDwHomework != null) {
			xjlDwHomework.status = "0XX";
			xjlDwHomework = xjlDwHomework.save();
			return xjlDwHomework;
		}
		return null;
	}
}