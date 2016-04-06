package com.org.rute;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.interfaces.rute.Business;

/**
 * ·����������������ǰ������˭������
 * @author Administrator
 *
 */
public class RuteAdapter {
	private static Log log = LogFactory.getLog(RuteAdapter.class);
	
	public static Business<String> adapter(JSONObject xmlJson){
		if(xmlJson == null) {
			throw new NullPointerException("EventAdapter can not deal an null param request");
		}
		Business<String> e = null;
		String msgType = xmlJson.getString("MsgType");
		log.info("������Ϣ����====>"+msgType);
		if(msgType.equals("event")) {
			e = new TypeEvent(xmlJson);
		} else if(msgType.equals("text")) {
			e = new TypeText(xmlJson);
		} else if(msgType.equals("image")) {
			e = new TypeImage(xmlJson);
		} else if(msgType.equals("news")) {
			e = new TypeNews(xmlJson);
		} else if(msgType.equals("voice")) {
			e = new TypeVoice(xmlJson);
		} else {
			throw new NullPointerException("unknown event type : " + msgType);
		}
		return e;
	}

}
