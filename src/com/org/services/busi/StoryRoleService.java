package com.org.services.busi;

import java.util.HashMap;
import java.util.Map;

import com.org.annotations.Init;
import com.org.dao.CommonDao;
import com.org.utils.BeanUtils;
import com.org.utils.DateUtil;

/**
 * @author Administrator
 */
@Init
public class StoryRoleService {
	private String sql_insert = "select * from wx_story_role where ";
	
	public boolean saveStory(Long templateid, String rolename, String message, String comments){
		
		Map<Integer, Object> params = new HashMap<Integer, Object>();
		params.put(1, templateid);
		params.put(2, rolename);
		params.put(3, message);
		params.put(4, comments);
		params.put(5, DateUtil.getDateStringByFormat(DateUtil.yyyyMMddHHmmss));
		
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");;
		boolean success = commonDao.addSingle(sql_insert, params);
		return success;
	}
}
