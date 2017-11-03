package controllers.comm;

import java.io.Serializable;

import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwUser;

public class SessionInfo implements Serializable {
	
	private WxUser wxUser;
	private String deviceFlag;

	public WxUser getWxUser() {
		return wxUser;
	}
	public void setWxUser(WxUser wxUser) {
		this.wxUser = wxUser;
	}
	public String getDeviceFlag() {
		return deviceFlag;
	}

	public void setDeviceFlag(String deviceFlag) {
		this.deviceFlag = deviceFlag;
	}
}
