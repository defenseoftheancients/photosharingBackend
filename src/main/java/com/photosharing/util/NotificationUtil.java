package com.photosharing.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosharing.constant.EActivity;
import com.photosharing.dto.CommentNotificationDTO;
import com.photosharing.dto.FollowNotificationDTO;
import com.photosharing.dto.LikeNotificationDTO;
import com.photosharing.dto.NotificationDTO;
import com.photosharing.dto.PhotoDTO;
import com.photosharing.dto.UserDTO;
import com.photosharing.entity.CommentNotificationEntity;
import com.photosharing.entity.FollowNotificationEntity;
import com.photosharing.entity.LikeNotificationEntity;
import com.photosharing.entity.NotificationEntity;

@Component
public class NotificationUtil {

	private static void DTOtoEntity(NotificationEntity notificationEntity, NotificationDTO notificationDTO) {
		notificationEntity.setId(notificationDTO.getId());
		notificationEntity.setSender(UserUtil._DTOtoEntity(notificationDTO.getSender()));
		notificationEntity.setReceiver(UserUtil._DTOtoEntity(notificationDTO.getReceiver()));
		notificationEntity.setContent(notificationDTO.getContent());
		notificationEntity.setObjecturl(notificationDTO.getObjecturl());
		notificationEntity.setIsRead(notificationDTO.getIsRead() == null ? false : notificationDTO.getIsRead());
		notificationEntity.setTimesent(notificationDTO.getTimesent() == null ? null : notificationDTO.getTimesent());
	}

	private static void EntityToDTO(NotificationEntity notificationEntity, NotificationDTO notificationDTO) {
		notificationDTO.setId(notificationEntity.getId());
		notificationDTO.setSender(UserUtil._EntitytoDTO(notificationEntity.getSender()));
		notificationDTO.setReceiver(UserUtil._EntitytoDTO(notificationEntity.getReceiver()));
		notificationDTO.setContent(notificationEntity.getContent());
		notificationDTO.setObjecturl(notificationEntity.getObjecturl());
		notificationDTO.setIsRead(notificationEntity.getIsRead());
		notificationDTO.setTimesent(notificationEntity.getTimesent());
		notificationDTO.setActivitytype(notificationEntity.getDiscriminatorValue());
	}

	public static LikeNotificationEntity _DTOtoEntity(LikeNotificationDTO notificationDTO) {
		LikeNotificationEntity notificationEntity = new LikeNotificationEntity();
		DTOtoEntity(notificationEntity, notificationDTO);
		notificationEntity.setPhoto(PhotoUtil._DTOtoEntity(notificationDTO.getLikedPhoto()));
		return notificationEntity;

	}

	public static LikeNotificationDTO _EntitytoDTO(LikeNotificationEntity notificationEntity) {
		LikeNotificationDTO notificationDTO = new LikeNotificationDTO();
		EntityToDTO(notificationEntity, notificationDTO);
		notificationDTO.setLikedPhoto(PhotoUtil._EntitytoDTO(notificationEntity.getPhoto()));
		return notificationDTO;
	}

	public static CommentNotificationEntity _DTOtoEntity(CommentNotificationDTO notificationDTO) {
		CommentNotificationEntity notificationEntity = new CommentNotificationEntity();
		DTOtoEntity(notificationEntity, notificationDTO);
		notificationEntity.setPhoto(PhotoUtil._DTOtoEntity(notificationDTO.getCommentedPhoto()));
		return notificationEntity;

	}

	public static CommentNotificationDTO _EntitytoDTO(CommentNotificationEntity notificationEntity) {
		CommentNotificationDTO notificationDTO = new CommentNotificationDTO();
		EntityToDTO(notificationEntity, notificationDTO);
		notificationDTO.setCommentedPhoto(PhotoUtil._EntitytoDTO(notificationEntity.getPhoto()));
		return notificationDTO;
	}

	public static FollowNotificationEntity _DTOtoEntity(FollowNotificationDTO notificationDTO) {
		FollowNotificationEntity notificationEntity = new FollowNotificationEntity();
		DTOtoEntity(notificationEntity, notificationDTO);
		return notificationEntity;

	}

	public static FollowNotificationDTO _EntitytoDTO(FollowNotificationEntity notificationEntity) {
		FollowNotificationDTO notificationDTO = new FollowNotificationDTO();
		EntityToDTO(notificationEntity, notificationDTO);
		return notificationDTO;
	}

	private static void mappingJson(JsonNode jsonRoot, NotificationDTO notificationDTO) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			if (jsonRoot.findValue("id") != null) {
				notificationDTO.setId(Long.parseLong(jsonRoot.findValue("id").toString()));
			}

			notificationDTO.setSender(objectMapper.readValue(jsonRoot.findValue("sender").toString(), UserDTO.class));
			notificationDTO.setReceiver(objectMapper.readValue(jsonRoot.findValue("receiver").toString(), UserDTO.class));
			notificationDTO.setContent(jsonRoot.findValue("content").asText());
			notificationDTO.setActivitytype(EActivity.valueOf(jsonRoot.findValue("activitytype").asText()));
			notificationDTO.setObjecturl(jsonRoot.findValue("objecturl").asText());
		
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public static NotificationDTO convert(String json, EActivity activitytype) {

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonRoot = null;
		try {
			jsonRoot = objectMapper.readTree(json);
			System.out.println(jsonRoot);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		switch (activitytype) {
		case LIKE_A_PHOTO: {
			LikeNotificationDTO notificationDTO = new LikeNotificationDTO();
			mappingJson(jsonRoot, notificationDTO);
			try {
				if (jsonRoot.findValue("attach") != null)
					notificationDTO.setLikedPhoto(
							objectMapper.readValue(jsonRoot.findValue("attach").toString(), PhotoDTO.class));
				else if (jsonRoot.findValue("likedPhoto") != null)
					notificationDTO.setLikedPhoto(
							objectMapper.readValue(jsonRoot.findValue("likedPhoto").toString(), PhotoDTO.class));

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			return notificationDTO;
		}
		case COMMENT_A_PHOTO: {
			CommentNotificationDTO notificationDTO = new CommentNotificationDTO();
			mappingJson(jsonRoot, notificationDTO);
			try {
				if (jsonRoot.findValue("attach") != null)
					notificationDTO.setCommentedPhoto(
							objectMapper.readValue(jsonRoot.findValue("attach").toString(), PhotoDTO.class));
				else if (jsonRoot.findValue("commentedPhoto") != null)
					notificationDTO.setCommentedPhoto(
							objectMapper.readValue(jsonRoot.findValue("commentedPhoto").toString(), PhotoDTO.class));

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			return notificationDTO;
		}
		case FOLLOW_A_USER: {
			FollowNotificationDTO notificationDTO = new FollowNotificationDTO();
			mappingJson(jsonRoot, notificationDTO);
			return notificationDTO;
		}
		default:
			break;
		}
		return null;

	}

}
