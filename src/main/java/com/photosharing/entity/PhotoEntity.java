package com.photosharing.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "photo")
@Transactional
public class PhotoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "resolution")
	private String resolution;

	@Column(name = "device")
	private String device;

	@Column(name = "path")
	private String path;

	@CreationTimestamp
	@Column(name = "timepost", nullable = false, updatable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private Date timepost;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@ManyToMany(mappedBy = "likedPhotos")
	private List<UserEntity> likedUsers = new ArrayList<>();

	@OneToMany(mappedBy = "photo")
	private List<CommentNotificationEntity> commentNotifications = new ArrayList<>();

	@OneToMany(mappedBy = "photo")
	private List<LikeNotificationEntity> likeNotifications = new ArrayList<>();

	@OneToMany(mappedBy = "photo")
	private List<TagEntity> tags = new ArrayList<>();

	@OneToMany(mappedBy = "photo")
	private List<CommentEntity> comments = new ArrayList<>();

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<TagEntity> getTags() {
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<UserEntity> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(List<UserEntity> likedUsers) {
		this.likedUsers = likedUsers;
	}

	public Date getTimepost() {
		return timepost;
	}

	public void setTimepost(Date timepost) {
		this.timepost = timepost;
	}

	public List<CommentNotificationEntity> getCommentNotifications() {
		return commentNotifications;
	}

	public void setCommentNotifications(List<CommentNotificationEntity> commentNotifications) {
		this.commentNotifications = commentNotifications;
	}

	public List<LikeNotificationEntity> getLikeNotifications() {
		return likeNotifications;
	}

	public void setLikeNotifications(List<LikeNotificationEntity> likeNotifications) {
		this.likeNotifications = likeNotifications;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

}
