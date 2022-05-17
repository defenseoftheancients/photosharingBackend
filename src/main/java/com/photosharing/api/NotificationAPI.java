package com.photosharing.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.photosharing.constant.EActivity;
import com.photosharing.dto.CommentNotificationDTO;
import com.photosharing.dto.FollowNotificationDTO;
import com.photosharing.dto.LikeNotificationDTO;
import com.photosharing.service.INotificationService;
import com.photosharing.util.NotificationUtil;

@CrossOrigin
@RestController
public class NotificationAPI {

	
	@Autowired
	private INotificationService notificationService;
	
	@GetMapping("/api/users/{userid}/notifications")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getNotifications(@PathVariable(required = false,value = "userid") Long userid) {
		List<Object> notifications =  notificationService.findAll(userid);
		return ResponseEntity.ok(notifications);
		
	}
	@PutMapping("/api/users/{userid}/notifications/{notificationid}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getNotifications(
			@PathVariable(required = true,value = "userid") Long userid,
			@PathVariable(required = true,value = "notificationid") Long notificationid,
			@RequestBody String jsonNotification) throws JsonMappingException, JsonProcessingException {
		if(jsonNotification.contains("LIKE_A_PHOTO")) {
			LikeNotificationDTO likeNotificationDTO =  (LikeNotificationDTO) NotificationUtil.convert(jsonNotification, EActivity.LIKE_A_PHOTO);
			likeNotificationDTO = (LikeNotificationDTO) notificationService.setSeen(likeNotificationDTO);
			return likeNotificationDTO == null ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : ResponseEntity.ok(likeNotificationDTO);
		}
		
		else if(jsonNotification.contains("COMMENT_A_PHOTO")) {
			CommentNotificationDTO commentNotificationDTO = (CommentNotificationDTO) NotificationUtil.convert(jsonNotification, EActivity.COMMENT_A_PHOTO);
			commentNotificationDTO = (CommentNotificationDTO) notificationService.setSeen(commentNotificationDTO);
			return commentNotificationDTO == null ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : ResponseEntity.ok(commentNotificationDTO);
		}
		else if(jsonNotification.contains("FOLLOW_A_USER")) {
			FollowNotificationDTO followNotificationDTO = (FollowNotificationDTO) NotificationUtil.convert(jsonNotification, EActivity.FOLLOW_A_USER);
			followNotificationDTO = (FollowNotificationDTO) notificationService.setSeen(followNotificationDTO);
			return followNotificationDTO == null ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : ResponseEntity.ok(followNotificationDTO);
		}
		return ResponseEntity.ok(null);
	}
}
