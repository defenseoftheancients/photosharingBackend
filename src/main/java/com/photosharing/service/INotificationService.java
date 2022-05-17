package com.photosharing.service;

import java.util.List;

import com.photosharing.dto.CommentNotificationDTO;
import com.photosharing.dto.FollowNotificationDTO;
import com.photosharing.dto.LikeNotificationDTO;
import com.photosharing.dto.NotificationDTO;

public interface INotificationService {
	List<Object> findAll(Long userid);
	NotificationDTO save(LikeNotificationDTO notificationDTO);
	NotificationDTO save(CommentNotificationDTO notificationDTO);
	NotificationDTO save(FollowNotificationDTO notificationDTO);
	NotificationDTO setSeen(LikeNotificationDTO notificationDTO);
	NotificationDTO setSeen(CommentNotificationDTO notificationDTO);
	NotificationDTO setSeen(FollowNotificationDTO notificationDTO);
	void delete(LikeNotificationDTO notificationDTO);
	void delete(FollowNotificationDTO notificationDTO);
}
