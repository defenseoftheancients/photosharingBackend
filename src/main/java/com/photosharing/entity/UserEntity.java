package com.photosharing.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "avatar")
	private String avatar;

	@ManyToMany
	@JoinTable(name = "user_follower", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
	private List<UserEntity> followedUser = new ArrayList<>();

	@ManyToMany(mappedBy = "followedUser")
	private List<UserEntity> follower = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<PhotoEntity> ownedPhotos = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<CommentEntity> comments = new ArrayList<>();

	@OneToMany(mappedBy = "sender")
	private List<NotificationEntity> sendedNotifications = new ArrayList<>();

	@OneToMany(mappedBy = "receiver")
	private List<NotificationEntity> receivedNotifications = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "user_photo", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "photo_id"))
	private List<PhotoEntity> likedPhotos = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles = new HashSet<>();

	public UserEntity() {

	}

	public UserEntity(Long id, String username, String fullname, String email, String password, Set<RoleEntity> roles) {
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public List<NotificationEntity> getSendedNotifications() {
		return sendedNotifications;
	}

	public void setSendedNotifications(List<NotificationEntity> sendedNotifications) {
		this.sendedNotifications = sendedNotifications;
	}

	public List<NotificationEntity> getReceivedNotifications() {
		return receivedNotifications;
	}

	public void setReceivedNotifications(List<NotificationEntity> receivedNotifications) {
		this.receivedNotifications = receivedNotifications;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public List<UserEntity> getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(List<UserEntity> followedUser) {
		this.followedUser = followedUser;
	}

	public List<UserEntity> getFollower() {
		return follower;
	}

	public void setFollower(List<UserEntity> follower) {
		this.follower = follower;
	}

	public List<PhotoEntity> getOwnedPhotos() {
		return ownedPhotos;
	}

	public void setOwnedPhotos(List<PhotoEntity> ownedPhotos) {
		this.ownedPhotos = ownedPhotos;
	}

	public List<PhotoEntity> getLikedPhotos() {
		return likedPhotos;
	}

	public void setLikedPhotos(List<PhotoEntity> likedPhotos) {
		this.likedPhotos = likedPhotos;
	}

}
