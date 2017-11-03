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
@Table(name = "xjl_dw_teacher")
public class XjlDwTeacher extends GenericModel{

	@Id
	@Column(name = "TEACHER_ID")
	public Long teacherId;

	@Column(name = "TEACHER_NAME")
	public String teacherName;

	@Column(name = "TEACHER_CODE")
	public String teacherCode;

	@Column(name = "TEACHER_SEX")
	public String teacherSex;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	public static Map queryXjlDwTeacherListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_teacher a ";
		SQLResult ret = SQLParser.parseSQL(sql, condition);
		Query query = JPA.em().createNativeQuery(ret.getSql(), XjlDwTeacher.class);
		int i = 1;
		for (ParamObject o : ret.getParams()) {
			query.setParameter(i++, o.getValue());
		}
		List<XjlDwTeacher> data = query.setFirstResult((pageIndex - 1) * pageSize)
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
