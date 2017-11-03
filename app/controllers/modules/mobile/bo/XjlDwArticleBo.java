package controllers.modules.mobile.bo;

import models.modules.mobile.XjlDwArticle;
import utils.DateUtil;
import utils.SeqUtil;
/**
 * @author    姓名   E-mail: 邮箱  Tel: 电话
 * @version   创建时间：2017-09-14 上午11:07:05
 * @describe  类说明
*/
public class XjlDwArticleBo {
	// 增加、编辑
	public static XjlDwArticle save(XjlDwArticle xjlDwArticle) {
		if (xjlDwArticle.articleId != null) {
		}
		if (xjlDwArticle.articleId == null) {
			xjlDwArticle.articleId = SeqUtil.maxValue("xjl_dw_article", "article_id");
			xjlDwArticle.status = "0AA";
			xjlDwArticle.createTime = DateUtil.getNowDate();
			xjlDwArticle.articlePublishDate = DateUtil.getNowDate();
		}
		xjlDwArticle = xjlDwArticle.save();
		return xjlDwArticle;
	}
	// 删除
	public static XjlDwArticle del(XjlDwArticle xjlDwArticle) {
		if (xjlDwArticle != null) {
			xjlDwArticle.status = "0XX";
			xjlDwArticle = xjlDwArticle.save();
			return xjlDwArticle;
		}
		return null;
	}
}