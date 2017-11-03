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

import play.Logger;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.StringUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_wx_class")
public class XjlDwWxClass extends GenericModel{

	@Id
	@Column(name = "CLASS_WX_ID")
	public Long classWxId;

	@Column(name = "CLASS_ID")
	public Long classId;

	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	/**
	 * 是不是默认班级
	 */
	@Column(name = "IS_DEFAULT")
	public String isDefault;
	//班级对象
	@Transient
	public XjlDwClass dwClass;

	/***
	 * 根据openId查找这个人关注的默认班级
	 * @param openid
	 * @return
	 */
	public static XjlDwWxClass getDefaultByOpenId(String openid) {
		int pageIndex = 1;
        int pageSize = 100;
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("isDefault", "Y");
        condition.put("wxOpenId", openid);
        Map returnMap = queryXjlDwWxClassListByPage(condition,pageIndex,pageSize);
        List<XjlDwWxClass> retData = (List<XjlDwWxClass>)returnMap.get("data");
        if (retData.isEmpty()){
        	return null;
        } else {
        	return retData.get(0);
        }
	}
	/**
	 * 根据openId得到这个人关注的所有班级
	 * @param openid
	 * @return
	 */
	public static Map getByOpenId(String openid) {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("wxOpenId", openid);
        return queryXjlDwWxClassListByPage(condition,-1,-1);
	}
	/**
	 * 根据openid和classid查找
	 * @param openid
	 * @param classId
	 * @return
	 */
	public static XjlDwWxClass getByOpenIdAndClassId(String openid,Long classId) {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("wxOpenId", openid);
        condition.put("classId", String.valueOf(classId));
        Map returnMap = queryXjlDwWxClassListByPage(condition,-1,-1);
        List<XjlDwWxClass> retData = (List<XjlDwWxClass>)returnMap.get("data");
        if (retData.isEmpty()){
        	return null;
        } else {
        	return retData.get(0);
        }
	}
	/**
	 * 查询微信用户关注班级的数据
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static Map queryXjlDwWxClassListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_wx_class a ";
		sql += "where status='0AA' [ and a.is_default=:isDefault ][ and a.CLASS_ID=l:classId ]";
		if(StringUtil.isNotEmpty(condition.get("wxOpenId"))){
			String searchKeyWord = condition.get("wxOpenId");
			sql += "and a.wx_open_id='"+searchKeyWord+"' ";
	    }
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwWxClass> data = ModelUtils.queryData(pageIndex, pageSize, ret, XjlDwWxClass.class);
		for (XjlDwWxClass xjlDwWxClass : data) {
			xjlDwWxClass.dwClass = XjlDwClass.queryXjlDwClassById(xjlDwWxClass.classId);
		}
		return ModelUtils.createResultMap(ret, data);
	}
}
