package com.org.rute;

import net.sf.json.JSONObject;

import com.org.interfaces.rute.Business;

/**
 * request from wx , type is "image"
 * @author Administrator
 *
 */
public class TypeImage implements Business<String> {
	//private Log log = LogFactory.getLog(TypeText.class);
	private JSONObject xmlJson;

	public TypeImage(JSONObject xmlJson) {
		this.xmlJson = xmlJson;
	}

	@Override
	public String call() {
		/*// ����Ϣ����
		String msgFromOpenid = xmlJson.getString("FromUserName");
		// ��ϢҪ�����Ķ����б�
		JSONArray chatingUserArray = WxUserContainer.getChatingUser();
		// ����Ϣ�ߵ��ǳ�
		Map<String, Boolean> chatingUsersMap = WxUserContainer.getChatingOpenidsMap();
		// �ж����Ƿ���������
		String returnStr = "";
		if(chatingUsersMap.containsKey(msgFromOpenid) && chatingUsersMap.get(msgFromOpenid)) {
			returnStr = WxConstant.RETURN_SUCCESS;
			// �����г�ȥ����Ϣ���Լ�
			chatingUserArray.remove(msgFromOpenid);
			// ����Ϣ�ߵ��ǳ�
			// TODO ͼ����Ϣ��û�취������nick��
			String nick = "test";
			
			String mediaId = nick + ":\n"+xmlJson.getString("MediaId");
			JSONObject paramContent = MessageUtil.getImageMessageJson(mediaId);
			
			MessageUtil.pushMassMessage(chatingUserArray, paramContent, 0);
		} else {
			returnStr = WxUtil.autoReply(xmlJson);
		}
		return returnStr;*/

		// TODO 
		return null;
	}

	public JSONObject getXmlJson() {
		return xmlJson;
	}

	public void setXmlJson(JSONObject xmlJson) {
		this.xmlJson = xmlJson;
	}
	
}
