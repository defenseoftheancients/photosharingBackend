package com.photosharing.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.photosharing.entity.PhotoEntity;
import com.photosharing.entity.UserEntity;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

	@Query(value = "SELECT photo.*, COUNT(user_photo.photo_id) AS totalLike FROM photo "
			+ "LEFT JOIN user_photo ON user_photo.photo_id = photo.id GROUP BY photo.id", countQuery = "select count(*) from photo", nativeQuery = true)
	Page<PhotoEntity> findAllByLike(Pageable pageable);
	
	@Query(value = "SELECT p FROM PhotoEntity p left join p.tags t WHERE "
		      + "t.tagname in :tags GROUP BY p.id")
	List<PhotoEntity> findAllByTags(@Param("tags") List<String> tags);
	
	Page<PhotoEntity> findAllByUserId(Pageable pageable, Long userId);

	@Query(value = "SELECT p FROM PhotoEntity p WHERE p.user in :followedUser")
	Page<PhotoEntity> findAllByFollowerId(Pageable pageable, @Param("followedUser") List<UserEntity> followedUser);
	
	@Query(value = "SELECT p.likedUsers FROM PhotoEntity p WHERE p.id = :photoid")
	List<UserEntity> findAllLikedUserById(@Param("photoid") Long photoid);
	
//	@Query(value = "SELECT u.likedPhotos FROM UserEntity u WHERE u.id = :userid")
	
	@Query(value = "SELECT * FROM photo WHERE id in (SELECT photo_id FROM user_photo WHERE user_id = :userid)", 
			nativeQuery = true)
	Page<PhotoEntity> findAllLikedPhotoByUserId(Pageable pageable, @Param("userid") Long userid);
}
