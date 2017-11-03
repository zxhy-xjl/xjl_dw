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
@Table(name = "xjl_dw_article")
public class XjlDwArticle extends GenericModel{

	@Id
	@Column(name = "ARTICLE_ID")
	public Long articleId;

	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;

	@Column(name = "ARTICLE_TITLE")
	public String articleTitle;

	@Column(name = "ARTICLE_CONTENT")
	public String articleContent;

	@Column(name = "ARTICLE_AUTHOR")
	public String articleAuthor;

	@Column(name = "ARTICLE_PUBLISH_DATE")
	public Date articlePublishDate;

	@Column(name = "ARTICLE_STATE")
	public String articleState;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Column(name = "CLASS_ID")
	public Long classId;
	
	public static Map queryXjlDwArticleListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_article a order by a.ARTICLE_PUBLISH_DATE desc";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List data = ModelUtils.queryData(pageIndex, pageSize, ret,XjlDwArticle.class);
		return ModelUtils.createResultMap(ret, data);
	}
}
