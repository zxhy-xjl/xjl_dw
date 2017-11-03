package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwAlbum;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:05:33
 * @describe  类说明
*/
public class XjlDwAlbumBo {
	// 增加、编辑
	public static XjlDwAlbum save(XjlDwAlbum xjlDwAlbum) {
		if (xjlDwAlbum.albumId != null) {
		}
		if (xjlDwAlbum.albumId == null) {
			xjlDwAlbum.albumId = SeqUtil.maxValue("xjl_dw_album", "album_id");
			xjlDwAlbum.status = "0AA";
			xjlDwAlbum.createTime = DateUtil.getNowDate();
		}
		xjlDwAlbum = xjlDwAlbum.save();
		return xjlDwAlbum;
	}
	// 删除
	public static XjlDwAlbum del(XjlDwAlbum xjlDwAlbum) {
		if (xjlDwAlbum != null) {
			xjlDwAlbum.status = "0XX";
			xjlDwAlbum = xjlDwAlbum.save();
			return xjlDwAlbum;
		}
		return null;
	}
}