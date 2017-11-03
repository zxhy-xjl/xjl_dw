package controllers.modules.mobile.bo;

import java.util.List;

import controllers.modules.mobile.filter.MobileFilter;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwWxClass;
import models.modules.mobile.XjlDwWxStudent;
import utils.DateUtil;
import utils.SeqUtil;
import utils.StringUtil;
/**
 * @author    lilisheng
 * @version   创建时间：2017-09-25 下午02:46:14
 * @describe  家长关注学生的关系表
*/
public class XjlDwWxStudentBo {
	/**
	 * 增加或者修改关注学生，如果是默认关注学生，则同步修改默认关注班级
	 * @param xjlDwWxStudent
	 * @return
	 */
	public static XjlDwWxStudent save(XjlDwWxStudent xjlDwWxStudent) {
		if (xjlDwWxStudent.studentWxId != null) {
		}
		if (xjlDwWxStudent.studentWxId == null) {
			xjlDwWxStudent.studentWxId = SeqUtil.maxValue("xjl_dw_wx_student", "student_wx_id");
			xjlDwWxStudent.status = "0AA";
			xjlDwWxStudent.createTime = DateUtil.getNowDate();
		}
		xjlDwWxStudent = xjlDwWxStudent.save();
//		if (StringUtil.eq("Y", xjlDwWxStudent.isDefault)){
//			//如果是默认关注学生，则需要同步更新关注班级和修改缓存对象
//			//1.取消原有默认关注，
//			XjlDwWxClass oldDefaultClass = XjlDwWxClass.getDefaultByOpenId(xjlDwWxStudent.wxOpenId);
//			if (oldDefaultClass != null){
//				oldDefaultClass.isDefault = "";
//				XjlDwWxClassBo.save(oldDefaultClass);
//			}
//			//2设置新默认关注
//			XjlDwWxClass newDefaultClass = XjlDwWxClass.getByOpenIdAndClassId(xjlDwWxStudent.wxOpenId,xjlDwWxStudent.dwClass.classId);
//			if (newDefaultClass == null){
//				newDefaultClass = new XjlDwWxClass();
//				newDefaultClass.classId = xjlDwWxStudent.dwClass.classId;
//				newDefaultClass.wxOpenId = xjlDwWxStudent.wxOpenId;
//			}
//			newDefaultClass.isDefault = "Y";
//			XjlDwWxClassBo.save(newDefaultClass);
//			//3.修改缓存中的对象
//			WxUser wxUser = MobileFilter.getWXUser();
//			wxUser.currentClass = xjlDwWxStudent.dwClass;
//			wxUser.currentStudent = xjlDwWxStudent.student;
//		}
		return xjlDwWxStudent;
	}
	// 删除
	public static XjlDwWxStudent del(XjlDwWxStudent xjlDwWxStudent) {
		if (xjlDwWxStudent != null) {
			xjlDwWxStudent.status = "0XX";
			xjlDwWxStudent = xjlDwWxStudent.save();
			return xjlDwWxStudent;
		}
		return null;
	}
}