package com.photosharing.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.photosharing.dto.PhotoDTO;
import com.photosharing.dto.UserDTO;
import com.photosharing.payload.response.PhotoResponse;

public interface IPhotoService {
	void save(UserDTO userDTO, List<PhotoDTO> photoDTO);
	PhotoResponse findById(Long photoId, String username);
	List<PhotoResponse> findAll(Pageable pageable, String username);
	List<PhotoResponse> findAllByLike(Pageable pageable, String username);
	List<PhotoResponse> findAllByTags(Pageable pageable, String username, List<String> tags);
	List<PhotoResponse> findAllByUserId(Pageable pageable, Long userid, String username);
	List<PhotoResponse> findAllByFollowedUser(Pageable pageable, Long userid);
	List<PhotoResponse> findAllLikedPhotoByUserId(Pageable pageable, Long userid);
	List<UserDTO> findAllLikedUsersById(Long photoid);
}
