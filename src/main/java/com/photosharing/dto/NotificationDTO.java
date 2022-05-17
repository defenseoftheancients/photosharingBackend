package com.photosharing.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.photosharing.constant.EActivity;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class NotificationDTO {

	private Long id;

	private UserDTO sender;

	private UserDTO receiver;

	private String content;

	private EActivity activitytype;

	private String objecturl;

	private Date timesent;

	private Boolean isRead;

	public NotificationDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getTimesent() {
		return timesent;
	}

	public void setTimesent(Date timesent) {
		this.timesent = timesent;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "NotificationDTO [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", content=" + content
				+ ", activitytype=" + activitytype + ", objecturl=" + objecturl + ", timesent=" + timesent + ", isRead="
				+ isRead + "]";
	}

}
