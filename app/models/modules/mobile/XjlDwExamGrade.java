package models.modules.mobile;
import java.sql.*;
import java.util.Date;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.lang.math.NumberUtils;

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;
/**
 * 成绩
 * @author lilisheng
 *
 */
@Entity
@Table(name = "xjl_dw_exam_grade")
public class XjlDwExamGrade extends GenericModel{

	@Id
	@Column(name = "EXAM_GRADE_ID")
	public Long examGradeId;
	//考试id
	@Column(name = "EXAM_ID")
	public Long examId;
	//科目id
	@Column(name = "SUBJECT_ID")
	public Long subjectId;
	//学生id
	@Column(name = "STUDENT_ID")
	public Long studentId;
	//成绩
	@Column(name = "EXAM_GRADE")
	public Double examGrade;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	/**
	 * 根据考试id和学生查询该学生在某次考试中的所有科目成绩
	 * @param examId
	 * @param studentId
	 * @return
	 */
	public static Map queryByStudentAndExam(Long examId, Long studentId) {
		String sql = "select a.* ";
		sql += "from xjl_dw_exam_grade a  where status='0AA' [ and EXAM_ID=l:examId ] [ and STUDENT_ID=l:studentId]";
		Map condition = new HashMap();
		condition.put("examId", String.valueOf(examId));
		condition.put("studentId", String.valueOf(studentId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwExamGrade> data = ModelUtils.queryData(ret,XjlDwExamGrade.class);
		return ModelUtils.createResultMap(data);
	}
	/**
	 * 得到考试总分
	 * @param examId
	 * @param studentId
	 * @return
	 */
	public static Double queryAmountByStudentAndExam(Long examId, Long studentId) {
		String sql = "select sum(EXAM_GRADE) ";
		sql += "from xjl_dw_exam_grade a where status='0AA' [ and EXAM_ID=l:examId ] [ and STUDENT_ID=l:studentId]";
		Map condition = new HashMap();
		condition.put("examId", String.valueOf(examId));
		condition.put("studentId", String.valueOf(studentId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object> data = ModelUtils.queryData(ret);
		return Double.parseDouble(data.get(0).toString());
	}
	/**
	 * 得到一个考试的最大/最小/评价分
	 * @param examId
	 * @return
	 */
	public static Map queryMaxMinAvg(Long examId){
		String sql = "select max(exam_grade),min(exam_grade),round(avg(exam_grade),2) from ("+
"select student_id,sum(exam_grade) as exam_grade from xjl_dw_exam_grade where [exam_id=l:examId] group by student_id "+
")  as foo";
		Map condition = new HashMap();
		condition.put("examId", String.valueOf(examId));
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object[]> data = ModelUtils.queryData(ret);
		Map mma = new HashMap();
		if (data.isEmpty()||data.get(0)==null){
			mma.put("max", "0");
			mma.put("min", "0");
			mma.put("avg", "0");
		} else {
			if (data.get(0)[0] == null){
				mma.put("max", "0");
				mma.put("min", "0");
				mma.put("avg", "0");
			} else {
				mma.put("max", data.get(0)[0].toString());
				mma.put("min",  data.get(0)[1].toString());
				mma.put("avg",  data.get(0)[2].toString());
			}
		}
		return mma;
	}
	
}
