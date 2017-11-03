package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwParentCommittee;
import models.modules.mobile.XjlDwWxRole;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-25 下午02:46:49
 * @describe  类说明
*/
public class XjlDwParentCommitteeBo {
	// 增加、编辑
	public static XjlDwParentCommittee save(XjlDwParentCommittee parentCommittee) {
		if (parentCommittee.parentCommitteeId != null) {
		}
		if (parentCommittee.parentCommitteeId == null) {
			parentCommittee.status = "0AA";
			parentCommittee.createTime = DateUtil.getNowDate();
		}
		parentCommittee = parentCommittee.save();
		return parentCommittee;
	}
	// 删除
	public static XjlDwParentCommittee del(XjlDwParentCommittee parentCommittee) {
		if (parentCommittee != null) {
			parentCommittee.status = "0XX";
			parentCommittee = parentCommittee.save();
			return parentCommittee;
		}
		return null;
	}
}