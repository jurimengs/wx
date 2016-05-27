package com.org.rute;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.interfaces.rute.Business;

/**
 * 路由适配器，决定当前请求由谁来处理
 * @author Administrator
 *
 */
public class RuteAdapter {
	private static Log log = LogFactory.getLog(RuteAdapter.class);
	
	/**
	 * 多线程同步处理
	 * @param xmlJson
	 * @return
	 */
	public static Business<String> adapter(JSONObject xmlJson){
		if(xmlJson == null) {
			throw new NullPointerException("EventAdapter can not deal an null param request");
		}
		Business<String> e = null;
		String msgType = xmlJson.getString("MsgType");
		log.info("请求消息类型====>"+msgType);
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

	/**
	 * 多线程异步处理
	 * @param xmlJson
	 * @return
	 */
	public static Runnable adapterRunnable(JSONObject xmlJson){
		if(xmlJson == null) {
			throw new NullPointerException("EventAdapter can not deal an null param request");
		}
		Runnable e = null;
		String msgType = xmlJson.getString("MsgType");
		log.info("请求消息类型====>"+msgType);
		if(msgType.equals("event")) {
			e = new TypeEventRunnable(xmlJson);
		} else if(msgType.equals("text")) {
			e = new TypeTextRunnable(xmlJson);
		} else if(msgType.equals("image")) {
			e = new TypeImageRunnable(xmlJson);
		} else if(msgType.equals("news")) {
			e = new TypeNewsRunnable(xmlJson);
		} else if(msgType.equals("voice")) {
			e = new TypeVoiceRunnable(xmlJson);
		} else {
			throw new NullPointerException("unknown event type : " + msgType);
		}
		return e;
	}
}
