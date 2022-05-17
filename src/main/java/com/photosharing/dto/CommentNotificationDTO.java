package com.photosharing.dto;

public class CommentNotificationDTO extends NotificationDTO {
	private PhotoDTO commentedPhoto;

	public PhotoDTO getCommentedPhoto() {
		return commentedPhoto;
	}

	public void setCommentedPhoto(PhotoDTO commentedPhoto) {
		this.commentedPhoto = commentedPhoto;
	}
}
