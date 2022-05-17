package com.photosharing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photosharing.dto.CommentDTO;
import com.photosharing.entity.CommentEntity;
import com.photosharing.repository.CommentRepository;
import com.photosharing.service.ICommentService;
import com.photosharing.util.CommentUtil;

@Service
public class CommentService implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public CommentDTO save(CommentDTO commentDTO) {
		return CommentUtil._EntitytoDTO(commentRepository.save(CommentUtil._DTOtoEntity(commentDTO)));
	}

	@Override
	public List<CommentDTO> findAllByPhotoIdOrderByIdDesc(Long photoId) {
		List<CommentEntity> commentEntities = commentRepository.findAllByPhotoIdOrderByIdDesc(photoId);
		List<CommentDTO> commentDTOs = new ArrayList<>();
		if(!commentEntities.isEmpty()) {
			commentEntities.forEach(commentEntity-> {
				commentDTOs.add(CommentUtil._EntitytoDTO(commentEntity));
			});
		}
		return commentDTOs;
	}
	
	
	
}
