package controllers.modules.mobile;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwArticle;
import models.modules.mobile.XjlDwGroupBuy;
import models.modules.mobile.XjlDwNotice;
import models.modules.mobile.XjlDwWxClass;
import play.Logger;
import play.cache.Cache;
import play.i18n.Messages;
import utils.SysParamUtil;
import controllers.comm.BaseController;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.bo.XjlDwFileBo;
import controllers.modules.mobile.bo.XjlDwWxClassBo;
import controllers.modules.mobile.filter.MobileFilter;
import controllers.modules.mobile.bo.WxUserBo;

public class A extends MobileFilter {
	 public static void manager() {
		   render("modules/xjldw/mobile/main.html");
	 } 
    public static void noticeDetail() {
      
    	XjlDwNotice notice = XjlDwNotice.findById(params.get("noticeId", Long.class));
        renderArgs.put("notice", notice);
        render("modules/xjldw/mobile/activity/notice_detail.html");
    }
    public static void noticeAdd() {
        render("modules/xjldw/mobile/activity/notice_add.html");
    }
    public static void noticeList() {
    	WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
        render("modules/xjldw/mobile/activity/notice_list.html");
    }
    public static void articleList() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
		if(params.get("type")!= null) {
			renderArgs.put("type", params.get("type"));
		}else{
			renderArgs.put("type", "list");
		}
        render("modules/xjldw/mobile/activity/article_list.html");
    }
    public static void articleDetail() {
    	XjlDwArticle article = XjlDwArticle.findById(params.get("articleId", Long.class));
        renderArgs.put("detail", article);
        renderArgs.put("type",params.get("type"));
        render("modules/xjldw/mobile/activity/article_detail.html");
    }
    public static void articleAdd() {
		XjlDwArticle article=null;
		if(params.get("articleId")!= null) {
			article = XjlDwArticle.findById(params.get("articleId", Long.class));
		}else{
			WxUser wxUser = getWXUser();
			article=new XjlDwArticle();
			article.articleAuthor=wxUser.nickName;
			article.articleState="0";
		}
		renderArgs.put("article", article);
        render("modules/xjldw/mobile/activity/article_add.html");
    }
    
    public static void groupList() {
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser",wxUser);
        render("modules/xjldw/mobile/activity/group_list.html");
    }
    public static void groupAdd() {
		WxUser wxUser =  getWXUser();
		renderArgs.put("wxUser",wxUser);
        render("modules/xjldw/mobile/activity/group_add.html");
    }
    public static void groupDetail() {
        renderArgs.put("groupBuyId", params.get("groupBuyId"));
        renderArgs.put("wxUser",getWXUser());
        render("modules/xjldw/mobile/activity/group_detail.html");
     }
    /**
     * 相册
     */
    public static void albumList() {
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser",getWXUser());
    	renderArgs.put("fileId", "1");
        render("modules/xjldw/mobile/activity/album_list.html");
    }
    public static void albumAdd(){
    	WxUser wxUser = getWXUser();
		renderArgs.put("wxUser",getWXUser());
        render("modules/xjldw/mobile/activity/album_add.html");
    }
    //选择照片
    public static void albumAddImage(){
    	WxUser wxUser = getWXUser();
		renderArgs.put("wxUser",getWXUser());
        render("modules/xjldw/mobile/activity/album_add_image.html");
    }
    //上传照片
    public static void albumUploadImage(){
    	WxUser wxUser = getWXUser();
		renderArgs.put("wxUser",getWXUser());
        render("modules/xjldw/mobile/activity/album_upload_image.html");
    }
    //上传照片成功之后的提示
    public static void albumUploadImageOk(){
    	WxUser wxUser = getWXUser();
		renderArgs.put("wxUser",getWXUser());
        render("modules/xjldw/mobile/activity/album_upload_image_ok.html");
    }
    public static void addClassAdmin(){
   	    String userKey = session.getId()+"_userkey";
    	WxUser wxUser =  getWXUser();
    	wxUser=WxUser.getFindByOpenId(wxUser.wxOpenId);
    	wxUser=WxUserBo.save(wxUser);
    	if(wxUser!=null){
    		XjlDwWxClass xjlDwWxClass=new XjlDwWxClass();
    		xjlDwWxClass.classId=1l;
    		xjlDwWxClass.isDefault="Y";
    		xjlDwWxClass.wxOpenId=wxUser.wxOpenId;
    		xjlDwWxClass=XjlDwWxClassBo.save(xjlDwWxClass);
    	}
    	wxUser = WxUser.getFindByOpenId(wxUser.wxOpenId);
    	Cache.set(userKey,wxUser);
    	renderArgs.put("wxUser",wxUser);
    	render("modules/xjldw/mobile/main.html");
    }
    
    //上传头像
    public static void uploadLogo() {

        String url = params.get("url");
        String openId = params.get("openId");
        Logger.info("上传图片url====" + url);

        Logger.info(url);
        String picUrl = downloadPhoto(url,openId, "A",null);
        WxUser wxUser =  getWXUser();
        XjlDwFileBo.saveImage(picUrl, wxUser.wxOpenId);
        if(picUrl.contains(".json;")&&picUrl.endsWith("encoding=utf-8")){
        	//上传失败
        	nok("上传失败，公众号没有足够的权限");
        }
        Logger.error("==========oldFileDelete ",params.get("oldFileDelete"));
        if (params.get("oldFileDelete")!=null) {
        	//如果上传图片的时候指定了旧图片（或文件），则上传成功后删除原文件
            BaseController.deleteFile(params.get("oldFileDelete"));
		}
        Logger.info("picUrl" + picUrl);
        ok(picUrl);
    }


}

