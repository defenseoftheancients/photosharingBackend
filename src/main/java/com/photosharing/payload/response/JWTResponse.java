package com.photosharing.payload.response;

import java.util.List;

import com.photosharing.dto.UserDTO;

public class JWTResponse {
	private String token;
	private String type = "Bearer";
	private UserDTO userDTO;
	private List<String> roles;

	public JWTResponse(String accesstoken, UserDTO userDTO, List<String> roles) {
		this.token = accesstoken;
		this.userDTO = userDTO;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

}
