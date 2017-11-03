package models.modules.mobile;
import java.sql.*;
import java.util.Date;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import play.Logger;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.StringUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_group_buy_order")
public class XjlDwGroupBuyOrder extends GenericModel{

	@Id
	@Column(name = "GROUP_ORDER_ID")
	public Long groupOrderId;

	@Column(name = "GROUP_BUY_ID")
	public Long groupBuyId;

	@Column(name = "GROUP_ITEM_ID")
	public Long groupItemId;

	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;
	
	@Column(name = "STUDENT_ID")
	public Long studentId;
	
	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Transient
	public String orderPrice;
	
	@Transient
	public String orderNum;
	
	@Transient
	public String nickName;

	public static Map queryXjlDwGroupBuyOrderListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_group_buy_order a ";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwGroupBuyOrder> data = ModelUtils.queryData(pageIndex, pageSize, ret, XjlDwGroupBuyOrder.class);
		return ModelUtils.createResultMap(ret, data);
	}
	/**
	 * 判断某一个人在某个团购中是否有订单
	 * @param groupBuyId
	 * @param wxOpenId
	 * @return
	 */
	public static boolean hasOrder(Long groupBuyId, String wxOpenId,Long studentId){
		String sql = "select count(1) ";
		sql += "from xjl_dw_group_buy_order a where a.status='0AA' [ and GROUP_BUY_ID = l:groupBuyId ] [ and WX_OPEN_ID=:wxOpenId ] [ and student_id=l:studentId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		condition.put("wxOpenId", wxOpenId);
		condition.put("studentId", String.valueOf(studentId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object> data = ModelUtils.queryData(ret);
		Logger.info("data.get(0)："+ data.get(0));
		return NumberUtils.toLong(data.get(0).toString())>0;
	}
	/**
	 * 判断某个用户是否购买了某个商品
	 * @param groupBuyId
	 * @param ItemId
	 * @param wxOpenId
	 * @return
	 */
	public static boolean hasOrderItem(Long groupBuyId, Long groupItemId, String wxOpenId, Long studentId){
		String sql = "select count(1) ";
		sql += "from xjl_dw_group_buy_order a where a.status='0AA' [ and GROUP_BUY_ID = l:groupBuyId ]  [ and GROUP_ITEM_ID = l:groupItemId ] [ and WX_OPEN_ID=:wxOpenId] [ and student_id=l:studentId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		condition.put("groupItemId", String.valueOf(groupItemId));
		condition.put("wxOpenId", wxOpenId);
		condition.put("studentId", String.valueOf(studentId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object> data = ModelUtils.queryData(ret);
		Logger.info("data.get(0)："+ data.get(0));
		return NumberUtils.toLong(data.get(0).toString())>0;
	}
	/**
	 * 根据团购号和当前用户id，获取当前用户在该团购中团购的清单内容
	 * @param groupBuyId
	 * @param wxOpenId
	 * @return
	 */
	public static List<XjlDwGroupBuyOrder> queryByGroupIdAndOpenId(Long groupBuyId, String wxOpenId, Long studentId){
		String sql = "select * ";
		sql += "from xjl_dw_group_buy_order a where a.status='0AA' [ and GROUP_BUY_ID = l:groupBuyId ] [ and WX_OPEN_ID=:wxOpenId ] [ and student_id=l:studentId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		condition.put("wxOpenId", wxOpenId);
		condition.put("studentId", String.valueOf(studentId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwGroupBuyOrder> data = ModelUtils.queryData(ret,XjlDwGroupBuyOrder.class);
		return data;
	}
	/**
	 * 根据团购号和学生id，获取当前学生在该团购中团购的清单内容
	 * 因为一个学生可能会被多个微信用户关注，也就是有可能买多次，所以这里按照学生维度进行统计
	 * @param groupBuyId
	 * @param wxOpenId
	 * @return
	 */
	public static List<XjlDwGroupBuyOrder> queryByGroupIdAndStudentId(Long groupBuyId, Long studentId){
		String sql = "select * ";
		sql += "from xjl_dw_group_buy_order a where a.status='0AA' [ and GROUP_BUY_ID = l:groupBuyId ] [ and student_id=l:studentId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		condition.put("studentId", String.valueOf(studentId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwGroupBuyOrder> data = ModelUtils.queryData(ret,XjlDwGroupBuyOrder.class);
		return data;
	}
	/**
	 * 根据团购号和当前用户id，删除当前用户在该团购中团购的清单内容
	 * @param groupBuyId
	 * @param wxOpenId
	 * @return
	 */
	public static int delByGroupIdAndUserId(Long groupBuyId, String wxOpenId, Long studentId){
		if (groupBuyId == null || StringUtils.isEmpty(wxOpenId)){
			throw new RuntimeException("团购和用户不能为空");
		}
		String sql = "delete from xjl_dw_group_buy_order where [ GROUP_BUY_ID = l:groupBuyId ] [ and WX_OPEN_ID=:wxOpenId ] [ and student_id=l:studentId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		condition.put("wxOpenId", wxOpenId);
		condition.put("studentId", String.valueOf(studentId));
		return ModelUtils.executeDelete(condition, sql);
	}
	
	/**
	 * 计算某个团购项目的总参与人数
	 * @param groupBuyId
	 * @param wxOpenId
	 * @return
	 */
	public static String totalBuyer(Long groupBuyId){
		String sql = "select count(DISTINCT a.wx_open_id) ";
		sql += "from xjl_dw_group_buy_order a where a.status='0AA' [ and GROUP_BUY_ID = l:groupBuyId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object> data = ModelUtils.queryData(ret);
		Logger.info("data.get(0)："+ data.get(0));
		if (data.get(0) == null){
			return "0";
		}
		return data.get(0).toString();
	}
	/**
	 * 得到某个团购条目的购买这数量
	 * @param groupBuyId
	 * @param groupItemId
	 * @return
	 */
	public static String totalItemBuyer(Long groupBuyId,Long groupItemId){
		String sql = "select count(DISTINCT a.wx_open_id) ";
		sql += "from xjl_dw_group_buy_order a where a.status='0AA' [ and GROUP_BUY_ID = l:groupBuyId ] [ and GROUP_ITEM_ID = l:groupItemId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		condition.put("groupItemId", String.valueOf(groupItemId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object> data = ModelUtils.queryData(ret);
		Logger.info("data.get(0)："+ data.get(0));
		if (data.get(0) == null){
			return "0";
		}
		return data.get(0).toString();
	}
	
	/**
	 * 计算某个团购项目的购买总金额
	 * @param groupBuyId
	 * @param wxOpenId
	 * @return
	 */
	public static String totalAmount(Long groupBuyId){
		String sql = "select sum(i.group_item_price) ";
		sql += "from xjl_dw_group_buy_order o " ;
		sql += "left JOIN xjl_dw_group_buy_item i on o.group_item_id=i.group_item_id ";
		sql += "where o.status='0AA' AND i.status='0AA' [ and o.GROUP_BUY_ID = l:groupBuyId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object> data = ModelUtils.queryData(ret);
		Logger.info("data.get(0)："+ data.get(0));
		if (data.get(0) == null){
			return "0";
		}
		return data.get(0).toString();
	}
	
	/**
	 * 根据购买人查询购买数量
	 * @param groupBuyId
	 * @param wxOpenId
	 * @return
	 */
	public static List<XjlDwGroupBuyOrder> queryBuyOrderBuyer(Long groupBuyId){
		String sql = "select sum(i.group_item_price),count(1) ,o.wx_open_id ";
		sql += "from xjl_dw_group_buy_order o " ;
		sql += "left JOIN xjl_dw_group_buy_item i on o.group_item_id=i.group_item_id ";
		sql += "left join wx_user u on o.wx_open_id=u.wx_open_id ";
		sql += "where o.status='0AA' AND i.status='0AA' [ and o.GROUP_BUY_ID = l:groupBuyId ] ";
		sql += "group by o.wx_open_id";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("groupBuyId", String.valueOf(groupBuyId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object[]> retData = ModelUtils.queryData(ret);
		
		List<XjlDwGroupBuyOrder> data =  new ArrayList<XjlDwGroupBuyOrder>();
		XjlDwGroupBuyOrder xjlDwGroupBuyOrder;
		for(Object[]m :retData){
			xjlDwGroupBuyOrder = new XjlDwGroupBuyOrder();
			if(m[0]!=null){
				xjlDwGroupBuyOrder.orderPrice =m[0].toString();
			}
			if(m[1]!=null)
				xjlDwGroupBuyOrder.orderNum = m[1].toString();
			if(m[2]!=null)
				xjlDwGroupBuyOrder.wxOpenId = m[2].toString();
			
			data.add(xjlDwGroupBuyOrder);
		}
		return data;
	}
}
