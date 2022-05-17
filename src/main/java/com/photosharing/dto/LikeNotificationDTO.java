package com.photosharing.dto;

public class LikeNotificationDTO extends NotificationDTO {
	protected PhotoDTO likedPhoto;

	public PhotoDTO getLikedPhoto() {
		return likedPhoto;
	}

	public void setLikedPhoto(PhotoDTO likedPhoto) {
		this.likedPhoto = likedPhoto;
	}
	
	
}
