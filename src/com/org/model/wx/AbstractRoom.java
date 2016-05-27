package com.org.model.wx;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRoom {
	private Long roomid;
	// 房间名称
	private String roomname;
	// 房间主题 
	private String roomtitle;
	// 模板id
	private Long templateid;
	// 是否故事模式
	private Boolean storymode;
	// 用户openid集合
	protected List<String> userList = new ArrayList<String>();
	// 角色list
	protected List<String> roleNameList = new ArrayList<String>();
	// 模板章节list
	protected List<String> nodeList = new ArrayList<String>();

	public void join(String openid){
		if(!userList.contains(openid)) {
			userList.add(openid);
			// 由于主要是从内存中读取信息进行处理，所以只更改内存中的用户房间状态，数据库状态更新，采用定时任务更新
		}
	}

	public void exit(String openid){
		if(!userList.contains(openid)) {
			userList.remove(openid);
		}
	}
	
	public List<String> getAllOpenid(){
		return userList;
	}

	public String getRandomRoleName() {
		synchronized (roleNameList) {
			if(roleNameList != null && roleNameList.size() > 0) {
				return roleNameList.remove(0);
			}
			return "游客";
		}
	}

	public abstract void sendToAll(WxUser wxUser, String content);

	public AbstractRoom() {
	}
	
	public AbstractRoom(Long roomid, String roomname, String roomtitle,
			Long templateid, Boolean storymode) {
		this.roomid = roomid;
		this.roomname = roomname;
		this.roomtitle = roomtitle;
		this.templateid = templateid;
		this.storymode = storymode;
	}

	public Long getRoomid() {
		return roomid;
	}

	public void setRoomid(Long roomid) {
		this.roomid = roomid;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getRoomtitle() {
		return roomtitle;
	}

	public void setRoomtitle(String roomtitle) {
		this.roomtitle = roomtitle;
	}

	public Long getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Long templateid) {
		this.templateid = templateid;
	}

	public Boolean isStorymode() {
		return storymode;
	}

	public void setStorymode(Boolean storymode) {
		this.storymode = storymode;
	}

	public List<String> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<String> nodeList) {
		this.nodeList = nodeList;
	}
	
	
}
