package com.org.model.wx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.services.busi.StoryService;
import com.org.utils.BeanUtils;
import com.org.wx.utils.MessageUtil;

public class CommonRoom extends AbstractRoom{
	private Log log = LogFactory.getLog(CommonRoom.class);

	public CommonRoom(Long roomid, String roomname, String roomtitle,
			Long templateid, Boolean storymode) {
		super(roomid, roomname, roomtitle, templateid, storymode);
	}
	
	public CommonRoom() {
		super();
	}

	@Override
	public void sendToAll(WxUser wxUser, String content) {
	}
}
