package com.photosharing.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photosharing.constant.ERole;
import com.photosharing.dto.UserDTO;
import com.photosharing.entity.RoleEntity;
import com.photosharing.entity.UserEntity;
import com.photosharing.repository.RoleRepository;
import com.photosharing.repository.UserRepository;
import com.photosharing.service.IUserService;
import com.photosharing.util.UserUtil;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserUtil userUtil;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		;
		return UserUtil._EntitytoDTO(userEntity);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	@Transactional
	public UserDTO save(UserDTO userDTO) {
		RoleEntity roleEntity = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		UserEntity userEntity = UserUtil._DTOtoEntity(userDTO);
		userEntity.getRoles().add(roleEntity);
		return UserUtil._EntitytoDTO(userRepository.save(userEntity));
	}

	@Override
	public UserDTO findById(Long id, String username) {
		UserEntity userEntity = userRepository.findById(id).orElse(null);
		if (userEntity == null)
			return null;
		UserDTO userDTO = UserUtil._EntitytoDTO(userEntity);
		
		if(UserUtil.isUserFollowed(userEntity.getFollower(), username))
			userDTO.setIsFollowing(true);
		else
			userDTO.setIsFollowing(false);
	
		
		return userDTO;
	}

	@Override
	public List<Map<String, Object>> findAllFollowedUserById(Long id) {
		return UserUtil.convert(userRepository.findAllFollowedUserById(id));
	}

	@Override
	public List<Map<String, Object>> findAllFollowerById(Long id) {
		return UserUtil.convert(userRepository.findAllFollowerById(id));
	}

	@Override
	@Transactional
	public void likeAPhoto(Long userid, Long photoid) {
		userRepository.likeAPhoto(userid, photoid);
	}

	@Override
	@Transactional
	public void unLikeAPhoto(Long userid, Long photoid) {
		userRepository.unLikeAPhoto(userid, photoid);
	}

	@Override
	@Transactional
	public void followAUser(long userid, Long followerid) {
		userRepository.followAUser(userid, followerid);
	}

	@Override
	@Transactional
	public void unFollowAUser(long userid, Long followerid) {
		userRepository.unFollowAUser(userid, followerid);
	}

	@Override
	public UserDTO changeAvatar(UserDTO userDTO) {
		try {
			UserEntity userEntity = userUtil.changeAvatar(userDTO);
			userEntity = userRepository.save(userEntity);
			return UserUtil._EntitytoDTO(userEntity);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
