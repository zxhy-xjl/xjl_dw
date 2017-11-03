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
import utils.DateUtil;
import utils.StringUtil;
import utils.SysParamUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_notice")
public class XjlDwNotice extends GenericModel{

	@Id
	@Column(name = "NOTICE_ID")
	public Long noticeId;

	@Column(name = "NOTICE_TITLE")
	public String noticeTitle;

	@Column(name = "NOTICE_CONTENT")
	public String noticeContent;

	@Column(name = "NOTICE_DATE")
	public Date noticeDate;

	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	public static Map queryNoticeListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_notice a order by a.notice_id desc";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwNotice> data = ModelUtils.queryData(pageIndex, pageSize, ret,XjlDwNotice.class);
		return ModelUtils.createResultMap(ret, data);
	}
}
