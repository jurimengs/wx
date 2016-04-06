package com.org.model.wx;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.caches.RoomContainer;
import com.org.common.CommonConstant;

public class WxUser {
	private static Log log = LogFactory.getLog(WxUser.class);
	private String openid;
	private String nickname;
	private Long roomId;
	private String sex;
	private String subscribeTime;
	private String subscribe;
	private String headimgurl;
	private String country;
	private String province;
	private String city;
	private String password;
	// ����ģʽ������
	private String storyNickName;

	/**
	 * 
	 * @param roomId
	 * @return 
	 */
	public JSONObject joininChatingRoom(Long roomId){
		this.roomId = roomId;
		JSONObject res = new JSONObject();
		// ����roomidѰ��room
		AbstractRoom cr = RoomContainer.getInstance().getRoomById(roomId);
		if(cr != null) {
			log.info("joininChatingRoom " + roomId);
			
			// ����֮ǰҪ�������û��Ĺ��½�ɫ��
			this.storyNickName = cr.getRandomRoleName();
			
			cr.join(openid);
			res.put(CommonConstant.RESP_CODE, "10000");
			res.put(CommonConstant.RESP_MSG, "����ɹ�");
		} else {
			res.put(CommonConstant.RESP_CODE, "error");
			res.put(CommonConstant.RESP_MSG, "���䲻����");
		}
		return res;
	}
	
	/**
	 * 
	 * @param roomId
	 * @return 
	 */
	public JSONObject exitChatingRoom(){
		JSONObject res = new JSONObject();
		// ����roomidѰ��room
		AbstractRoom cr = RoomContainer.getInstance().getRoomById(roomId);
		if(cr != null) {
			if(StringUtils.isNotEmpty(openid)) {
				this.roomId = null;
				cr.exit(openid);
				res.put(CommonConstant.RESP_CODE, "10000");
				res.put(CommonConstant.RESP_MSG, "�˳��ɹ�");
			} else {
				res.put(CommonConstant.RESP_CODE, "error");
				res.put(CommonConstant.RESP_MSG, "�û�δ��ʼ����openidΪ��");
			}
		} else {
			res.put(CommonConstant.RESP_CODE, "error");
			res.put(CommonConstant.RESP_MSG, "�û�δ�ڷ�����");
		}
		return res;
	}

	public WxUser(){
		/*this.openid = openid;
		// ��ȡ�û���Ϣ������ʼ��
		JSONObject localUser = WxUserContainer.getInstance().getLocalUser(openid);
		if(localUser != null) {
			this.nickname = localUser.getString("nickname");
			this.sex = localUser.getString("sex");
			this.subscribeTime = localUser.getString("subscribeTime");
			this.subscribe = localUser.getString("subscribe");
			this.headimgurl = localUser.getString("headimgurl");
			this.country = localUser.getString("country");
			this.province = localUser.getString("province");
			this.city = localUser.getString("city");
			this.password = localUser.containsKey("password") ? localUser.getString("password") : "";
		}*/
	}
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStoryNickName() {
		return storyNickName;
	}

	public void setStoryName(String storyNickName) {
		this.storyNickName = storyNickName;
	}
	
}
