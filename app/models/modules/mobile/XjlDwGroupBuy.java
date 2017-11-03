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

import controllers.modules.mobile.bo.XjlDwGroupBuyBo;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.DateUtil;
import utils.StringUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_group_buy")
public class XjlDwGroupBuy extends GenericModel{

	@Id
	@Column(name = "GROUP_BUY_ID")
	public Long groupBuyId;

	@Column(name = "GROUP_BUY_TITLE")
	public String groupBuyTitle;

	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;
	
	@Column(name = "CLASS_ID")
	public Long classId;

	@Column(name = "GROUP_BUY_BEGIN_TIME")
	public Date groupBuyBeginTime;

	@Column(name = "GROUP_BUY_END_TIME")
	public Date groupBuyEndTime;
	
	@Column(name = "GROUP_BUY_STATE")
	public String groupBuyState;
	//状态值，1正常，2关闭
	public static final String groupBuyState_running = "1";
	public static final String groupBuyState_closed = "2";
	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Transient
	public String groupBuyTime;
	@Transient
	public String groupBuyStateInfo;
	
	@Transient
	public List<XjlDwGroupBuyItem> listGroupBuyItem;
	
	public static Map queryXjlDwGroupBuyListByPage(Map<String, String> condition,
			int pageIndex, int pageSize) {
			String sql = "select a.group_buy_id,a.group_buy_title,a.group_buy_begin_time,a.group_buy_end_time,a.group_buy_state ";
			sql += "from xjl_dw_group_buy a ";
			sql +="where a.status='0AA' ";
			//如果有classid，增加classid查询条件
			sql += "[ and a.class_id=l:classId ] ";
			if(StringUtil.isNotEmpty(condition.get("searchKeyWord"))){
				String searchKeyWord = condition.get("searchKeyWord");
				sql += "and a.group_buy_title like '%"+searchKeyWord+"%' ";
			}
			sql += "order by a.group_buy_id desc ";
			
			SQLResult ret = ModelUtils.createSQLResult(condition, sql);
			List<Object[]> retData = ModelUtils.queryData(pageIndex, pageSize, ret);
			List<XjlDwGroupBuy> data =  new ArrayList<XjlDwGroupBuy>();
			XjlDwGroupBuy xjlDwGroupBuy;
			Date nowDate = DateUtil.getNowDate();
			for(Object[]m :retData){
				xjlDwGroupBuy = new XjlDwGroupBuy();
				if(m[0]!=null)
					xjlDwGroupBuy.groupBuyId = StringUtil.getLong(m[0].toString());
				if(m[1]!=null)
					xjlDwGroupBuy.groupBuyTitle = m[1].toString();
				if(m[2]!=null)
					xjlDwGroupBuy.groupBuyBeginTime = DateUtil.parseDate(m[2]);
				if(m[3]!=null)
					xjlDwGroupBuy.groupBuyEndTime = DateUtil.parseDate(m[3]);
				if(m[4]!=null)
					xjlDwGroupBuy.groupBuyState = m[4].toString();
				if(xjlDwGroupBuy.groupBuyBeginTime!=null&&xjlDwGroupBuy.groupBuyEndTime!=null){	
					xjlDwGroupBuy.groupBuyTime=DateUtil.date2String(xjlDwGroupBuy.groupBuyBeginTime,"MM.dd")+"-"+DateUtil.date2String(xjlDwGroupBuy.groupBuyEndTime,"MM.dd");
				}
				if(("2").equals(xjlDwGroupBuy.groupBuyState)){
					xjlDwGroupBuy.groupBuyStateInfo="结束";
				}else if(("1").equals(xjlDwGroupBuy.groupBuyState)){
					if(nowDate.compareTo(xjlDwGroupBuy.groupBuyEndTime)>0){
						xjlDwGroupBuy.groupBuyStateInfo="结束";
					}else{
						xjlDwGroupBuy.groupBuyStateInfo="进行中";
					}
				}
				data.add(xjlDwGroupBuy);
			}
			return ModelUtils.createResultMap(ret, data);
	}
}
