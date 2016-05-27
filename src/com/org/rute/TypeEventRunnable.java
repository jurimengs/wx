package com.org.rute;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.caches.RoomContainer;
import com.org.caches.WxUserContainer;
import com.org.common.CommonConstant;
import com.org.model.wx.WxUser;
import com.org.services.WxUserService;
import com.org.utils.BeanUtils;
import com.org.utils.DateUtil;
import com.org.wx.utils.MessageUtil;
import com.org.wx.utils.SuanMing;
import com.org.wx.utils.WxUserUtil;
import com.org.wx.utils.WxUtil;

/**
 * request from wx , type is "event"
 * @author Administrator
 *
 */
public class TypeEventRunnable implements Runnable {
	private Log log = LogFactory.getLog(TypeEventRunnable.class);
	private JSONObject xmlJson;

	public TypeEventRunnable(JSONObject xmlJson) {
		this.xmlJson = xmlJson;
	}
	
	public JSONObject getXmlJson() {
		return xmlJson;
	}

	public void setXmlJson(JSONObject xmlJson) {
		this.xmlJson = xmlJson;
	}

	@Override
	public void run() {
		String FromUserName = xmlJson.getString("FromUserName");
		String Event = xmlJson.getString("Event");
		// 拿事件类型 和 点击的按钮key值判断 可以决定业务类型
		if(Event.equals("CLICK")) {
			log.info("CLICK 事件处理");
			String EventKey = xmlJson.getString("EventKey"); // 对应自定义的key 值
			JSONObject returns = null;
			String content = "";
			WxUser wxUser = WxUserContainer.getInstance().getLocalUser(FromUserName);
			
			JSONObject res = null;
			if(WxUtil.ENTER_CHATING_ROOM.equals(EventKey)) {
				// 加入聊天室
				if(wxUser.getRoomId() != null) {
					// 如果在房间中，先退出
					wxUser.exitChatingRoom();
				}
				res = wxUser.joininChatingRoom(Long.valueOf(RoomContainer.DEFAULT_ROOM_ID));
			} else if(WxUtil.EXIT_CHATING_ROOM.equals(EventKey)) {
				// 退出聊天室
				res = wxUser.exitChatingRoom();
			} else if(WxUtil.ENTER_STORE_ROOM.equals(EventKey)) {
				// 进入故事模式
				if(wxUser.getRoomId() != null) {
					// 如果在房间中，先退出
					wxUser.exitChatingRoom();
				}
				res = wxUser.joininChatingRoom(Long.valueOf(RoomContainer.STORY_ROOM_ID));
			} else if(WxUtil.EXIT_STORE_ROOM.equals(EventKey)) {
				// 退出故事模式
				res = wxUser.exitChatingRoom();
				
			}
			
			if(res != null) {

				// 回复文本消息
				if(res.getString(CommonConstant.RESP_CODE).equals("10000")) {
					content = res.getString(CommonConstant.RESP_MSG);
				} else {
					content = "进入失败："+res.getString(CommonConstant.RESP_MSG) ;
				}
			} else {
				content = "啊出问题啦！向管理员反映吧！";
			}
			
			returns = MessageUtil.sendToOne(content, FromUserName);
			if(returns != null && (returns.getInt("errcode")==0)) {
				log.info("消息推送成功");
			}
		} else if(Event.equals("unsubscribe") || Event.equals("subscribe")) {
			
			JSONObject actual = WxUserUtil.getUserBaseInfo(FromUserName);
			String subscribe = actual.containsKey("subscribe") ? actual.getString("subscribe") : null;
			String subscribe_time = DateUtil.getDateStringByFormat(DateUtil.yyyyMMddHHmmss);
			String nickname = actual.containsKey("nickname") ? actual.getString("nickname") : null;
			String sex = actual.containsKey("sex") ? actual.getString("sex") : null;
			String headimgurl = actual.containsKey("headimgurl") ? actual.getString("headimgurl") : null;
			String country = actual.containsKey("country") ? actual.getString("country") : null;
			String province = actual.containsKey("province") ? actual.getString("province") : null;
			String city = actual.containsKey("city") ? actual.getString("city") : null;
			
			WxUserService uService = (WxUserService)BeanUtils.getBean("wxUserService");
			uService.saveOrUpdate(FromUserName, nickname, sex, subscribe_time, subscribe, headimgurl, country, province, city);
		} else if(Event.equals("LOCATION")) {
			
			// 先判断这个人今天是否已经被推送过算命消息了
			WxUserService wxService = (WxUserService)BeanUtils.getBean("wxUserService");
			boolean res = wxService.hasSentSuanming(FromUserName);
			if(res) {
				log.info("LOCATION 今天已算过命了: " + FromUserName);
				return;
			}
			// {"ToUserName":"gh_b4c1774a1ef7","FromUserName":"osp6swrNZiWtEuTy-Gj1cBVA1l38","CreateTime":"1458029241","MsgType":"event","Event":"LOCATION","Latitude":"31.166275","Longitude":"121.389099","Precision":"30.000000"}
			// 经度
			String latitude = xmlJson.getString("Latitude");
			// 纬度
			String longitude = xmlJson.getString("Longitude");
			// 精度 基本没用
			//String Precision = xmlJson.getString("Precision");
			
			StringBuffer temp = new StringBuffer();
//			temp.append("您的当前位置:\n");
//			temp.append("纬度:").append(Latitude);
//			temp.append("\n");
//			temp.append("经度:").append(Longitude);
			
			temp.append("根据您当前的经度和纬度推算您今天的时运:\n");
			temp.append(SuanMing.byLocation(latitude, longitude));

			log.info("LOCATION 发送微信客户端的内容: " + temp.toString());
			
			JSONObject returns = MessageUtil.sendToOne(temp.toString(), FromUserName);
			if(returns != null && (returns.getInt("errcode")==0)) {
				log.info("消息推送成功");
			}
		}
		// 
	}
	
}
