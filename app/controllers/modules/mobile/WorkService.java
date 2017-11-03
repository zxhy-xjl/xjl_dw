package controllers.modules.mobile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import models.modules.mobile.ModelUtils;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlDwAlbum;
import models.modules.mobile.XjlDwAlbumTemplate;
import models.modules.mobile.XjlDwArticle;
import models.modules.mobile.XjlDwClass;
import models.modules.mobile.XjlDwExam;
import models.modules.mobile.XjlDwExamGrade;
import models.modules.mobile.XjlDwExamSubject;
import models.modules.mobile.XjlDwGroupBuy;
import models.modules.mobile.XjlDwGroupBuyItem;
import models.modules.mobile.XjlDwHomework;
import models.modules.mobile.XjlDwHomeworkModel;
import models.modules.mobile.XjlDwNotice;
import models.modules.mobile.XjlDwWxClass;
import models.modules.mobile.XjlDwStudent;
import models.modules.mobile.XjlDwSubject;
import models.modules.mobile.XjlDwWxStudent;

import play.cache.Cache;
import play.i18n.Messages;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.bo.WxUserBo;
import controllers.modules.mobile.bo.XjlDwArticleBo;
import controllers.modules.mobile.bo.XjlDwExamBo;
import controllers.modules.mobile.bo.XjlDwExamGradeBo;
import controllers.modules.mobile.bo.XjlDwExamSubjectBo;
import controllers.modules.mobile.bo.XjlDwGroupBuyBo;
import controllers.modules.mobile.bo.XjlDwGroupBuyItemBo;
import controllers.modules.mobile.bo.XjlDwHomeworkBo;
import controllers.modules.mobile.bo.XjlDwHomeworkModelBo;
import controllers.modules.mobile.bo.XjlDwWxStudentBo;
import controllers.modules.mobile.filter.MobileFilter;
import controllers.modules.mobile.bo.XjlDwNoticeBo;
import utils.DateUtil;
import utils.StringUtil;

/**
 * 作业控制器 作业/成绩
 * 
 * @author lilisheng
 * 
 */
public class WorkService extends MobileFilter {
	
