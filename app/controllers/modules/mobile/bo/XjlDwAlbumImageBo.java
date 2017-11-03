package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwAlbumImage;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:06:22
 * @describe  类说明
*/
public class XjlDwAlbumImageBo {
	// 增加、编辑
	public static XjlDwAlbumImage save(XjlDwAlbumImage xjlDwAlbumImage) {
		if (xjlDwAlbumImage.albumImageId != null) {
		}
		if (xjlDwAlbumImage.albumImageId == null) {
			xjlDwAlbumImage.albumImageId = SeqUtil.maxValue("xjl_dw_album_image", "album_image_id");
			xjlDwAlbumImage.status = "0AA";
			xjlDwAlbumImage.createTime = DateUtil.getNowDate();
		}
		xjlDwAlbumImage = xjlDwAlbumImage.save();
		return xjlDwAlbumImage;
	}
	// 删除
	public static XjlDwAlbumImage del(XjlDwAlbumImage xjlDwAlbumImage) {
		if (xjlDwAlbumImage != null) {
			xjlDwAlbumImage.status = "0XX";
			xjlDwAlbumImage = xjlDwAlbumImage.save();
			return xjlDwAlbumImage;
		}
		return null;
	}
}