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

import org.apache.commons.lang.math.NumberUtils;

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_album")
public class XjlDwAlbum extends GenericModel{

	@Id
	@Column(name = "ALBUM_ID")
	public Long albumId;

	@Column(name = "ALBUM_TEMPLATE_ID")
	public Long albumTemplateId;

	@Column(name = "ALBUM_TITLE")
	public String albumTitle;
	
	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Column(name = "CLASS_ID")
	public Long classId;
	//相册对应的封面
	@Transient
	public XjlDwAlbumTemplate template;
	//相册封面需要的图片
	@Transient
	public List<XjlDwAlbumImage> templateImageList;
	/**
	 * 查询相册，自动带上魔板信息和模板需要的封面照片信息
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static Map queryXjlDwAlbumListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select a.* ";
		sql += "from xjl_dw_album a order by create_time desc";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlDwAlbum> data = ModelUtils.queryData(pageIndex, pageSize, ret,XjlDwAlbum.class);
		Map<String, XjlDwAlbumTemplate> templateMap = new HashMap<String, XjlDwAlbumTemplate>();
		for (XjlDwAlbum xjlDwAlbum : data) {
			if (xjlDwAlbum.albumTemplateId != null){
				//看看模板是不是已经获取过，如果获取过，则不再从数据库中获取
				if (!templateMap.containsKey(String.valueOf(xjlDwAlbum.albumTemplateId))){
					XjlDwAlbumTemplate template = XjlDwAlbumTemplate.queryXjlDwAlbumTemplateListById(xjlDwAlbum.albumTemplateId);
					//把这个模板添加到已经存在的对象中
					templateMap.put(String.valueOf(xjlDwAlbum.albumTemplateId), template);
				}
				XjlDwAlbumTemplate template = templateMap.get(String.valueOf(xjlDwAlbum.albumTemplateId));
				if (template != null){
					//添加相册模板
					xjlDwAlbum.template = template;
					//添加相册封面上的图片列表
					xjlDwAlbum.templateImageList = (List<XjlDwAlbumImage>)XjlDwAlbumImage.queryXjlDwAlbumImageListByPage(condition, 1, template.albumTemplateImgNum.intValue()).get("data");
				}
			}
		}
		return ModelUtils.createResultMap(ret, data);
	}
}
