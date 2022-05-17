package com.photosharing.payload.response;

import com.photosharing.constant.EActivity;
import com.photosharing.dto.CommentDTO;

public class CommentResponse {
	private CommentDTO commentDTO;

	private EActivity activitytype;

	public CommentResponse(CommentDTO commentDTO, EActivity activitytype) {

		this.commentDTO = commentDTO;
		this.activitytype = activitytype;
	}

	public CommentDTO getCommentDTO() {
		return commentDTO;
	}

	public void setCommentDTO(CommentDTO commentDTO) {
		this.commentDTO = commentDTO;
	}

	public EActivity getActivitytype() {
		return activitytype;
	}

	public void setActivitytype(EActivity activitytype) {
		this.activitytype = activitytype;
	}

	@Override
	public String toString() {
		return "CommentResponse [commentDTO=" + commentDTO + ", activitytype=" + activitytype + "]";
	}
	
}
