package com.org.caches;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.interfaces.caches.Container;
import com.org.model.wx.WxUser;
import com.org.services.WxUserService;
import com.org.utils.BeanUtils;
import com.org.wx.utils.WxUserUtil;

/**
 * 用户信息容器。用于缓存用户信息
 * @author Administrator
 *
 */
public class WxUserContainer implements Container{
	private static Map<String, WxUser> wxUserInfoMap;
	private static WxUserContainer temp;
	private WxUserContainer(){}

	public JSONArray getWxUserList(){
		// TODO 
		return null;
	}

	/**
	 * 初始化用户缓存信息
	 */
	public void init(){
		wxUserInfoMap = new HashMap<String, WxUser>();
		WxUserService wxService = (WxUserService)BeanUtils.getBean("wxUserService");
		List<WxUser> array = wxService.queryAll(null);
		
		WxUser temp = null;
		String key = null;
		if(array != null) {
			for (int i = 0; i < array.size(); i++) {
				temp = array.get(i);
				key = temp.getOpenid();
				wxUserInfoMap.put(key, temp);
			}
			log.info("已初始化用户信息"+ array.size() +"条");
		} else {
			log.info("未查询到已存在用户");
		}
	}

	/**
	 * 获取用户信息, 先从缓存中获取，没有再从数据库获取
	 * @param openid
	 * @return
	 */
	public WxUser getLocalUser(String openid){
		WxUser wxUser = null;
		if(wxUserInfoMap.containsKey(openid)) {
			wxUser = wxUserInfoMap.get(openid);
		} else {
			log.info("getLocalUser 缓存无信息，执行微信查询");
			// 根据openid去微信查询用户信息
			JSONObject res = WxUserUtil.getUserBaseInfo(openid);
			// 
			WxUserService wxService = (WxUserService)BeanUtils.getBean("wxUserService");
			// 保存，并返回数据库保存的用户信息
			log.info("getLocalUser 微信查询返回信息保存到本地");
			wxUser = wxService.saveAndReturn(res);
			wxUserInfoMap.put(openid, wxUser);
		}

		return wxUser;
	}
	
	/**
	 * 缓存所有用户信息，只在全局缓存用户信息的时候调用，不适用于部分用户信息缓存
	 * @param args 数据库查出的用户信息列表
	public void cacheUserInfoArray(JSONArray args){
		wxUserInfoMap = new HashMap<String, JSONObject>();
		JSONObject temp = null;
		String keytemp = null;
		for (int i = 0; i < args.size(); i++) {
			temp = args.getJSONObject(i);
			keytemp = temp.getString("openid");
			wxUserInfoMap.put(keytemp, temp);
		}
	}
	*/
	
	/**
	 * 缓存单个用户信息
	 * @param args
	 */
	public void cacheUserInfo(WxUser temp){
		if(temp != null) {
			String keytemp = temp.getOpenid();
			wxUserInfoMap.put(keytemp, temp);
		}
	}
	
	public static WxUserContainer getInstance(){
		if(temp == null) {
			temp = new WxUserContainer();
		}
		return temp;
	}
	
	private static Log log = LogFactory.getLog(WxUserContainer.class);
}
