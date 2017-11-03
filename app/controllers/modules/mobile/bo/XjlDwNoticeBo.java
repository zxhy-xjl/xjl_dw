package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwNotice;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-18 下午01:35:19
 * @describe  类说明
*/
public class XjlDwNoticeBo {
	// 增加、编辑
	public static XjlDwNotice save(XjlDwNotice xjlDwNotice) {
		if (xjlDwNotice.noticeId != null) {
		}
		if (xjlDwNotice.noticeId == null) {
			xjlDwNotice.noticeId = SeqUtil.maxValue("xjl_dw_notice", "notice_id");
			xjlDwNotice.status = "0AA";
			xjlDwNotice.noticeDate = DateUtil.getNowDate();
			xjlDwNotice.createTime = DateUtil.getNowDate();
		}
		xjlDwNotice = xjlDwNotice.save();
		return xjlDwNotice;
	}
	// 删除
	public static XjlDwNotice del(XjlDwNotice xjlDwNotice) {
		if (xjlDwNotice != null) {
			xjlDwNotice.status = "0XX";
			xjlDwNotice = xjlDwNotice.save();
			return xjlDwNotice;
		}
		return null;
	}
}