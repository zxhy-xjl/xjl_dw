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
 * 作业
 * @author lilisheng
 *
 */
@Entity
@Table(name = "xjl_dw_homework")
public class XjlDwHomework extends GenericModel{

	@Id
	@Column(name = "HOMEWORK_ID")
	public Long homeworkId;
	//班级id
	@Column(name = "CLASS_ID")
	public Long classId;
	//科目id
	@Column(name="subject_id")
	public Long subjectId;
	//作业标题
	@Column(name = "HOMEWORK_TITLE")
	public String homeworkTitle;
	//作业内容
	@Column(name = "HOMEWORK_CONTENT")
	public String homeworkContent;
	//发布人
	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	public static Map queryXjlDwHomeworkListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_homework a where status='0AA' [ and CLASS_ID=l:classId] order by create_time desc";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwHomework> data = ModelUtils.queryData(pageIndex, pageSize, ret, XjlDwHomework.class);
		return ModelUtils.createResultMap(ret,data);
	}
}
