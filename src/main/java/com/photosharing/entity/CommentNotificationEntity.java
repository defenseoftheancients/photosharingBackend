package com.photosharing.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@DiscriminatorValue("COMMENT_A_PHOTO")
@Table(name = "commentnotification")
@OnDelete(action = OnDeleteAction.CASCADE)
public class CommentNotificationEntity extends NotificationEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "photo_id")
	private PhotoEntity photo;

	public PhotoEntity getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoEntity photo) {
		this.photo = photo;
	}
}

