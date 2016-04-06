package com.org.model.wx;

import java.util.ArrayList;
import java.util.List;

public class Story{
	private String title;
	private boolean over;
	private List<UserContent> contentList;
	private List<UserContent> roleList;
	
	public void addContent(UserContent userContent){
		if(contentList == null) {
			contentList = new ArrayList<UserContent>();
		}
		contentList.add(userContent);
	}
	
	public boolean isOver() {
		return over;
	}
	public void over(boolean over) {
		this.over = over;
	}
	public List<UserContent> getContentList() {
		return contentList;
	}
	public void setContentList(List<UserContent> contentList) {
		this.contentList = contentList;
	}
}
