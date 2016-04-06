package com.org.threads.business;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.org.queues.MessageSendTask;

public class SendMessageThread implements Runnable {
	private JSONObject paramContent;
	private List<String> openidList;
	
	public SendMessageThread(JSONObject paramContent, List<String> openidList) {
		this.paramContent = paramContent;
		this.openidList = openidList;
	}

	@Override
	public void run() {
		if(openidList != null && openidList.size() > 0) {
			for (int i = 0; i < openidList.size(); i++) {
				if(StringUtils.isNotEmpty(openidList.get(i))){
					new MessageSendTask(paramContent, openidList.get(i)).sendByService();
				}
			}
		}
	}
	
}
