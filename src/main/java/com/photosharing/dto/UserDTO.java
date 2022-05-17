package com.photosharing.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("user")
public class UserDTO implements UserDetails {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id;

	@NotBlank
	@JsonProperty("username")
	private String username;

	@JsonProperty("fullname")
	private String fullname;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank
	private String password;

	@JsonProperty("email")
	private String email;

	@JsonProperty("avatar")
	private String avatar;

	private Boolean isFollowing;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private MultipartFile multipartFile;

	@JsonIgnore
	private Set<RoleDTO> roles = new HashSet<>();

	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorities;

	private List<Map<String, Object>> followedUser = new ArrayList<>();

	private List<Map<String, Object>> follower = new ArrayList<>();

	public UserDTO() {

	}

	public UserDTO(String username, String fullname, String email, String password) {
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		if (avatar != null && avatar.contains("http://localhost:8081/photosharing/api/photos/"))
			this.avatar = avatar;
		else
			this.avatar = "http://localhost:8081/photosharing/api/photos/" + avatar;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsFollowing() {
		return isFollowing;
	}

	public void setIsFollowing(Boolean isFollowing) {
		this.isFollowing = isFollowing;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<Map<String, Object>> getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(List<Map<String, Object>> followedUser) {
		this.followedUser = followedUser;
	}

	public List<Map<String, Object>> getFollower() {
		return follower;
	}

	public void setFollower(List<Map<String, Object>> follower) {
		this.follower = follower;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", fullname=" + fullname + ", password=" + password
				+ ", email=" + email + ", roles=" + roles + ", authorities=" + authorities + ", followedUser="
				+ followedUser + ", follower=" + follower + "]";
	}

}
