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
import utils.StringUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_wx_student")
public class XjlDwWxStudent extends GenericModel{

	@Id
	@Column(name = "STUDENT_WX_ID")
	public Long studentWxId;

	@Column(name = "STUDENT_ID")
	public Long studentId;

	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;

	@Column(name = "STATUS")
	public String status;
	/**
	 * 是不是默认选择，一个家长可以关注多个学生，只有一个是默认，也就是会查看这个学生以及所在班级的信息
	 * 设置成默认之后，需要同步更新班级的默认值
	 */
	@Column(name = "IS_DEFAULT")
	public String isDefault;
	
	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Transient
	public String studentNo;
	
	@Transient
	public String studentSex;
	
	@Transient
	public String studentName;
	
	@Transient
	public String className;
	
	@Transient
	public Long classId;
	
	@Transient
	public String classCode;
	
	@Transient
	public XjlDwStudent student;
	@Transient
	public XjlDwClass dwClass;
	/**
	 * 得到微信用户所有关注的学生
	 * @param wxOpenId
	 * @return
	 */
	public static Map queryXjlDwWxStudentListByOpenId(String wxOpenId){
		String sql = "select * ";
		sql += "from xjl_dw_wx_student a where a.status='0AA' and WX_OPEN_ID='" + wxOpenId + "'";
		Map<String, String> condition = new HashMap<String, String>();
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwWxStudent> data = ModelUtils.queryData(0, -1, ret,XjlDwWxStudent.class);
		//添加学生信息和班级信息
		for (XjlDwWxStudent xjlDwWxStudent : data) {
			xjlDwWxStudent.student=XjlDwStudent.queryXjlDwStudentById(xjlDwWxStudent.studentId);
			xjlDwWxStudent.dwClass = XjlDwClass.queryXjlDwClassById(xjlDwWxStudent.student.classId);
		}
		return ModelUtils.createResultMap(ret, data);
	}
	
	public static Map queryXjlDwWxStudentListByPage(Map<String, String> condition,
			int pageIndex, int pageSize) {
			String sql = "select a.student_id,a.is_default ";
			sql+=",s.student_name,s.class_id,s.student_no,s.student_sex ";
			sql+=",c.class_name,c.class_code,a.student_wx_id ";
			sql += "from xjl_dw_wx_student a ";
			sql += "left join xjl_dw_student s on a.student_id=s.student_id ";
			sql += "left join xjl_dw_class c on s.class_id=c.class_id ";
			sql +="where 1=3 [ or a.wx_open_id=:wx_open_id ][ or a.student_id=1:studentId ][ or s.class_id=1:classId ] ";
			sql += "and a.status='0AA' and s.status='0AA' and c.status='0AA'";
			sql += "order by a.student_id desc ";
			SQLResult ret = ModelUtils.createSQLResult(condition, sql);
			List<Object[]> retData = ModelUtils.queryData(pageIndex, pageSize, ret);
			List<XjlDwWxStudent> data =  new ArrayList<XjlDwWxStudent>();
			XjlDwWxStudent xjlDwWxStudent;
			for(Object[]m :retData){
				xjlDwWxStudent = new XjlDwWxStudent();
				xjlDwWxStudent.student=new XjlDwStudent();
				xjlDwWxStudent.dwClass=new XjlDwClass();
				if(m[0]!=null)
					xjlDwWxStudent.studentId = StringUtil.getLong(m[0].toString());
				if(m[1]!=null)
					xjlDwWxStudent.isDefault = m[1].toString();
				if(m[2]!=null)
					xjlDwWxStudent.studentName = m[2].toString();
				if(m[3]!=null)
					xjlDwWxStudent.classId =  StringUtil.getLong(m[3].toString());
				if(m[4]!=null)
					xjlDwWxStudent.studentNo = m[4].toString();
				if(m[5]!=null)
					xjlDwWxStudent.studentSex = m[5].toString();
				if(m[6]!=null)
					xjlDwWxStudent.className = m[6].toString();
				if(m[7]!=null)
					xjlDwWxStudent.classCode = m[7].toString();
				if(m[8]!=null)
					xjlDwWxStudent.studentWxId =  StringUtil.getLong(m[8].toString());
				if (XjlDwParentCommittee.isParentCommittee(xjlDwWxStudent.classId, condition.get("wxOpenId"))){
					xjlDwWxStudent.className += "（家委会)";
				}
				data.add(xjlDwWxStudent);
			}
			return ModelUtils.createResultMap(ret, data);
		}
	/**
	 * 根据openId得到缺省的关注学生
	 * @param wxOpenId
	 * @return
	 */
	public static XjlDwWxStudent queryDefaultByOpenId(String wxOpenId){
		String sql = "select * ";
		sql += "from xjl_dw_wx_student a where a.status='0AA' and is_default='Y' and WX_OPEN_ID='" + wxOpenId + "'";
		Map<String, String> condition = new HashMap<String, String>();
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwWxStudent> data = ModelUtils.queryData(0, -1, ret,XjlDwWxStudent.class);
		//添加学生信息和班级信息
		if (data.isEmpty()){
			return null;
		} else {
			XjlDwWxStudent xjlDwWxStudent = data.get(0);
			xjlDwWxStudent.student=XjlDwStudent.queryXjlDwStudentById(xjlDwWxStudent.studentId);
			xjlDwWxStudent.dwClass = XjlDwClass.queryXjlDwClassById(xjlDwWxStudent.student.classId);
			return xjlDwWxStudent;
		}
		
	}
	public static List<XjlDwWxStudent> queryByStudentId(Long studentId){
		String sql = "select * ";
		sql += "from xjl_dw_wx_student a where a.status='0AA' [ and STUDENT_ID=l:studentId]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("studentId", String.valueOf(studentId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwWxStudent> data = ModelUtils.queryData(ret,XjlDwWxStudent.class);
		return data;
	}
}
