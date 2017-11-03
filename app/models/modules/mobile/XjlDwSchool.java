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
import utils.DateUtil;
import utils.SecurityUtil;
import utils.StringUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_school")
public class XjlDwSchool extends GenericModel{

	@Id
	@Column(name = "SCHOOL_ID")
	public Long schoolId;

	@Column(name = "SCHOOL_NAME")
	public String schoolName;

	@Column(name = "SCHOOL_CODE")
	public String schoolCode;

	@Column(name = "SCHOOL_LOGO")
	public String schoolLogo;

	@Column(name = "SCHOOL_TEL")
	public String schoolTel;

	@Column(name = "SCHOOL_AREA")
	public String schoolArea;

	@Column(name = "SCHOOL_ADDRESS")
	public String schoolAddress;

	@Column(name = "EFF_DATE")
	public Date effDate;

	@Column(name = "EXP_DATE")
	public Date expDate;

	@Column(name = "SCHOOL_DOMAIN")
	public String schoolDomain;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Transient
	public WxServer wxServer;

	public static Map queryXjlDwSchoolListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		System.out.println("condition:"+condition);
		String sql = "select a.school_id,a.school_name,a.school_code,a.school_logo,a.school_tel,a.school_area,a.school_address,a.school_domain ";
		sql+=",b.wx_server_id,b.app_id,b.app_secret ";
		sql += "from xjl_dw_school a ";
		sql += "left join wx_server b on a.school_id=b.school_id ";
		sql +="where 1=3 [ or a.school_id=l:schoolId ][ or a.school_domain=:schoolDomain ] ";
		sql += "and a.status='0AA' and b.status='0AA' ";
		System.out.println("searchKeyWord:"+StringUtil.isNotEmpty(condition.get("searchKeyWord")));
		if(StringUtil.isNotEmpty(condition.get("searchKeyWord"))){
			String searchKeyWord = condition.get("searchKeyWord");
			System.out.println("searchKeyWord:"+searchKeyWord);
			sql += "and (a.school_name like '%"+searchKeyWord+"%' or a.school_tel like '%"+searchKeyWord+"%' ) ";
		}
		sql += "order by a.school_id desc ";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object[]> retData = ModelUtils.queryData(pageIndex, pageSize, ret);
		List<XjlDwSchool> data =  new ArrayList<XjlDwSchool>();
		XjlDwSchool xjlDwSchool;
		for(Object[]m :retData){
			xjlDwSchool = new XjlDwSchool();
			xjlDwSchool.wxServer = new WxServer();
			if(m[0]!=null){
				xjlDwSchool.schoolId = StringUtil.getLong(m[0].toString());
			}
			if(m[1]!=null)
				xjlDwSchool.schoolName = m[1].toString();
			if(m[2]!=null)
				xjlDwSchool.schoolCode = m[2].toString();
			if(m[3]!=null)
				xjlDwSchool.schoolLogo = m[3].toString();
			if(m[4]!=null)
				xjlDwSchool.schoolTel = m[4].toString();
			if(m[5]!=null)
				xjlDwSchool.schoolArea = m[5].toString();
			if(m[6]!=null)
				xjlDwSchool.schoolAddress = m[6].toString();
			if(m[7]!=null)
				xjlDwSchool.schoolDomain = m[7].toString();
			if(m[8]!=null)
				xjlDwSchool.wxServer.wxServerId = StringUtil.getLong(m[8].toString());
			if(StringUtil.isNotEmpty(m[9]))
				//xjlDwSchool.wxServer.appId = SecurityUtil.decrypt(m[9].toString());
				xjlDwSchool.wxServer.appId =m[9].toString();
			if(StringUtil.isNotEmpty(m[10]))
				//xjlDwSchool.wxServer.appSecret = SecurityUtil.decrypt(m[10].toString());
				xjlDwSchool.wxServer.appSecret =m[10].toString();
			data.add(xjlDwSchool);
		}
		return ModelUtils.createResultMap(ret, data);
	}

	
	/***
	 * 根据主键查询单个学校的信息
	 * @param schoolId 学校标识
	 * @param schoolDoMain学校域名
	 * @return 返回学校信息
	 */
	public static XjlDwSchool getSchoolBySchoolId(Long schoolId,String schoolDoMain) {
		int pageIndex = 1;
        int pageSize = 100;
        Map condition = new HashMap<String, String>();
        if(StringUtil.isNotEmpty(schoolId)){
            condition.put("schoolId",schoolId);
        }
        System.out.println(schoolDoMain);
        if(StringUtil.isNotEmpty(schoolDoMain)){
        	condition.put("schoolDomain", schoolDoMain);
        }
        Map returnMap = queryXjlDwSchoolListByPage(condition,pageIndex,pageSize);
        System.out.println(returnMap);
        if(returnMap!=null&&returnMap.get("data")!=null){
        	if("1".equals(returnMap.get("total").toString())){
            	List<XjlDwSchool> retData = (List<XjlDwSchool>)returnMap.get("data");
            	if(retData.size()>0){
                	return retData.get(0);
            	}else{
            		return null;
            	}
        	}else{
        		Logger.error("==========ERROR==========查出的学校信息不唯一，共有"+returnMap.get("total")+"条",XjlDwSchool.class);
        		return null;
        	}
        }else{
        	return null;
        }
	}
}
