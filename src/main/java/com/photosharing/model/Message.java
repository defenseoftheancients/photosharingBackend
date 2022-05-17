package com.photosharing.model;

import com.photosharing.constant.EActivity;
import com.photosharing.dto.UserDTO;

public class Message {
	
	private UserDTO sender;
	private UserDTO receiver;
	private String content;
	private EActivity activitytype;
	private String objecturl;

	public Message() {
		
	}

	public UserDTO getSender() {
		return sender;
	}

	public void setSender(UserDTO sender) {
		this.sender = sender;
	}

	public UserDTO getReceiver() {
		return receiver;
	}

	public void setReceiver(UserDTO receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public EActivity getActivitytype() {
		return activitytype;
	}

	public void setActivitytype(EActivity activitytype) {
		this.activitytype = activitytype;
	}

	public String getObjecturl() {
		return objecturl;
	}

	public void setObjecturl(String objecturl) {
		this.objecturl = objecturl;
	}

	
}
