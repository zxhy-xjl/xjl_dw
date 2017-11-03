package models.modules.mobile;
import java.sql.*;
import java.util.Date;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_role")
public class XjlDwRole extends GenericModel{

	@Id
	@Column(name = "ROLE_ID")
	public Long roleId;

	@Column(name = "ROLE_NAME")
	public String roleName;

	@Column(name = "ROLE_CODE")
	public String roleCode;
	
	@Column(name = "SCHOOL_ID")
	public Long schoolId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	public static Map queryXjlDwRoleListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_role a ";
		SQLResult ret = SQLParser.parseSQL(sql, condition);
		Query query = JPA.em().createNativeQuery(ret.getSql(), XjlDwRole.class);
		int i = 1;
		for (ParamObject o : ret.getParams()) {
			query.setParameter(i++, o.getValue());
		}
		List<XjlDwRole> data = query.setFirstResult((pageIndex - 1) * pageSize)
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
