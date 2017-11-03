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
 * 考试科目
 * @author lilisheng
 *
 */
@Entity
@Table(name = "xjl_dw_exam_subject")
public class XjlDwExamSubject extends GenericModel{

	@Id
	@Column(name = "EXAM_SUBJECT_ID")
	public Long examSubjectId;
	//考试id
	@Column(name = "EXAM_ID")
	public Long examId;
	//科目id
	@Column(name = "SUBJECT_ID")
	public Long subjectId;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	@Column(name = "STATUS")
	public String status;

	public static Map queryByExam(Long examId) {
		String sql = "select * ";
		sql += "from xjl_dw_exam_subject a where status='0AA' [ and EXAM_ID=l:examId] ";
		Map<String, String> condition = new HashMap<String,String>();
		condition.put("examId", String.valueOf(examId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwExamSubject> data = ModelUtils.queryData(ret, XjlDwExamSubject.class);
		return ModelUtils.createResultMap(data);
	}
	/**
	 * 删除考试科目,设置状态为0XX
	 * @param examId
	 * @return
	 */
	public static int deleteByExam(Long examId){
		String sql = "update xjl_dw_exam_subject set status='0XX' where [ EXAM_ID = l:examId ]";
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("examId", String.valueOf(examId));
		return ModelUtils.executeDelete(condition, sql);
	}
}
