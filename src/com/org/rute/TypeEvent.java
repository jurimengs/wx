package com.org.rute;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.caches.RoomContainer;
import com.org.caches.WxUserContainer;
import com.org.common.CommonConstant;
import com.org.common.WxConstant;
import com.org.interfaces.rute.Business;
import com.org.model.wx.WxUser;
import com.org.services.WxUserService;
import com.org.utils.BeanUtils;
import com.org.utils.DateUtil;
import com.org.wx.utils.MessageUtil;
import com.org.wx.utils.WxUserUtil;
import com.org.wx.utils.WxUtil;

/**
 * request from wx , type is "event"
 * @author Administrator
 *
 */
public class TypeEvent implements Business<String> {
	private Log log = LogFactory.getLog(TypeEvent.class);
	private JSONObject xmlJson;

	public TypeEvent(JSONObject xmlJson) {
		this.xmlJson = xmlJson;
	}

	@Override
	public String call() throws Exception {
		String FromUserName = xmlJson.getString("FromUserName");
		String Event = xmlJson.getString("Event");
		// ���¼����� �� ����İ�ťkeyֵ�ж� ���Ծ���ҵ������
		if(Event.equals("CLICK")) {
			log.info("CLICK �¼�����");
			String EventKey = xmlJson.getString("EventKey"); // ��Ӧ�Զ����key ֵ
			JSONObject returns = null;
			String content = "";
			WxUser wxUser = WxUserContainer.getInstance().getLocalUser(FromUserName);
			
			JSONObject res = null;
			if(WxUtil.ENTER_CHATING_ROOM.equals(EventKey)) {
				// ����������
				if(wxUser.getRoomId() != null) {
					// ����ڷ����У����˳�
					wxUser.exitChatingRoom();
				}
				res = wxUser.joininChatingRoom(Long.valueOf(RoomContainer.DEFAULT_ROOM_ID));
			} else if(WxUtil.EXIT_CHATING_ROOM.equals(EventKey)) {
				// �˳�������
				res = wxUser.exitChatingRoom();
			} else if(WxUtil.ENTER_STORE_ROOM.equals(EventKey)) {
				// �������ģʽ
				if(wxUser.getRoomId() != null) {
					// ����ڷ����У����˳�
					wxUser.exitChatingRoom();
				}
				res = wxUser.joininChatingRoom(Long.valueOf(RoomContainer.STORY_ROOM_ID));
			} else if(WxUtil.EXIT_STORE_ROOM.equals(EventKey)) {
				// �˳�����ģʽ
				res = wxUser.exitChatingRoom();
				
			}
			
			if(res != null) {

				// �ظ��ı���Ϣ
				if(res.getString(CommonConstant.RESP_CODE).equals("10000")) {
					content = res.getString(CommonConstant.RESP_MSG);
				} else {
					content = "����ʧ�ܣ�"+res.getString(CommonConstant.RESP_MSG) ;
				}
			} else {
				content = "�����������������Ա��ӳ�ɣ�";
			}
			
			returns = MessageUtil.sendToOne(content, FromUserName);
			if(returns != null && (returns.getInt("errcode")==0)) {
				log.info("��Ϣ���ͳɹ�");
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
			// {"ToUserName":"gh_b4c1774a1ef7","FromUserName":"osp6swrNZiWtEuTy-Gj1cBVA1l38","CreateTime":"1458029241","MsgType":"event","Event":"LOCATION","Latitude":"31.166275","Longitude":"121.389099","Precision":"30.000000"}
			// ����
			String Latitude = xmlJson.getString("Latitude");
			// γ��
			String Longitude = xmlJson.getString("Longitude");
			// ���� ����û��
			//String Precision = xmlJson.getString("Precision");
			
			StringBuffer temp = new StringBuffer();
			temp.append("���ĵ�ǰλ��:\n");
			temp.append("γ��:").append(Latitude);
			temp.append("\n");
			temp.append("����:").append(Longitude);
			JSONObject returns = MessageUtil.sendToOne(temp.toString(), FromUserName);
			if(returns != null && (returns.getInt("errcode")==0)) {
				log.info("��Ϣ���ͳɹ�");
			}
		}
		// 
		return WxConstant.RETURN_SUCCESS;
	}
	
	public JSONObject getXmlJson() {
		return xmlJson;
	}

	public void setXmlJson(JSONObject xmlJson) {
		this.xmlJson = xmlJson;
	}
	
}
