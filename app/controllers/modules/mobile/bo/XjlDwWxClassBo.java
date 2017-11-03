package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwWxClass;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-25 下午02:45:41
 * @describe  类说明
*/
public class XjlDwWxClassBo {
	// 增加、编辑
	public static XjlDwWxClass save(XjlDwWxClass xjlDwWxClass) {
		if (xjlDwWxClass.classWxId != null) {
		}
		if (xjlDwWxClass.classWxId == null) {
			xjlDwWxClass.classWxId = SeqUtil.maxValue("xjl_dw_wx_class", "class_wx_id");
			xjlDwWxClass.status = "0AA";
			xjlDwWxClass.createTime = DateUtil.getNowDate();
		}
		xjlDwWxClass = xjlDwWxClass.save();
		return xjlDwWxClass;
	}
	// 删除
	public static XjlDwWxClass del(XjlDwWxClass xjlDwWxClass) {
		if (xjlDwWxClass != null) {
			xjlDwWxClass.status = "0XX";
			xjlDwWxClass = xjlDwWxClass.save();
			return xjlDwWxClass;
		}
		return null;
	}
}