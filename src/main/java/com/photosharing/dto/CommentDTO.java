package com.photosharing.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentDTO {

	private Long id;

	@JsonProperty("content")
	private String content;

	private UserDTO author;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private PhotoDTO photo;

	private Date timesent;

	private List<CommentDTO> children;

	private Long parentId;

	public PhotoDTO getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoDTO photo) {
		this.photo = photo;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public Date getTimesent() {
		return timesent;
	}

	public void setTimesent(Date timesent) {
		this.timesent = timesent;
	}

	public List<CommentDTO> getChildren() {
		return children;
	}

	public void setChildren(List<CommentDTO> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "CommentDTO [id=" + id + ", content=" + content + ", author=" + author + ", photo=" + photo
				+ ", timesent=" + timesent + ", children=" + children + ", parentId=" + parentId + "]";
	}

}
