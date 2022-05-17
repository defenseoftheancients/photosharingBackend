package com.photosharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.photosharing.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO user_photo VALUES(?1, ?2)", nativeQuery = true)
	void likeAPhoto(Long userid, Long photoid);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM user_photo WHERE user_id = ?1 and photo_id = ?2", nativeQuery = true)
	void unLikeAPhoto(Long userid, Long photoid);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO user_follower VALUES(?1, ?2)", nativeQuery = true)
	void followAUser(Long userid, Long followerid);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM user_follower WHERE user_id = ?1 and follower_id = ?2", nativeQuery = true)
	void unFollowAUser(Long userid, Long followerid);
	
	@Query(value = "SELECT id, fullname, avatar FROM user "
			+ "WHERE id in(SELECT follower_id From user_follower WHERE user_follower.user_id = ?1 )", nativeQuery = true)
	List<Object[]> findAllFollowedUserById(Long id);
	
	@Query(value = "SELECT id, fullname, avatar FROM user "
			+ "WHERE id in(SELECT user_id From user_follower WHERE user_follower.follower_id = ?1 )", nativeQuery = true)
	List<Object[]> findAllFollowerById(Long id);
	
	@Query(value = "SELECT COUNT(*) FROM user_follower u WHERE u.user_id=?1", nativeQuery = true)
	long countFollowedUserById(Long id);
	
	@Query(value = "SELECT COUNT(*) FROM user_follower u WHERE u.follower_id=?1", nativeQuery = true)
	long countFollowerById(Long id);
}
