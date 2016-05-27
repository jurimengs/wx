package com.org.rute;

import net.sf.json.JSONObject;

/**
 * request from wx , type is "image"
 * @author Administrator
 *
 */
public class TypeImageRunnable implements Runnable {
	//private Log log = LogFactory.getLog(TypeText.class);
	private JSONObject xmlJson;

	public TypeImageRunnable(JSONObject xmlJson) {
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
		// TODO 
	}
	
}
