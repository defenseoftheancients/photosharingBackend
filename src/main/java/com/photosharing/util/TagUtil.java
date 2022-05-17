package com.photosharing.util;

import org.springframework.stereotype.Component;

import com.photosharing.dto.TagDTO;
import com.photosharing.entity.TagEntity;
@Component
public class TagUtil {
	public static TagEntity _DTOtoEntity(TagDTO tagDTO) {
		TagEntity tagEntity = new TagEntity();
		tagEntity.setId(tagDTO.getId());
		tagEntity.setTagname(tagDTO.getTagname());
		return tagEntity;
		
	}
	public static TagDTO _EntitytoDTO(TagEntity tagEntity) {
		TagDTO tagDTO = new TagDTO();
		tagDTO.setId(tagEntity.getId());
		tagDTO.setTagname(tagEntity.getTagname());
		return tagDTO;

	}
}
