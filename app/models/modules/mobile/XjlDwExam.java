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
/**
 * 考试
 * @author lilisheng
 *
 */
@Entity
@Table(name = "xjl_dw_exam")
public class XjlDwExam extends GenericModel{

	@Id
	@Column(name = "EXAM_ID")
	public Long examId;
	//考试标题，比如9月份月考
	@Column(name = "EXAM_TITLE")
	public String examTitle;
	//考试时间
	@Column(name = "EXAM_DATE")
	public Date examDate;
	//班级id
	@Column(name="CLASS_ID")
	public Long classId;
	//发布人id
	@Column(name="wx_open_id")
	public String wxOpenId;
	
	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	public static Map query(Map<String, String> condition,int pageIndex, int pageSize){
		String sql = "select * ";
		sql += "from xjl_dw_exam a where a.status='0AA' [ and class_id=l:classId]";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwExam> data = ModelUtils.queryData(pageIndex, pageSize, ret, XjlDwExam.class);
		return ModelUtils.createResultMap(ret, data);
	}
	/**
	 * 根据班级号查询
	 * @param classId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static Map queryByClass(Long classId,
		int pageIndex, int pageSize) {
		Map<String, String> condition = new HashMap();
		condition.put("classId", String.valueOf(classId));
		return query(condition,pageIndex,pageSize);
	}
}
