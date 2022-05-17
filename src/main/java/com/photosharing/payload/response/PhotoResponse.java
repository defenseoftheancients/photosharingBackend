package com.photosharing.payload.response;

import com.photosharing.constant.EActivity;
import com.photosharing.dto.PhotoDTO;
import com.photosharing.dto.UserDTO;

public class PhotoResponse {
	private PhotoDTO photoDTO;
	private UserDTO userDTO;
	private String sender;
	private EActivity activitytype;
	
	public PhotoResponse(PhotoDTO photoDTO, UserDTO userDTO) {
		
		this.photoDTO = photoDTO;
		this.userDTO = userDTO;
	}
	@Override
	public String toString() {
		return "[photoDTO=" + photoDTO + "\n userDTO=" + userDTO + "]";
	}
	public PhotoDTO getPhotoDTO() {
		return photoDTO;
	}
	public void setPhotoDTO(PhotoDTO photoDTO) {
		this.photoDTO = photoDTO;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public EActivity getActivitytype() {
		return activitytype;
	}
	public void setActivitytype(EActivity activitytype) {
		this.activitytype = activitytype;
	}
	
}
