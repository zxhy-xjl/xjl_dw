package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwGroupBuyOrder;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:21:23
 * @describe  类说明
*/
public class XjlDwGroupBuyOrderBo {
	// 增加、编辑
	public static XjlDwGroupBuyOrder save(XjlDwGroupBuyOrder xjlDwGroupBuyOrder) {
		if (xjlDwGroupBuyOrder.groupOrderId != null) {
		}
		if (xjlDwGroupBuyOrder.groupOrderId == null) {
			xjlDwGroupBuyOrder.groupOrderId = SeqUtil.maxValue("xjl_dw_group_buy_order", "group_order_id");
			xjlDwGroupBuyOrder.status = "0AA";
			xjlDwGroupBuyOrder.createTime = DateUtil.getNowDate();
		}
		xjlDwGroupBuyOrder = xjlDwGroupBuyOrder.save();
		return xjlDwGroupBuyOrder;
	}
	// 删除
	public static XjlDwGroupBuyOrder del(XjlDwGroupBuyOrder xjlDwGroupBuyOrder) {
		if (xjlDwGroupBuyOrder != null) {
			xjlDwGroupBuyOrder.status = "0XX";
			xjlDwGroupBuyOrder = xjlDwGroupBuyOrder.save();
			return xjlDwGroupBuyOrder;
		}
		return null;
	}
	//删除当前用户在某个团购中的订单
	public static int delByGroupIdAndUserId(Long groupBuyId,String wxOpenId,Long studentId){
		return XjlDwGroupBuyOrder.delByGroupIdAndUserId(groupBuyId, wxOpenId,studentId);
	}
}