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
@Table(name = "xjl_dw_album_template")
public class XjlDwAlbumTemplate extends GenericModel{

	@Id
	@Column(name = "ALBUM_TEMPLATE_ID")
	public Long albumTemplateId;

	@Column(name = "ALBUM_TEMPLATE_TITLE")
	public String albumTemplateTitle;

	@Column(name = "ALBUM_TEMPLATE_IMG")
	public String albumTemplateImg;

	@Column(name = "ALBUM_TEMPLATE_STYLE")
	public String albumTemplateStyle;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	//相册封面需要显示图片的数量
	@Column(name = "ALBUM_TEMPLATE_IMG_NUM")
	public Long albumTemplateImgNum;
	
	public static Map queryXjlDwAlbumTemplateListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_album_template a where a.status='0AA'";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwAlbumTemplate> data = ModelUtils.queryData(pageIndex, pageSize, ret,XjlDwAlbumTemplate.class);
		return ModelUtils.createResultMap(ret, data);
	}
	public static XjlDwAlbumTemplate queryXjlDwAlbumTemplateListById(Long albumTemplateId) {
			String sql = "select * ";
			sql += "from xjl_dw_album_template a where a.ALBUM_TEMPLATE_ID=[l:albumTemplateId]";
			Map<String, String> condition = new HashMap<>();
			condition.put("albumTemplateId", String.valueOf(albumTemplateId));
			SQLResult ret = ModelUtils.createSQLResult(condition, sql);
			List<XjlDwAlbumTemplate> data = ModelUtils.queryData(1, -1, ret,XjlDwAlbumTemplate.class);
			if (data.isEmpty()){
				throw new RuntimeException("没有找到对应的模板:" + albumTemplateId);
			} else {
				return data.get(0);
			}
		}
}
