package com.photosharing.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.photosharing.dto.PhotoDTO;
import com.photosharing.dto.UserDTO;
import com.photosharing.entity.PhotoEntity;
import com.photosharing.entity.TagEntity;
import com.photosharing.entity.UserEntity;
import com.photosharing.payload.response.PhotoResponse;
import com.photosharing.repository.PhotoRepository;
import com.photosharing.repository.TagRepository;
import com.photosharing.repository.UserRepository;
import com.photosharing.service.IPhotoService;
import com.photosharing.util.PhotoUtil;
import com.photosharing.util.UserUtil;

@Service
public class PhotoService implements IPhotoService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhotoRepository photoRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private PhotoUtil photoUtil;

	@Override
	@Transactional
	public void save(UserDTO userDTO, List<PhotoDTO> photoDTOs) {
		try {
			List<PhotoEntity> photoEntities = photoUtil.save(userDTO, photoDTOs);
			photoRepository.saveAll(photoEntities);
			photoEntities.forEach(photoEntity -> {
				List<TagEntity> tagEntities = photoEntity.getTags();
				tagEntities.forEach(tagEntity -> {
					tagEntity.setPhoto(photoEntity);
				});
				tagRepository.saveAll(tagEntities);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
	public PhotoResponse findById(Long photoId, String username) {
		List<PhotoEntity> photoEntities = new ArrayList<>();
		PhotoEntity photoEntity = photoRepository.findById(photoId).orElse(null);
		if (photoEntity != null) {
			photoEntities.add(photoEntity);
			return PhotoUtil.setLikeNFollow(photoEntities, username).get(0);
		}
		return null;
	}

	@Override
	public List<PhotoResponse> findAll(Pageable pageable, String username) {
		List<PhotoEntity> photoEntities = photoRepository.findAll(pageable).getContent();
		return PhotoUtil.setLikeNFollow(photoEntities, username);
	}

	@Override
	public List<PhotoResponse> findAllByLike(Pageable pageable, String username) {
		List<PhotoEntity> photoEntities = photoRepository.findAllByLike(pageable).getContent();
		return PhotoUtil.setLikeNFollow(photoEntities, username);
	}

	@Override
	public List<PhotoResponse> findAllByUserId(Pageable pageable, Long userid, String username) {
		List<PhotoEntity> photoEntities = photoRepository.findAllByUserId(pageable, userid).getContent();
		return PhotoUtil.setLikeNFollow(photoEntities, username);
	}

	@Override
	public List<PhotoResponse> findAllByTags(Pageable pageable, String username, List<String> tags) {
		List<PhotoEntity> photoEntities = photoRepository.findAllByTags(tags);
		return PhotoUtil.setLikeNFollow(photoEntities, username);
	}

	@Override
	public List<PhotoResponse> findAllByFollowedUser(Pageable pageable, Long userid) {
		UserEntity userEntity = userRepository.findById(userid).orElse(null);
		List<PhotoEntity> photoEntities = photoRepository.findAllByFollowerId(pageable, userEntity.getFollowedUser())
				.getContent();
		return PhotoUtil.setLikeNFollow(photoEntities, userEntity.getUsername());
	}

	@Override
	public List<UserDTO> findAllLikedUsersById(Long photoid) {
		List<UserEntity> userEntities = photoRepository.findAllLikedUserById(photoid);
		List<UserDTO> userDTOs = new ArrayList<>();
		userEntities.forEach(userEntity -> {
			userDTOs.add(UserUtil._EntitytoDTO(userEntity));
		});
		return userDTOs;
	}

	@Override
	public List<PhotoResponse> findAllLikedPhotoByUserId(Pageable pageable, Long userid) {
		UserEntity userEntity = userRepository.findById(userid).orElse(null);
		if(userEntity != null) {
			List<PhotoEntity> photoEntities = photoRepository.findAllLikedPhotoByUserId(pageable, userEntity.getId()).getContent();
			return PhotoUtil.setLikeNFollow(photoEntities, userEntity.getUsername());
		}
		return null;
	}

}
