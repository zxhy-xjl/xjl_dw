package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwHomeworkModel;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:22:00
 * @describe  类说明
*/
public class XjlDwHomeworkModelBo {
	// 增加、编辑
	public static XjlDwHomeworkModel save(XjlDwHomeworkModel xjlDwHomeworkModel) {
		if (xjlDwHomeworkModel.modelId != null) {
		}
		if (xjlDwHomeworkModel.modelId == null) {
			xjlDwHomeworkModel.modelId = SeqUtil.maxValue("xjl_dw_homework_model", "model_id");
			xjlDwHomeworkModel.status = "0AA";
			xjlDwHomeworkModel.createTime = DateUtil.getNowDate();
		}
		xjlDwHomeworkModel = xjlDwHomeworkModel.save();
		return xjlDwHomeworkModel;
	}
	// 删除
	public static XjlDwHomeworkModel del(XjlDwHomeworkModel xjlDwHomeworkModel) {
		if (xjlDwHomeworkModel != null) {
			xjlDwHomeworkModel.status = "0XX";
			xjlDwHomeworkModel = xjlDwHomeworkModel.save();
			return xjlDwHomeworkModel;
		}
		return null;
	}
}