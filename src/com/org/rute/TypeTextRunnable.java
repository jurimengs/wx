package com.org.rute;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.caches.RoomContainer;
import com.org.caches.WxUserContainer;
import com.org.model.wx.AbstractRoom;
import com.org.model.wx.WxUser;
import com.org.wx.utils.MessageUtil;
import com.org.wx.utils.WxUtil;

/**
 * request from wx , type is "text"
 * @author Administrator
 *
 */
public class TypeTextRunnable implements Runnable {
	private Log log = LogFactory.getLog(TypeTextRunnable.class);
	private JSONObject xmlJson;

	public TypeTextRunnable(JSONObject xmlJson) {
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
		// 发消息的人
		String msgFromOpenid = xmlJson.getString("FromUserName");
		String content = xmlJson.getString("Content");
		
		WxUser wxUser = WxUserContainer.getInstance().getLocalUser(msgFromOpenid);
		// TODO 
		//wxUser.setRoomId(Long.valueOf("0"));
		
		Long roomid = wxUser.getRoomId();
		if(roomid != null) {
			// 如果在聊天室中
			log.info("当前用户所在房间号 " + roomid);
			AbstractRoom cr = RoomContainer.getInstance().getRoomById(roomid);
			if(cr != null) {
				// 发送的操作，应该是由房间完成，房间完成自己的业务逻辑，比如发送，或发送+存储
				cr.sendToAll(wxUser, content);
			} else {
				log.info("房间不存在: " + roomid);
			}
		} else {
			String returnStr = WxUtil.matchContent(content);
			JSONObject returns = MessageUtil.sendToOne(returnStr, msgFromOpenid);
			if(returns != null && (returns.getInt("errcode")==0)) {
				log.info("单独回复用户消息推送成功");
			} else {
				log.info("单独回复用户消息推送失败" + returns.getString("errmsg"));
			}
		}
	}

}
