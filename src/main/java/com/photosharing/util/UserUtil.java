package com.photosharing.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosharing.dto.RoleDTO;
import com.photosharing.dto.UserDTO;
import com.photosharing.entity.RoleEntity;
import com.photosharing.entity.UserEntity;
@Component
public class UserUtil {
	
	@Value("${tuanhoang.app.storage}")
	private String storage;
	
	public static UserEntity _DTOtoEntity(UserDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		Set<RoleEntity> roles = new HashSet<>();
		userDTO.getRoles().forEach(role-> {
			roles.add(RoleUtil._DTOtoEntity(role));
		});
		userEntity.setId(userDTO.getId());
		userEntity.setUsername(userDTO.getUsername());
		userEntity.setFullname(userDTO.getFullname());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setAvatar(userDTO.getAvatar());
		userEntity.setRoles(roles);
		return userEntity;
	}
	public static UserDTO _EntitytoDTO(UserEntity userEntity) {
		List<GrantedAuthority> authorities = userEntity.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		Set<RoleDTO> roles = new HashSet<>();
		userEntity.getRoles().forEach(role-> {
			roles.add(RoleUtil._EntitytoDTO(role));
		});
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userEntity.getId());
		userDTO.setUsername(userEntity.getUsername());
		userDTO.setFullname(userEntity.getFullname());
		userDTO.setPassword(userEntity.getPassword());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setAvatar(userEntity.getAvatar());
		userDTO.setRoles(roles);
		userDTO.setAuthorities(authorities);
		return userDTO;
	}
	public static UserDTO _JsonToObject(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			UserDTO userDTO = objectMapper.readValue(json, UserDTO.class);
			return userDTO;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public  UserEntity changeAvatar(UserDTO userDTO) throws IOException {
	
		String directoryString = storage + "\\" + userDTO.getUsername();
		System.out.println(directoryString);
		File directory = new File(directoryString);
		if (!directory.exists()) {
			directory.mkdir();
			System.out.print("No Folder");
			System.out.print("Folder created");
		}
		String filename = PhotoUtil.writeToDirectory(directoryString, userDTO.getMultipartFile(), "avatar");
		filename = userDTO.getUsername() + "\\" + filename;
		UserEntity userEntity =_DTOtoEntity(userDTO);
		userEntity.setAvatar(filename);
		return userEntity;
	}
	
	public static List<Map<String, Object>> convert(List<Object[]> list) {
		List<Map<String, Object>> result = new ArrayList<>();
		list.forEach(item -> {
			Map<String, Object> tmp = new LinkedHashMap<>();
			tmp.put("id", item[0]);
			tmp.put("username", item[1]);
			tmp.put("avatar", "http://localhost:8081/photosharing/api/photos/" + item[2]);
			result.add(tmp);
		});
		return result;
	}
	
	public static boolean isUserLiked(final List<UserEntity> userEntities, final String username){
	    return userEntities.stream().filter(o -> o.getUsername().equals(username)).findFirst().isPresent();
	}
	public static boolean isUserFollowed(final List<UserEntity> userEntities, final String username){
	    return userEntities.stream().filter(o -> o.getUsername().equals(username)).findFirst().isPresent();
	}
}
