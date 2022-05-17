package com.photosharing.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class PhotoDTO {

	private Long id;

	private Boolean likedByUser;

	private Long totalLike = 0L;

	@JsonProperty("title")
	private String title;

	@JsonProperty("description")
	private String description;

	@JsonProperty("resolution")
	private String resolution;

	@JsonProperty("device")
	private String device;

	@JsonProperty("path")
	private String path;

	@JsonProperty("timepost")
	private Date timepost;

	@JsonIgnore
	private MultipartFile multipartFile;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String content;

	private List<TagDTO> tags = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		if (path != null && path.contains("http://localhost:8081/photosharing/api/photos/"))
			this.path = path;
		else
			this.path = "http://localhost:8081/photosharing/api/photos/" + path;
	}

	public List<TagDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}

	public Boolean getLikedByUser() {
		return likedByUser;
	}

	public void setLikedByUser(Boolean likedByUser) {
		this.likedByUser = likedByUser;
	}

	public Long getTotalLike() {
		return totalLike;
	}

	public void setTotalLike(Long totalLike) {
		this.totalLike = totalLike;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public Date getTimepost() {
		return timepost;
	}

	public void setTimepost(Date timepost) {
		this.timepost = timepost;
	}

	@Override
	public String toString() {
		return "PhotoDTO [id=" + id + ", likedByUser=" + likedByUser + ", totalLike=" + totalLike + ", title=" + title
				+ ", description=" + description + ", resolution=" + resolution + ", device=" + device + ", path="
				+ path + ", timepost=" + timepost + ", multipartFile=" + multipartFile + ", content=" + content
				+ ", tags=" + tags + "]";
	}

}
