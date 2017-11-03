package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwGroupBuyItem;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:20:39
 * @describe  类说明
*/
public class XjlDwGroupBuyItemBo {
	// 增加、编辑
	public static XjlDwGroupBuyItem save(XjlDwGroupBuyItem xjlDwGroupBuyItem) {
		if (xjlDwGroupBuyItem.groupItemId != null) {
		}
		if (xjlDwGroupBuyItem.groupItemId == null) {
			xjlDwGroupBuyItem.groupItemId = SeqUtil.maxValue("xjl_dw_group_buy_item", "group_item_id");
			xjlDwGroupBuyItem.status = "0AA";
			xjlDwGroupBuyItem.createTime = DateUtil.getNowDate();
		}
		xjlDwGroupBuyItem = xjlDwGroupBuyItem.save();
		return xjlDwGroupBuyItem;
	}
	// 删除
	public static XjlDwGroupBuyItem del(XjlDwGroupBuyItem xjlDwGroupBuyItem) {
		if (xjlDwGroupBuyItem != null) {
			xjlDwGroupBuyItem.status = "0XX";
			xjlDwGroupBuyItem = xjlDwGroupBuyItem.save();
			return xjlDwGroupBuyItem;
		}
		return null;
	}
}