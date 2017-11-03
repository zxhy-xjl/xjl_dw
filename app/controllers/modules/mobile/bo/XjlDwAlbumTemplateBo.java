package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwAlbumTemplate;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:06:48
 * @describe  类说明
*/
public class XjlDwAlbumTemplateBo {
	// 增加、编辑
	public static XjlDwAlbumTemplate save(XjlDwAlbumTemplate xjlDwAlbumTemplate) {
		if (xjlDwAlbumTemplate.albumTemplateId != null) {
		}
		if (xjlDwAlbumTemplate.albumTemplateId == null) {
			xjlDwAlbumTemplate.albumTemplateId = SeqUtil.maxValue("xjl_dw_album_template", "album_template_id");
			xjlDwAlbumTemplate.status = "0AA";
			xjlDwAlbumTemplate.createTime = DateUtil.getNowDate();
		}
		xjlDwAlbumTemplate = xjlDwAlbumTemplate.save();
		return xjlDwAlbumTemplate;
	}
	// 删除
	public static XjlDwAlbumTemplate del(XjlDwAlbumTemplate xjlDwAlbumTemplate) {
		if (xjlDwAlbumTemplate != null) {
			xjlDwAlbumTemplate.status = "0XX";
			xjlDwAlbumTemplate = xjlDwAlbumTemplate.save();
			return xjlDwAlbumTemplate;
		}
		return null;
	}
}