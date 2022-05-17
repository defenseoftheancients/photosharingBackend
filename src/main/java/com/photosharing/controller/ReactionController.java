package com.photosharing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.photosharing.constant.EActivity;
import com.photosharing.dto.CommentDTO;
import com.photosharing.dto.CommentNotificationDTO;
import com.photosharing.dto.FollowNotificationDTO;
import com.photosharing.dto.LikeNotificationDTO;
import com.photosharing.payload.response.CommentResponse;
import com.photosharing.payload.response.PhotoResponse;
import com.photosharing.service.ICommentService;
import com.photosharing.service.IPhotoService;
import com.photosharing.service.impl.NotificationService;
import com.photosharing.service.impl.UserService;
import com.photosharing.util.CommentUtil;
import com.photosharing.util.NotificationUtil;

@RestController
public class ReactionController {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired 
	private NotificationService notificationService;
	
	@Autowired 
	private IPhotoService photoService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private ICommentService commentService;
	
	
	@MessageMapping("/private-follow")
	public String receiveFollowPublicNotification(@Payload String reactionNotification) throws JsonMappingException, JsonProcessingException {
		FollowNotificationDTO notificationDTO = (FollowNotificationDTO) NotificationUtil.convert(reactionNotification, EActivity.FOLLOW_A_USER);
		if(notificationDTO.getActivitytype() == EActivity.FOLLOW_A_USER) {
			userService.followAUser(notificationDTO.getSender().getId(), notificationDTO.getReceiver().getId());
			notificationDTO = (FollowNotificationDTO) notificationService.save(notificationDTO);
			simpMessagingTemplate.convertAndSendToUser(notificationDTO.getReceiver().getUsername(), "/private", notificationDTO);
			simpMessagingTemplate.convertAndSendToUser(notificationDTO.getSender().getUsername(), "/private", notificationDTO);
		}
		else if(notificationDTO.getActivitytype() == EActivity.UNFOLLOW_A_USER) {
			notificationService.delete(notificationDTO);
			userService.unFollowAUser(notificationDTO.getSender().getId(), notificationDTO.getReceiver().getId());
			simpMessagingTemplate.convertAndSendToUser(notificationDTO.getSender().getUsername(), "/private", notificationDTO);
		}
		return "OK";
	}
	
	@MessageMapping("/private-comment")
	public String receiveCommentPublicNotification(@Payload String reactionNotification) throws JsonMappingException, JsonProcessingException {
		CommentNotificationDTO notificationDTO = (CommentNotificationDTO) NotificationUtil.convert(reactionNotification, EActivity.COMMENT_A_PHOTO);
		CommentDTO commentDTO = null;
		if(notificationDTO.getActivitytype() == EActivity.COMMENT_A_PHOTO) {
			commentDTO = CommentUtil._JSONtoObject(reactionNotification);
			commentDTO.setAuthor(notificationDTO.getSender());
			commentDTO.setPhoto(notificationDTO.getCommentedPhoto());
			commentDTO = commentService.save(commentDTO);
			if(notificationDTO.getSender().getId() != notificationDTO.getReceiver().getId()) {
				notificationDTO = (CommentNotificationDTO) notificationService.save(notificationDTO);
				simpMessagingTemplate.convertAndSendToUser(notificationDTO.getReceiver().getUsername(), "/private", notificationDTO);
			}
		}
		simpMessagingTemplate.convertAndSend("/photo/" + notificationDTO.getCommentedPhoto().getId(), new CommentResponse(commentDTO, EActivity.COMMENT_A_PHOTO));
		return "OK";
	}
	
	@MessageMapping("/private-like")
	public String receiveLikePrivateNotification(@Payload String reactionNotification) {
		System.out.println(reactionNotification);
		LikeNotificationDTO notificationDTO = (LikeNotificationDTO) NotificationUtil.convert(reactionNotification,EActivity.LIKE_A_PHOTO);
		PhotoResponse photoResponse = null;
		if(notificationDTO.getActivitytype() == EActivity.LIKE_A_PHOTO) {
			userService.likeAPhoto(notificationDTO.getSender().getId(), notificationDTO.getLikedPhoto().getId());
			if(notificationDTO.getSender().getId() != notificationDTO.getReceiver().getId()) {
				notificationDTO = (LikeNotificationDTO) notificationService.save(notificationDTO);
				simpMessagingTemplate.convertAndSendToUser(notificationDTO.getReceiver().getUsername(), "/private", notificationDTO);
				simpMessagingTemplate.convertAndSendToUser(notificationDTO.getSender().getUsername(), "/private", notificationDTO);
			}
			else
				simpMessagingTemplate.convertAndSendToUser(notificationDTO.getReceiver().getUsername(), "/private", notificationDTO);
			
		}
		else if(notificationDTO.getActivitytype() == EActivity.UNLIKE_A_PHOTO) {
			notificationService.delete(notificationDTO);
			userService.unLikeAPhoto(notificationDTO.getSender().getId(), notificationDTO.getLikedPhoto().getId());
			photoResponse = photoService.findById(notificationDTO.getLikedPhoto().getId(), notificationDTO.getSender().getUsername());
			simpMessagingTemplate.convertAndSendToUser(notificationDTO.getSender().getUsername(), "/private", notificationDTO);
			
		}
		photoResponse = photoService.findById(notificationDTO.getLikedPhoto().getId(), notificationDTO.getSender().getUsername());
		photoResponse.setSender(notificationDTO.getSender().getUsername());
		photoResponse.setActivitytype(EActivity.LIKE_A_PHOTO);
		simpMessagingTemplate.convertAndSend("/photo/" + notificationDTO.getLikedPhoto().getId(), photoResponse);
		return "OK";
	}
}
