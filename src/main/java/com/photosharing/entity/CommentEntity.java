package com.photosharing.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "comment")
public class CommentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "message")
	private String content;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "photo_id", nullable = false)
	private PhotoEntity photo;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name = "parent_id")
	private List<CommentEntity> replies;

	@CreationTimestamp
	@Column(name = "timesent", nullable = false, updatable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private Date timesent;

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

	public PhotoEntity getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoEntity photo) {
		this.photo = photo;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<CommentEntity> getReplies() {
		return replies;
	}

	public void setReplies(List<CommentEntity> replies) {
		this.replies = replies;
	}

	public Date getTimesent() {
		return timesent;
	}

	public void setTimesent(Date timesent) {
		this.timesent = timesent;
	}

}
