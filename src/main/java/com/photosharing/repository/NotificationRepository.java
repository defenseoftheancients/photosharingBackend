package com.photosharing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.photosharing.entity.NotificationEntity;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>{
	
	List<Object> findAllByReceiverIdOrderByIdDesc(Long receiver_id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM notification WHERE sender_id = ?1 and activity_type = ?2", nativeQuery = true)
	void delete(Long senderId, String activitytype);
}
