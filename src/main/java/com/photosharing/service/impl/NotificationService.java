package com.photosharing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photosharing.constant.EActivity;
import com.photosharing.dto.CommentNotificationDTO;
import com.photosharing.dto.FollowNotificationDTO;
import com.photosharing.dto.LikeNotificationDTO;
import com.photosharing.dto.NotificationDTO;
import com.photosharing.entity.CommentNotificationEntity;
import com.photosharing.entity.FollowNotificationEntity;
import com.photosharing.entity.LikeNotificationEntity;
import com.photosharing.repository.NotificationRepository;
import com.photosharing.service.INotificationService;
import com.photosharing.util.NotificationUtil;

@Service
public class NotificationService implements INotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public NotificationDTO save(LikeNotificationDTO notificationDTO) {
		LikeNotificationEntity notificationEntity = NotificationUtil._DTOtoEntity(notificationDTO);
		notificationEntity = notificationRepository.save(notificationEntity);
		return NotificationUtil._EntitytoDTO(notificationEntity);
	}

	@Override
	public NotificationDTO setSeen(LikeNotificationDTO notificationDTO) {
		LikeNotificationEntity notificationEntity = (LikeNotificationEntity) notificationRepository
				.findById(notificationDTO.getId()).orElse(null);
		if (notificationEntity == null)
			return null;
		else {
			notificationEntity.setIsRead(true);
			notificationEntity = notificationRepository.save(notificationEntity);
			return NotificationUtil._EntitytoDTO(notificationEntity);
		}
	}

	@Override
	public void delete(LikeNotificationDTO notificationDTO) {
		notificationRepository.delete(notificationDTO.getSender().getId(), EActivity.LIKE_A_PHOTO.toString());
	}

	@Override
	public NotificationDTO save(CommentNotificationDTO notificationDTO) {
		CommentNotificationEntity notificationEntity = NotificationUtil._DTOtoEntity(notificationDTO);
		notificationEntity = notificationRepository.save(notificationEntity);
		return NotificationUtil._EntitytoDTO(notificationEntity);
	}

	@Override
	public NotificationDTO setSeen(CommentNotificationDTO notificationDTO) {
		CommentNotificationEntity notificationEntity = (CommentNotificationEntity) notificationRepository
				.findById(notificationDTO.getId()).orElse(null);
		if (notificationEntity == null)
			return null;
		else {
			notificationEntity.setIsRead(true);
			notificationEntity = notificationRepository.save(notificationEntity);
			return NotificationUtil._EntitytoDTO(notificationEntity);
		}
	}

	@Override
	public NotificationDTO save(FollowNotificationDTO notificationDTO) {
		FollowNotificationEntity notificationEntity = NotificationUtil._DTOtoEntity(notificationDTO);
		notificationEntity = notificationRepository.save(notificationEntity);
		return NotificationUtil._EntitytoDTO(notificationEntity);
	}

	@Override
	public NotificationDTO setSeen(FollowNotificationDTO notificationDTO) {
		FollowNotificationEntity notificationEntity = (FollowNotificationEntity) notificationRepository.findById(notificationDTO.getId()).orElse(null);
		if (notificationEntity == null)
			return null;
		else {
			notificationEntity.setIsRead(true);
			notificationEntity = notificationRepository.save(notificationEntity);
			return NotificationUtil._EntitytoDTO(notificationEntity);
		}
	}

	@Override
	public void delete(FollowNotificationDTO notificationDTO) {
		notificationRepository.delete(notificationDTO.getSender().getId(), EActivity.FOLLOW_A_USER.toString());
	}

	@Override
	public List<Object> findAll(Long userid) {
		List<Object> notificationEntities = notificationRepository.findAllByReceiverIdOrderByIdDesc(userid);
		List<Object> notifications = new ArrayList<Object>();
		notificationEntities.forEach(notificationEntity -> {
			
			switch (notificationEntity.getClass().getSimpleName()) {
			case "LikeNotificationEntity":
				notifications.add(NotificationUtil._EntitytoDTO((LikeNotificationEntity) notificationEntity));
				break;
			case "FollowNotificationEntity":
				notifications.add(NotificationUtil._EntitytoDTO((FollowNotificationEntity) notificationEntity));
				break;
			case "CommentNotificationEntity":
				notifications.add(NotificationUtil._EntitytoDTO((CommentNotificationEntity) notificationEntity));
				break;
			default:
				break;
			}
		});

		return notifications;
	}

}
