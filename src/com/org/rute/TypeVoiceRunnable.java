package com.org.rute;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * request from wx , type is "news"
 * @author Administrator
 *
 */
public class TypeVoiceRunnable implements Runnable {
	private Log log = LogFactory.getLog(this.getClass().getName());
	private JSONObject xmlJson;

	public TypeVoiceRunnable(JSONObject xmlJson) {
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
		// TODO Auto-generated method stub
		log.info("TypeVoiceRunnable.run()...");
	}
	
}
