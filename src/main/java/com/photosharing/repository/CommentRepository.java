package com.photosharing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photosharing.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	List<CommentEntity> findAllByPhotoIdOrderByIdDesc(Long photoId);
}
