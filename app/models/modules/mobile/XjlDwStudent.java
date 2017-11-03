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
@Table(name = "xjl_dw_student")
public class XjlDwStudent extends GenericModel{

	@Id
	@Column(name = "STUDENT_ID")
	public Long studentId;

	@Column(name = "CLASS_ID")
	public Long classId;

	@Column(name = "STUDENT_NAME")
	public String studentName;

	@Column(name = "STUDENT_NO")
	public String studentNo;

	@Column(name = "STUDENT_SEX")
	public String studentSex;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	@Column(name = "UPDATE_TIME")
	public Date updateTime;

	@Column(name = "VERSION")
	public Long version;
	
	@Transient
	public String  className;

	public static Map queryXjlDwStudentListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		
		String sql = "select * ";
		sql += "from xjl_dw_student a ";
		sql += "where status='0AA' [ and a.class_id=l:classId] ";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwStudent> data = ModelUtils.queryData(pageIndex,pageSize, ret,XjlDwStudent.class);
		return ModelUtils.createResultMap(ret, data);
	}
	/**
	 * 根据班级id获取所有的学生数据
	 * @param classId
	 * @return
	 */
	public static Map queryByClassId(Long classId){
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("classId", String.valueOf(classId));
		return queryXjlDwStudentListByPage(condition, 1, 0);
	}
	/**
	 * 根据学生id查找学生对象
	 * @param studentId
	 * @return
	 */
	public static XjlDwStudent queryXjlDwStudentById(Long studentId) {
		String sql = "select * ";
		sql += "from xjl_dw_student a  where a.STUDENT_ID=[l:studentId] ";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("studentId", String.valueOf(studentId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwStudent> data = ModelUtils.queryData(1, -1, ret,XjlDwStudent.class);
		if (data.isEmpty()){
			throw new RuntimeException("没有找到学生:" + studentId);
		}
		return data.get(0);
	}
}
