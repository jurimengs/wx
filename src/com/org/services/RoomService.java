package com.org.services;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.org.annotations.Init;
import com.org.dao.CommonDao;
import com.org.utils.BeanUtils;

/**
 * 用于同步微信用户信息
 * @author Administrator
 *
 */
@Init
public class RoomService {
	// 
	private static String sql_query_by_id = "select * from wx_room where roomid = ?";
	
	private static String sql_query_all = "select * from wx_room";

	public JSONObject query(Long roomId){
		Map<Integer, Object> params = new HashMap<Integer, Object>();
		params.put(1, roomId);
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");
		JSONObject res = commonDao.queryJSONObject(sql_query_by_id, params, null);
		return res;
	}
	
	/**
	 * 由于Room的类型存在不同，所以这里查询不用泛型返回，而是使用json，便于后面业务处理
	 * @return
	 */
	public JSONArray queryRoomList(){
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");;
		JSONArray list = commonDao.queryJSONArray(sql_query_all);
		return list;
	}
}
