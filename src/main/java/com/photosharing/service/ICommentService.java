package com.photosharing.service;

import java.util.List;

import com.photosharing.dto.CommentDTO;

public interface ICommentService {
	CommentDTO save(CommentDTO commentDTO);
	List<CommentDTO> findAllByPhotoIdOrderByIdDesc(Long photoId);
}
