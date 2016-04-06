package com.org.rute;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.caches.RoomContainer;
import com.org.caches.WxUserContainer;
import com.org.interfaces.room.Room;
import com.org.interfaces.rute.Business;
import com.org.model.wx.AbstractRoom;
import com.org.model.wx.WxUser;
import com.org.wx.utils.WxUtil;

/**
 * request from wx , type is "text"
 * @author Administrator
 *
 */
public class TypeText implements Business<String> {
	private Log log = LogFactory.getLog(TypeText.class);
	private JSONObject xmlJson;

	public TypeText(JSONObject xmlJson) {
		this.xmlJson = xmlJson;
	}
	
	@Override
	public String call() {
		// ����Ϣ����
		String msgFromOpenid = xmlJson.getString("FromUserName");
		String content = xmlJson.getString("Content");
		
		WxUser wxUser = WxUserContainer.getInstance().getLocalUser(msgFromOpenid);
		// TODO 
		//wxUser.setRoomId(Long.valueOf("0"));
		
		String returnStr = "";
		Long roomid = wxUser.getRoomId();
		if(roomid != null) {
			// �������������
			log.info("��ǰ�û����ڷ���� " + roomid);
			AbstractRoom cr = RoomContainer.getInstance().getRoomById(roomid);
			if(cr != null) {
				// ���͵Ĳ�����Ӧ�����ɷ�����ɣ���������Լ���ҵ���߼������緢�ͣ�����+�洢
				cr.sendToAll(wxUser, content);
			} else {
				log.info("���䲻����: " + roomid);
			}
		} else {
			returnStr = WxUtil.autoReply(xmlJson);
		}

		return returnStr;
	}

	public JSONObject getXmlJson() {
		return xmlJson;
	}

	public void setXmlJson(JSONObject xmlJson) {
		this.xmlJson = xmlJson;
	}

}
