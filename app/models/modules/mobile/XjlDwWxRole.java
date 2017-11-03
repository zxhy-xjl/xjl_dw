package models.modules.mobile;
import java.sql.*;
import java.util.Date;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import play.Logger;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.StringUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_wx_role")
public class XjlDwWxRole extends GenericModel{

	@Id
	@Column(name = "ROLE_WX_ID")
	public Long roleWxId;

	@Column(name = "ROLE_ID")
	public Long roleId;

	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	/***
	 * 根据openId得到该用户的所有角色
	 * @param openid
	 * @return
	 */
	public static List<XjlDwWxRole> getFindByOpenId(String openid) {
        Map condition = new HashMap<String, String>();
        if(StringUtil.isNotEmpty(openid)){
            condition.put("wxOpenId", openid);
        }
        Map returnMap = queryXjlDwWxRoleListByPage(condition,1,100);
        if(returnMap!=null&&returnMap.get("data")!=null){
        	List<XjlDwWxRole> retData = (List<XjlDwWxRole>)returnMap.get("data");
        	if(retData.size()>0){
            	return retData;
        	}else{
        		return null;
        	}

        }else{
        	return null;
        }
	}

	public static Map queryXjlDwWxRoleListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_wx_role a ";
		sql += "where a.status='0AA' ";
		if(StringUtil.isNotEmpty(condition.get("wxOpenId"))){
			String searchKeyWord = condition.get("wxOpenId");
			sql += "and a.wx_open_id='"+searchKeyWord+"' ";
	    }
		SQLResult ret = SQLParser.parseSQL(sql, condition);
		Query query = JPA.em().createNativeQuery(ret.getSql(), XjlDwWxRole.class);
		int i = 1;
		for (ParamObject o : ret.getParams()) {
			query.setParameter(i++, o.getValue());
		}
		List<XjlDwWxRole> data = query.setFirstResult((pageIndex - 1) * pageSize)
			.setMaxResults(pageSize).getResultList();
		Query query2 = JPA.em().createNativeQuery(ret.getCountSql());
		int j = 1;
		for (ParamObject o : ret.getParams()) {
			query2.setParameter(j++, o.getValue());
		}
		List<Object> countRet = query2.getResultList();
		long total = Long.parseLong(countRet.get(0).toString());
		Map hm = new HashMap();
		hm.put("total", total);
		hm.put("data", data);
		return hm;
	}
}
