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

import org.apache.commons.lang.StringUtils;

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;
/**
 * 班级家委会
 * @author lilisheng
 *
 */
@Entity
@Table(name = "xjl_dw_parent_committee")
public class XjlDwParentCommittee extends GenericModel{

	@Id
	@Column(name = "parent_committee_id")
	public Long parentCommitteeId;
	//班级id
	@Column(name = "class_id")
	public Long classId;
	//微信openId
	@Column(name = "wx_open_id")
	public String wxOpenId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	

	public static Map query(Map<String, String> condition) {
		String sql = "select * ";
		sql += "from xjl_dw_parent_committee a where STATUS='0AA' [ and class_id=l:classId] [ and wx_open_id=:wxOpenId]";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwParentCommittee> data = ModelUtils.queryData(ret, XjlDwParentCommittee.class);
		return ModelUtils.createResultMap(data);
	}
	/**
	 * 查找某个班级下面的所有家委会成员
	 * @param classId
	 * @return
	 */
	public static Map queryByClass(Long classId){
		Map<String, String> condition = new HashMap<String,String>();
		condition.put("classId", String.valueOf(classId));
		return query(condition);
	}
	/**
	 * 查询某个家长所担任的家委会班级
	 * @param wxOpenId
	 * @return
	 */
	public static Map queryByWxOpenId(String wxOpenId){
		Map<String, String> condition = new HashMap<String,String>();
		condition.put("wxOpenId", wxOpenId);
		return query(condition);
	}
	/**
	 * 查找某个家长是不是在某个班级做家委会
	 * @param classId
	 * @param wxOpenId
	 * @return
	 */
	public static boolean isParentCommittee(Long classId,String wxOpenId){
		Map<String, String> condition = new HashMap<String,String>();
		condition.put("classId", String.valueOf(classId));
		condition.put("wxOpenId", wxOpenId);
		Map map = query(condition);
		return ((Long)map.get("total"))==1;
	}
}
