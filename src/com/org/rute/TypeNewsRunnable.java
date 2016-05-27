package com.org.rute;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * request from wx , type is "news"
 * @author Administrator
 *
 */
public class TypeNewsRunnable implements Runnable {
	private Log log = LogFactory.getLog(TypeNewsRunnable.class);
	private JSONObject xmlJson;

	public TypeNewsRunnable(JSONObject xmlJson) {
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
		log.info("TypeNewsRunnable.run()...");
	}
	
}
