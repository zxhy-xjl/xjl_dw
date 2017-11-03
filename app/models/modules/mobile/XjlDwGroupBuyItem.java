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

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_group_buy_item")
public class XjlDwGroupBuyItem extends GenericModel{

	@Id
	@Column(name = "GROUP_ITEM_ID")
	public Long groupItemId;

	@Column(name = "GROUP_BUY_ID")
	public Long groupBuyId;

	@Column(name = "GROUP_ITEM_TITLE")
	public String groupItemTitle;

	@Column(name = "GROUP_ITEM_PRICE")
	public Double groupItemPrice;

	@Column(name = "GROUP_ITEM_CONTENT")
	public String groupItemContent;

	@Column(name = "GROUP_ITEM_IMAGE")
	public String groupItemImage;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	@Column(name = "STATUS")
	public String status;
	
	@Transient
	public boolean isGroupBuy;

	public static Map queryXjlDwGroupBuyItemListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_group_buy_item a ";
		sql +="where 1=3 [or a.group_buy_id=l:groupBuyId]";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwGroupBuyItem> data = ModelUtils.queryData(pageIndex, pageSize, ret, XjlDwGroupBuyItem.class);
		return ModelUtils.createResultMap(ret, data);
	}
}
