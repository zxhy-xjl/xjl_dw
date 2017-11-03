package models.modules.mobile;
import java.sql.*;
import java.util.Date;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import utils.StringUtil;
import utils.jpa.ParamObject;
import utils.jpa.SQLResult;
import utils.jpa.sql.SQLParser;

@Entity
@Table(name = "xjl_dw_menu")
public class XjlDwMenu extends GenericModel{

	@Id
	@Column(name = "MENU_ID")
	public Long menuId;

	@Column(name = "MENU_NAME")
	public String menuName;

	@Column(name = "MENU_CODE")
	public String menuCode;

	@Column(name = "MENU_URL")
	public String menuUrl;

	@Column(name = "MENU_LOGO")
	public String menuLogo;

	@Column(name = "MENU_LEVEL")
	public Long menuLevel;

	@Column(name = "MENU_ORDER")
	public Long menuOrder;

	@Column(name = "PARENT_MENU_ID")
	public Long parentMenuId;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;

	@Transient
	public List<XjlDwMenu> listZzbMenu = new ArrayList<XjlDwMenu>();
	
	public static List<XjlDwMenu> queryMenuListByPage(Long roleId,Integer menuLevel,Long parentMenuId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("roleId", roleId);
		condition.put("menuLevel", menuLevel);
		condition.put("parentMenuId", parentMenuId);
		int pageIndex = 1;
		int pageSize = 1000;
		
		String sql = "select b.menu_id,b.menu_name,b.menu_url,b.menu_level,b.menu_logo,b.menu_order,b.status,b.parent_menu_id ";
		sql += "from xjl_dw_role_menu a ";
		sql += "left join  xjl_dw_menu b on a.menu_id=b.menu_id ";
		sql += "where 1=3 [or a.role_id=:roleId ][and b.menu_level=l:menuLevel ][and b.parent_menu_id=l:parentMenuId ] and b.status ='0AA' ";
		sql += "order by b.menu_order asc,b.create_time desc";

		SQLResult ret = SQLParser.parseSQL(sql, condition);

		Query query = JPA.em().createNativeQuery(ret.getSql());
		int i = 1;
		for (ParamObject o : ret.getParams()) {
			query.setParameter(i++, o.getValue());
		}
		List<Object[]> data = query.setFirstResult((pageIndex - 1) * pageSize)
				.setMaxResults(pageSize).getResultList();
		List<XjlDwMenu> retData =  new ArrayList<XjlDwMenu>();
		XjlDwMenu zzbMenu;
		for(Object[] m:data){
			zzbMenu = new XjlDwMenu();
			if(m[0]!=null)
				zzbMenu.menuId = StringUtil.getLong(m[0].toString());
			if(m[1]!=null)
				zzbMenu.menuName = m[1].toString();
			if(m[2]!=null)
				zzbMenu.menuUrl = m[2].toString();
			if(m[3]!=null)
				zzbMenu.menuLevel = StringUtil.getLong(m[3].toString());
			if(m[4]!=null)
				zzbMenu.menuLogo = m[4].toString();
			if(m[5]!=null)
				zzbMenu.menuOrder = StringUtil.getLong(m[5].toString());
			if(m[6]!=null)
				zzbMenu.status = m[6].toString();
			if(m[7]!=null)
				zzbMenu.parentMenuId = StringUtil.getLong(m[7].toString());
			
			retData.add(zzbMenu);
		}
		return retData;
	}
}
