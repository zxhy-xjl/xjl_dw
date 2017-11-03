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

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_album_image")
public class XjlDwAlbumImage extends GenericModel{

	@Id
	@Column(name = "ALBUM_IMAGE_ID")
	public Long albumImageId;

	@Column(name = "FILE_ID")
	public Long fileId;

	@Column(name = "ALBUM_ID")
	public Long albumId;

	@Column(name = "IMAGE_TITLE")
	public String imageTitle;

	@Column(name = "IMAGE_ORDER")
	public Long imageOrder;

	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	//文件对一个的url地址
	@Transient
	public String fileUrl;
	
	public static Map queryXjlDwAlbumImageListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from xjl_dw_album_image a order by IMAGE_ORDER";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		
		List<XjlDwAlbumImage> data = ModelUtils.queryData(pageIndex, pageSize, ret,XjlDwAlbumImage.class);
		for (XjlDwAlbumImage xjlDwAlbumImage : data) {
			XjlDwFile file = XjlDwFile.queryXjlDwFileById(xjlDwAlbumImage.fileId);
			if (file != null){
				xjlDwAlbumImage.fileUrl = file.fileUrl;
			}
		}
		return ModelUtils.createResultMap(ret, data);
	}
}
