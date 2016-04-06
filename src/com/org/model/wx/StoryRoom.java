package com.org.model.wx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.services.busi.StoryService;
import com.org.utils.BeanUtils;
import com.org.wx.utils.MessageUtil;

public class StoryRoom extends AbstractRoom{
	private Log log = LogFactory.getLog(StoryRoom.class);
	// 
	private Story story = new Story();

	@Override
	public void sendToAll(WxUser wxUser, String content) {
		log.info("StoryRoom === > sendToAll");
		String rolename = wxUser.getStoryNickName();
		// TODO 故事模式，要将用户发送的信息保存下来，
		if(!story.isOver()) {
			StoryService storyService = (StoryService)BeanUtils.getBean("storyService");
			storyService.saveStory(getTemplateid(), rolename, content, "");
		}
		// 这里的名字要用故事名
		MessageUtil.sendToMultiByQueue(rolename + ":\n" + content, userList);
	}
	
	public void overStory(){
		this.story.over(true);
	}

	public StoryRoom(Long roomid, String roomname, String roomtitle,
			Long templateid, Boolean storymode) {
		super(roomid, roomname, roomtitle, templateid, storymode);
	}
	
	public StoryRoom() {
		super();
	}
}
