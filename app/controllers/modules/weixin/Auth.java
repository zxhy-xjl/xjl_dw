package controllers.modules.weixin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import models.modules.mobile.WxServer;
import models.modules.mobile.WxUser;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import play.Logger;
import play.mvc.Controller;
import utils.CheckSignature;
import utils.HttpClientUtil;
import utils.InputStreamUtils;
import utils.SecurityUtil;
import controllers.comm.Sign;
import controllers.modules.mobile.bo.WxServerBo;
import controllers.modules.mobile.bo.WxUserBo;

public class Auth extends Controller {
	public static void get() {
		String echostr = params.get("echostr");
		String signature = params.get("signature");
		boolean isWeiXin = false;
		if (signature != null && !signature.isEmpty()) {
			isWeiXin = CheckSignature.doCheck(params);
		}
		if (isWeiXin) {
			renderText(echostr);
		} else {
			renderText("error signature:" + signature + ", echostr: " + echostr);
		}
	}
	public static void post() throws ServletException, IOException {
		response.encoding = "UTF-8";
		request.encoding = "UTF-8";
		try {
			String reqXML = InputStreamUtils.getString(request.body);
			Logger.info("系统接收:[\n " + reqXML + " \n");
			Document doc = DocumentHelper.parseText(reqXML);
			Element root = doc.getRootElement();
			String fromUsername = root.elementText("FromUserName");
            String toUsername = root.elementText("ToUserName");
			String msgType = root.element("MsgType").getTextTrim();
			//文字回复模板
			String textTpl = "<xml>"+
					  "<ToUserName><![CDATA[%1$s]]></ToUserName>"+
					  "<FromUserName><![CDATA[%2$s]]></FromUserName>"+
					  "<CreateTime>%3$s</CreateTime>"+
					  "<MsgType><![CDATA[%4$s]]></MsgType>"+
					  "<Content><![CDATA[%5$s]]></Content>"+
					  "<FuncFlag>0</FuncFlag>"+
					  "</xml>";	
			String time = new Date().getTime()+"";
			Long schoolId = null;
			String appId = "";
			String appSecret = "";
			String accessToken = "";
			String requestUrl = "";
			JSONObject returnJson = null;
			WxServer wxServer = null;
			WxUser wxUser = null;
			if(("image").equals(msgType)){
				//用户发的是图片
				String picUrl = root.elementText("PicUrl");
				String content="上传的图片访问URL：\r"+picUrl;
				msgType = "text";
            	String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, content);
            	renderText(resultStr == null ? "" : resultStr);
			}else if(("location").equals(msgType)){
				//用户发的是位置
				double Location_X = Double.parseDouble(root.elementText("Location_X"));
				double Location_Y = Double.parseDouble(root.elementText("Location_Y"));
				String Scale = root.elementText("Scale");
				String Label = root.elementText("Label");
				String content="位置坐标信息：\r";
				content += Location_X+","+Location_Y+","+Scale+"\r";
				content += "位置："+Label;
				msgType = "text";
            	String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, content);
            	renderText(resultStr == null ? "" : resultStr);
				
			}else if(("event").equals(msgType)){
				//事件｜关注、取消关注
				//事件类型
            	String event=root.elementText("Event");
//            	//获取带参数二维码中的参数、不是带参数的情况为空值
//            	String qrscene = root.elementText("EventKey");
        		wxServer = WxServerBo.readWxServerByWxCode(toUsername);
        		if(wxServer==null){
    				Logger.error("==========================公众号在我们系统中没有配置");
        		}else{
        			schoolId = wxServer.schoolId;
        			//关注
                	if(event.equals("subscribe")){
                		//关注
                		if(wxServer!=null){
                			//找到对应的商户
                			appId = SecurityUtil.decrypt(wxServer.appId);
                			appSecret = SecurityUtil.decrypt(wxServer.appSecret);
                			wxUser = WxUserBo.readWxUserByOpenIdAndSchoolId(fromUsername, schoolId);
                			if(wxUser!=null){
                				//以前关注过，不用重复记录
                				wxUser.isConcerned = "Y";
				    			wxUser = WxUserBo.save(wxUser);
    							String content="欢迎您的归来，您的关注是我前进最大动力  ❤❤";
    							msgType = "text";
    			            	String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, content);
    		    				Logger.info("==========================以前关注过，不用重复记录"+fromUsername+"|"+schoolId);
    			            	renderText(resultStr == null ? "" : resultStr);
                			}else{
                				//以前没有关注过
                				wxUser = new WxUser();
                				wxUser.wxOpenId = fromUsername;
                				wxUser.schoolId = schoolId;
                				accessToken = Sign.getAccessToken(appId,appSecret);
                				requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+fromUsername+"&lang=zh_CN";
                				returnJson = HttpClientUtil.invoke(requestUrl, "POST", null);
    					    	if(returnJson!=null&&returnJson.containsKey("openid")&&returnJson.containsKey("nickname")){
    			    				Logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>userInfo"+returnJson);
    					    		wxUser.nickName = returnJson.getString("nickname");
    				    			wxUser.sex= returnJson.getString("sex");
    				    			wxUser.sex = "1".equals(wxUser.sex)?"男":"女";
    				        		wxUser.language = returnJson.getString("language");
    				    			wxUser.city = returnJson.getString("city");
    				    			wxUser.province = returnJson.getString("province"); 
    				    			wxUser.country = returnJson.getString("country");
    				    			wxUser.headImgUrl = returnJson.getString("headimgurl").replace("/0", "/132");
    				    			wxUser.isConcerned = "Y";
    				    			//wxUser.wxRole="0";
    				    			wxUser = WxUserBo.save(wxUser);
    				    			if(wxUser!=null){
    				    				//关注成功
    									String content="感谢您的关注，您的关注是我前进最大动力  ❤❤";
    									msgType = "text";
    					            	String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, content);
    					            	renderText(resultStr == null ? "" : resultStr);
    				    			}else{
    				    				Logger.info("==========================关注保存失败");
    				    			}
    					    	}else{
    			    				Logger.error("==========================returnJson"+returnJson);
    					    	}
                			}
                		}
                	}else if(event.equals("unsubscribe")){
                		//取消关注
            			wxUser = WxUserBo.readWxUserByOpenIdAndSchoolId(fromUsername, schoolId);
            			if(wxUser!=null){
            				wxUser.isConcerned = "N";
            				wxUser = WxUserBo.save(wxUser);
            				if(wxUser!=null){
                				Logger.info("==========================取消关注"+fromUsername);
            				}else{
                				Logger.error("==========================取消关注===更新状态保存失败"+fromUsername);
            				}
            			}else{
            				Logger.error("==========================之前没有保存数据，下次来再保存吧"+fromUsername+"|vnoId:"+schoolId);
            			}
                	}
        		}
			}else if(("text").equals(msgType)){
				//用户发的是文字
				String keyword = root.elementTextTrim("Content");
				if(keyword.equals("id")){
					String content="微信标识："+toUsername;
					msgType = "text";
	            	String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, content);
	            	renderText(resultStr == null ? "" : resultStr);
				}else if(keyword.equals("idd")){
					String content="个人微信号："+fromUsername;
					msgType = "text";
	            	String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, content);
	            	renderText(resultStr == null ? "" : resultStr);
				}else{
					renderText(keyword);
				}
			}
//			RequestHandle handle = RequestHandleFactory.getHandle(reqXML);
//			String resp = handle.doHandle();
//			Logger.info("系统回复: [\n " + resp + " \n]");
//			renderText(resp == null ? "" : resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
