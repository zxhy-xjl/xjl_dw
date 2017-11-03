package controllers.modules.mobile.bo;

import java.util.Date;

import models.modules.mobile.XjlDwGroupBuy;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:11:02
 * @describe  类说明
*/
public class XjlDwGroupBuyBo {
	// 增加、编辑
	public static XjlDwGroupBuy save(XjlDwGroupBuy xjlDwGroupBuy) {
		if (xjlDwGroupBuy.groupBuyId != null) {
		}
		if (xjlDwGroupBuy.groupBuyId == null) {
			xjlDwGroupBuy.groupBuyId = SeqUtil.maxValue("xjl_dw_group_buy", "group_buy_id");
			xjlDwGroupBuy.status = "0AA";
			xjlDwGroupBuy.groupBuyBeginTime= DateUtil.getNowDate();
			xjlDwGroupBuy.createTime = DateUtil.getNowDate();
		}
		xjlDwGroupBuy = xjlDwGroupBuy.save();
		return xjlDwGroupBuy;
	}
	// 删除
	public static XjlDwGroupBuy del(XjlDwGroupBuy xjlDwGroupBuy) {
		if (xjlDwGroupBuy != null) {
			xjlDwGroupBuy.status = "0XX";
			xjlDwGroupBuy = xjlDwGroupBuy.save();
			return xjlDwGroupBuy;
		}
		return null;
	}
	//关闭团购
	public static void close(Long groupBuyId){
		XjlDwGroupBuy xjlDwGroupBuy = XjlDwGroupBuy.findById(groupBuyId);
		close(xjlDwGroupBuy);
	}
	public static void close(XjlDwGroupBuy xjlDwGroupBuy){
		xjlDwGroupBuy.groupBuyState = XjlDwGroupBuy.groupBuyState_closed;
		xjlDwGroupBuy.groupBuyEndTime = DateUtil.getNowDate();
		save(xjlDwGroupBuy);
	}
	public static void checkState(XjlDwGroupBuy xjlDwGroupBuy){
		Date nowDate = DateUtil.getNowDate();
		if(("2").equals(xjlDwGroupBuy.groupBuyState)){
			xjlDwGroupBuy.groupBuyStateInfo="结束";
		}else if(("1").equals(xjlDwGroupBuy.groupBuyState)){
			if(nowDate.compareTo(xjlDwGroupBuy.groupBuyEndTime)>0){
				//如果发现时间自动到了，需要更新数据状态
				XjlDwGroupBuyBo.close(xjlDwGroupBuy.groupBuyId);
				//更新当前数据状态
				xjlDwGroupBuy.groupBuyState = XjlDwGroupBuy.groupBuyState_closed;
				xjlDwGroupBuy.groupBuyStateInfo="结束";
			} else {
				xjlDwGroupBuy.groupBuyStateInfo="进行中";
			}
		}
	}
}