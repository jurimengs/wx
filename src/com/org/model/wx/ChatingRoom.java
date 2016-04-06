package com.org.model.wx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.wx.utils.MessageUtil;

public class ChatingRoom extends AbstractRoom {

	private Log log = LogFactory.getLog(ChatingRoom.class);

	@Override
	public void sendToAll(WxUser wxUser, String content) {
		log.info("ChatingRoom === > sendToAll");
		String nick = wxUser.getNickname();
		//MessageUtil.sendToMultiByQueue(nick + ":\n" + content, userList);
		// 队列的消耗有点大，改用线程池
		MessageUtil.sendToMultiByThreadPool(nick + ":\n" + content, userList);
	}
	
	public ChatingRoom(Long roomid, String roomname, String roomtitle,
			Long templateid, Boolean storymode) {
		super(roomid, roomname, roomtitle, templateid, storymode);
	}
	
	public ChatingRoom() {
		super();
	}
}
