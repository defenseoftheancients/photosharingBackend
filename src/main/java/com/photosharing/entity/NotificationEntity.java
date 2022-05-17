package com.photosharing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.photosharing.constant.EActivity;

@Entity(name="notification")
@Inheritance(strategy  = InheritanceType.JOINED)
@DiscriminatorColumn(name = "activity_type",
discriminatorType = DiscriminatorType.STRING)
public class NotificationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private UserEntity sender;
	
	@ManyToOne
	@JoinColumn(name = "receiver_id", nullable = false)
	private UserEntity receiver;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "objecturl")
	private String objecturl;
	
	@CreationTimestamp
	@Column(name="timesent", nullable = false, updatable = false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private Date timesent;
	
	@Column(name = "isread", columnDefinition="tinyint(1) default 0")
	private Boolean isRead;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getSender() {
		return sender;
	}

	public void setSender(UserEntity sender) {
		this.sender = sender;
	}

	public UserEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(UserEntity receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Transient
	public EActivity getDiscriminatorValue(){
	    DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );
	    return val == null ? null : EActivity.valueOf(val.value());
	}
	
}
