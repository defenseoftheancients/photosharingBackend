package com.photosharing.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosharing.dto.CommentDTO;
import com.photosharing.entity.CommentEntity;

@Component
public class CommentUtil {
	
	public static CommentDTO _JSONtoObject(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonRoot = null;
		try {
			jsonRoot = objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			return objectMapper.readValue(jsonRoot.findValue("comment").toString(), CommentDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static CommentEntity _DTOtoEntity(CommentDTO commentDTO) {
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setId(commentDTO.getId());
		commentEntity.setContent(commentDTO.getContent());
		commentEntity.setPhoto(PhotoUtil._DTOtoEntity(commentDTO.getPhoto()));
		commentEntity.setUser(UserUtil._DTOtoEntity(commentDTO.getAuthor()));
		return commentEntity;

	}

	public static CommentDTO _EntitytoDTO(CommentEntity commentEntity) {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setId(commentEntity.getId());
		commentDTO.setContent(commentEntity.getContent());
		commentDTO.setPhoto(PhotoUtil._EntitytoDTO(commentEntity.getPhoto()));
		commentDTO.setAuthor(UserUtil._EntitytoDTO(commentEntity.getUser()));
		commentDTO.setTimesent(commentEntity.getTimesent());
		if(commentEntity.getReplies() != null) {
			commentEntity.getReplies().forEach(reply-> {
				commentDTO.getChildren().add(_EntitytoDTO(reply));
			});
		}
		return commentDTO;
	}
}
