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
 * 作业标榜
 * @author lilisheng
 *
 */
@Entity
@Table(name = "xjl_dw_homework_model")
public class XjlDwHomeworkModel extends GenericModel{

	@Id
	@Column(name = "MODEL_ID")
	public Long modelId;
	//标榜标题
	@Column(name = "MODEL_TITLE")
	public String modelTitle;
	//标榜内容
	@Column(name = "MODEL_CONTENT")
	public String modelContent;
	//针对那个作业
	@Column(name = "HOMEWORK_ID")
	public Long homeworkId;
	//学生id
	@Column(name = "STUDENT_ID")
	public Long studentId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	public static Map queryXjlDwHomeworkModelListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_homework_model a where status='0AA' [ and homework_id=l:homeworkId]";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwHomeworkModel> data = ModelUtils.queryData(ret, XjlDwHomeworkModel.class);
		return ModelUtils.createResultMap(ret, data);
	}
	/**
	 * 查询标榜数量
	 * @param homeworkId
	 * @return
	 */
	public static long queryCountByHomework(Long homeworkId){
		String sql = "select count(*) ";
		sql += "from xjl_dw_homework_model a where status='0AA' [ and homework_id=l:homeworkId]";
		Map condition = new HashMap();
		condition.put("homeworkId", homeworkId);
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object> data = ModelUtils.queryData(ret);
		return Long.parseLong(data.get(0).toString());
	}
}
