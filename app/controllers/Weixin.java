package controllers;

import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;

import models.modules.mobile.WxServer;
import net.sf.json.JSONObject;
import play.Logger;
import play.cache.Cache;
import utils.EncoderHandler;
import utils.HttpClientUtil;
import utils.SysParamUtil;
import controllers.comm.BaseController;
import controllers.modules.weixin.utils.AccessTokenHolder;
import controllers.modules.weixin.utils.WXRequestAddr;

public class Weixin extends BaseController {

	public static void getWxSdkInfo(Long vnoId) {
		String url = params.get("url");
		String openId = params.get("openId");

		Logger.info(" +++getWxSdkInfo vnoId =%s ", vnoId);
		Logger.info(" +++getWxSdkInfo openId =%s ", openId);

		WxServer server = WxServer.findById(1l);

		String appId = server.appId;
		Logger.info(" +++getWxSdkInfo appId =%s ", appId);

		String timestamp = create_timestamp();
		String nonce = create_nonce_str();
		String accessToken = AccessTokenHolder.getAccessTokenByAppId(appId);

		String jsapi_ticket = (String) Cache.get(appId);

		if (jsapi_ticket == null) {
			String getTicket = String.format(WXRequestAddr.POST_JSAPI_TICKET,
					accessToken);
			JSONObject json = HttpClientUtil.invoke(getTicket, "POST", null);
			// 去SHA1 散列值
			jsapi_ticket = json.getString("ticket");
			Cache.set(appId, jsapi_ticket, "1h");
		}

		String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket,
				"timestamp=" + timestamp, "noncestr=" + nonce, "url=" + url };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String content = paramArr[0].concat("&" + paramArr[1])
				.concat("&" + paramArr[2]).concat("&" + paramArr[3]);

		//
		// String getSinature = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
		// + nonce + "&timestamp=" + timestamp + "&url=" + url;
		// Logger.info("getSinature = " + content);

		// String sortStr = CheckSignature.getSortString(timestamp, nonce,
		// token);
		// 去SHA1 散列值
		String signature = EncoderHandler.encode("SHA1", content);

		// Logger.info("jsapi_ticket = " + jsapi_ticket);
		// Logger.info("signature = " + signature);
		// Logger.info("timestamp = " + timestamp);
		// Logger.info("nonce = " + nonce);
		 Logger.info("url = " + url);
		HashMap map = new HashMap();
		map.put("appId", appId);
		map.put("signature", signature);
		map.put("timestamp", timestamp);
		map.put("nonce", nonce);
		map.put("jsapi_ticket", jsapi_ticket);
		map.put("url", url);
		renderJSON(map);
		// renderTemplate("/weixin.html", appId, signature, timestamp, nonce);
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return "Wm3WZYTPz0wzccnW";
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