	/**
	 * 考试成绩单列表
	 */
	public static void queryExam() {
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		WxUser wxUser = getWXUser();
		Map condition = params.allSimple();
		condition.put("classId", wxUser.currentClass.classId);
		Map ret = XjlDwExam.query(condition, pageIndex, pageSize);
		List<XjlDwExam> list = (List<XjlDwExam>)ret.get("data");
		List<Map> examInfoList = new ArrayList();
		for (XjlDwExam xjlDwExam : list) {
			Map examInfo = new HashMap();
			examInfo.put("exam", xjlDwExam);
			Map mma = XjlDwExamGrade.queryMaxMinAvg(xjlDwExam.examId);
			examInfo.put("mma", mma);
			examInfoList.add(examInfo);
		}
		ret.put("data", examInfoList);
		ok(ret);
	}
	/**
	 * 保存考试科目，如果考试id为空，则创建考试
	 */
	public static void saveExamSubject(){
		WxUser wxUser = getWXUser();
		XjlDwExam exam = null;
		if (StringUtil.isNotEmpty(params.get("examId"))){
			exam = XjlDwExam.findById(Long.parseLong(params.get("examId")));
		} else {
			exam = new XjlDwExam();
			exam.classId = wxUser.currentClass.classId;
			exam.examTitle = params.get("examTitle");
			if (StringUtil.isEmpty(params.get("examDate"))){
				exam.examDate = DateUtil.getNowDate();
			} else {
				exam.examDate = DateUtil.strToDateByFormat(params.get("examDate"), "yyyy-MM-dd");
			}
			exam.wxOpenId = wxUser.wxOpenId;
			exam = XjlDwExamBo.save(exam);
		}
		String [] examSubjectList=params.getAll("examSubjectList");
        JSONArray examSubjectJsonList = JSONArray.fromObject(examSubjectList); 
        XjlDwExamSubjectBo.delByExam(exam.examId);
        //List<JSONObject> subjectList = new ArrayList<JSONObject>();
        for (int i = 0; i < examSubjectJsonList.size(); i++){
        	if (examSubjectJsonList.getJSONObject(i).getBoolean("isExamSubject")){
        		long subjectId = examSubjectJsonList.getJSONObject(i).getLong("subjectId");
        		XjlDwExamSubjectBo.add(exam.examId, subjectId);
        		//subjectList.add(examSubjectJsonList.getJSONObject(i));
        	}
        }
        //WorkService.initExamGrad(exam.examId,subjectList);
        ok(exam);
	}
//	public static void initExamGrad(Long examId, List<JSONObject> subjectList){
//		WxUser wxUser = getWXUser();
//		List<XjlDwStudent> studentList = (List<XjlDwStudent>)XjlDwStudent.queryByClassId(wxUser.currentClass.classId).get("data");
//		for (XjlDwStudent xjlDwStudent : studentList) {
//			for (JSONObject subject : subjectList) {
//				XjlDwExamGrade grade = new XjlDwExamGrade();
//				grade.examGrade = 0D;
//				grade.examId = examId;
//				grade.studentId = xjlDwStudent.studentId;
//				grade.subjectId = subject.getLong("subjectId");
//				
//				XjlDwExamGradeBo.save(grade);
//			}
//		}
//	}
	/**
	 * 得到考试科目列表
	 */
	public static void queryExamSubjectList(){
		WxUser wxUser = getWXUser();
		Long examId = Long.parseLong(params.get("examId"));
		Map map = XjlDwExamSubject.queryByExam(examId);
        ok(map);
	}
	/**
	 * 保存考试成绩
	 */
	public static void saveExamGrade(){
		WxUser wxUser = getWXUser();
		Long examId = Long.parseLong(params.get("examId"));
		Long studentId = Long.parseLong(params.get("studentId"));
		String [] grade=params.getAll("grade");
        JSONArray gradeList = JSONArray.fromObject(grade); 
        for (int i = 0; i < gradeList.size(); i++){
        	JSONObject gradeJson = gradeList.getJSONObject(i);
        	long gradeId = gradeJson.getLong("gradeId");
        	XjlDwExamGrade examGrade = null;
        	if (gradeId==0){
        		examGrade = new XjlDwExamGrade();
        		examGrade.examId = examId;
        		examGrade.studentId = studentId;
        		examGrade.subjectId = gradeJson.getLong("subjectId");
        	} else {
        		examGrade = XjlDwExamGrade.findById(gradeId);
        	}
        	examGrade.examGrade = gradeJson.getDouble("gradeValue");
        	XjlDwExamGradeBo.save(examGrade);
        }
		ok();
	}
	/**
	 * 得到所有学生成绩信息
	 */
	public static void queryStudentExamGradeList(){
		WxUser wxUser = getWXUser();
		List<XjlDwSubject> subjectList = XjlDwSubject.all().fetch();
		
		Long examId = StringUtil.getLong(params.get("examId"));
		List<XjlDwExamSubject> examSubjectList = (List<XjlDwExamSubject>)XjlDwExamSubject.queryByExam(examId).get("data");
		
		Map map = XjlDwStudent.queryByClassId(wxUser.currentClass.classId);
		List<XjlDwStudent> studentList = (List<XjlDwStudent>)map.get("data");
		List<Map> studentInfoList = new ArrayList<Map>();
		
		for (XjlDwStudent student : studentList) {
			Map studentInfo = new HashMap();
			studentInfo.put("student", student);
			List<Map> gradeList = new ArrayList();
			List<XjlDwExamGrade> studentGradeList = (List<XjlDwExamGrade>)XjlDwExamGrade.queryByStudentAndExam(examId, student.studentId).get("data");
			for (XjlDwExamSubject xjlDwExamSubject : examSubjectList) {
				Map gradeInfo = new HashMap();
				gradeInfo.put("subjectId", xjlDwExamSubject.subjectId);
				for (XjlDwSubject xjlDwSubject : subjectList) {
					if (xjlDwSubject.subjectId == xjlDwExamSubject.subjectId){
						gradeInfo.put("subjectTitle", xjlDwSubject.subjectTitle);
						gradeInfo.put("gradeId", "0");
						gradeInfo.put("gradeValue", "0");
						break;
					}
				}
				for (XjlDwExamGrade grade : studentGradeList) {
					if (grade.subjectId == xjlDwExamSubject.subjectId){
						gradeInfo.put("gradeId", grade.examGradeId);
						gradeInfo.put("gradeValue", grade.examGrade);
						break;
					}
				}
				gradeList.add(gradeInfo);
			}
			studentInfo.put("grade", gradeList);
			double total = 0;
			for (XjlDwExamGrade grade : studentGradeList) {
				total += grade.examGrade;
			}
			studentInfo.put("total", total);
			studentInfoList.add(studentInfo);
		}
		map.put("data", studentInfoList);
		ok(map);
	}
	/**
	 * 得到所有科目
	 */
	public static void querySubjectList(){
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		WxUser wxUser = getWXUser();
		Map condition = params.allSimple();
		Map map = XjlDwSubject.queryXjlDwSubjectListByPage(condition, pageIndex, pageSize);
		ok(map);
	}
	/**
	 * 查询考试成绩
	 */
	public static void queryExamGrade(){
		WxUser wxUser = getWXUser();
		Long examId = StringUtil.getLong(params.get("examId"));
		Long studentId = StringUtil.getLong(params.get("studentId"));
		Map map = XjlDwExamGrade.queryByStudentAndExam(examId, studentId);
		List<XjlDwExamGrade> gradeList= (List<XjlDwExamGrade>)map.get("data");
		
		ok(map);
	}
	/**
	 * 保存家庭作业
	 */
	public static void saveHomework(){
		WxUser wxUser = getWXUser();
		String homeworkString=params.get("homework");
        JSONObject homeworkJson = JSONObject.fromObject(homeworkString); 
        XjlDwHomework homework = new XjlDwHomework();
        homework.classId = wxUser.currentClass.classId;
        homework.homeworkContent = homeworkJson.getString("homeworkContent");
        homework.homeworkTitle = homeworkJson.getString("homeworkTitle");
        homework.subjectId = homeworkJson.getJSONObject("subject").getLong("subjectId");
        XjlDwHomeworkBo.save(homework);
        ok();
	}
	/**
	 * 保存标榜
	 */
	public static void saveHomeworkRemark(){
		WxUser wxUser = getWXUser();
		String remarkString=params.get("remark");
		JSONObject remarkJson = JSONObject.fromObject(remarkString); 
		XjlDwHomeworkModel model = new XjlDwHomeworkModel();
		model.homeworkId=Long.parseLong(remarkJson.getString("homeworkId"));
		model.modelTitle = remarkJson.getString("remark");
		model.modelContent=remarkJson.getString("remark");
		XjlDwHomeworkModelBo.save(model);
		ok();
	}
	/**
	 * 查询家庭作业
	 */
	public static void queryHomeworkList(){
		WxUser wxUser = getWXUser();
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		Map condition = params.allSimple();
		condition.put("classId", wxUser.currentClass.classId);
		Map map = XjlDwHomework.queryXjlDwHomeworkListByPage(condition, pageIndex, pageSize);
		List<XjlDwHomework> list = (List<XjlDwHomework>)map.get("data");
		List<Map> homeworkInfoList = new ArrayList();
		for (XjlDwHomework homework : list){
			Map homeworkInfo = new HashMap<>();
			homeworkInfo.put("homework", homework);
			if (homework.subjectId != null){
				XjlDwSubject subject = XjlDwSubject.findById(homework.subjectId);
				homeworkInfo.put("subjectTitle", subject.subjectTitle);
			} else {
				homeworkInfo.put("subjectTitle", "");
			}
			long modelCount = XjlDwHomeworkModel.queryCountByHomework(homework.homeworkId);
			homeworkInfo.put("modelCount", modelCount);
			homeworkInfoList.add(homeworkInfo);
		}
		map.put("data", homeworkInfoList);
		ok(map);
	}
	/**
	 * 
	 */
	public static void queryHomeworkRemarkList(){
		WxUser wxUser = getWXUser();
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		Map condition = params.allSimple();
		Map map = XjlDwHomeworkModel.queryXjlDwHomeworkModelListByPage(condition, pageIndex, pageSize);
		ok(map);
	}
}
