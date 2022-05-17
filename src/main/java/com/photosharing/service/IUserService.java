package com.photosharing.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.photosharing.dto.UserDTO;

public interface IUserService extends UserDetailsService {
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	UserDTO save(UserDTO userDTO);

	void likeAPhoto(Long userid, Long photoid);
	void unLikeAPhoto(Long userid, Long photoid);
	void followAUser(long userid, Long followerid);
	void unFollowAUser(long userid, Long followerid);

	UserDTO changeAvatar(UserDTO userDTO);
	List<Map<String, Object>> findAllFollowedUserById(Long id);
	List<Map<String, Object>> findAllFollowerById(Long id);
	UserDTO findById(Long id, String username);

}
