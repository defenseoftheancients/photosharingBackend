package com.photosharing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagDTO {
	private Long id;
	
	@JsonProperty("tagname")
	private String tagname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	@Override
	public String toString() {
		return "TagDTO [id=" + id + ", tagname=" + tagname + "]";
	}

}
