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
import utils.StringUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_class")
public class XjlDwClass extends GenericModel{

	@Id
	@Column(name = "CLASS_ID")
	public Long classId;

	@Column(name = "CLASS_NAME")
	public String className;

	@Column(name = "CLASS_CODE")
	public String classCode;
	
	@Column(name = "SCHOOL_ID")
	public Long schoolId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	public static Map queryXjlDwClassListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_class a ";
		sql += "where 1=3 [or a.school_id=l:schoolId ][or a.class_id=l:classId ]  ";
		SQLResult ret = SQLParser.parseSQL(sql, condition);
		Query query = JPA.em().createNativeQuery(ret.getSql(), XjlDwClass.class);
		int i = 1;
		for (ParamObject o : ret.getParams()) {
			query.setParameter(i++, o.getValue());
		}
		List<XjlDwClass> data = query.setFirstResult((pageIndex - 1) * pageSize)
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
	/**
	 * 根据班级id得到班级信息
	 * @param classId
	 * @return
	 */
	public static XjlDwClass queryXjlDwClassById(Long classId) {
			String sql = "select * ";
			sql += "from xjl_dw_class a where a.CLASS_ID=[l:classId]";
			Map<String, String> condition = new HashMap<String, String>();
			condition.put("classId", String.valueOf(classId));
			SQLResult ret = ModelUtils.createSQLResult(condition, sql);
			List<XjlDwClass> data = ModelUtils.queryData(0, -1, ret, XjlDwClass.class);
			if (data.isEmpty()){
				throw new RuntimeException("没有找到班级:" + classId);
			} else {
				return data.get(0);
			}
		}
}
