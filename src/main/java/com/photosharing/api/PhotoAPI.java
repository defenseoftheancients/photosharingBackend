package com.photosharing.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photosharing.dto.PhotoDTO;
import com.photosharing.dto.UserDTO;
import com.photosharing.payload.response.PhotoResponse;
import com.photosharing.service.IPhotoService;
import com.photosharing.util.UserUtil;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PhotoAPI {

	@Value("${tuanhoang.app.storage}")
	private String storage;

	@Autowired
	private IPhotoService photoService;

		

	@GetMapping("/photos")
	public ResponseEntity<?> getPhotos(@RequestParam(required = false, value = "username") String username,
			@RequestParam(required = true, value = "page") int page,
			@RequestParam(required = false, value = "sortParam") String sortParam,
			@RequestParam(required = false, value = "searchParam") String searchParam)
			throws JsonMappingException, JsonProcessingException {
		List<PhotoResponse> photoResponses = null;
		if (sortParam != null && sortParam.equals("DESC")) {
			Pageable pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "totalLike");
			photoResponses = photoService.findAllByLike(pageable, username);
		} else if (searchParam != null) {
			Pageable pageable = PageRequest.of(page - 1, 10);
			photoResponses = photoService.findAllByTags(pageable, username, Arrays.asList(searchParam.split("-", -1)));
		} else {
			Pageable pageable = PageRequest.of(page - 1, 10);
			photoResponses = photoService.findAll(pageable, username);
		}
		return ResponseEntity.ok(photoResponses);
	}

	@GetMapping("/photos/follow")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getPhotosOfFollowedUser(@RequestParam(required = false, value = "userid") String userid,
			@RequestParam(required = true, value = "page") int page)
			throws JsonMappingException, JsonProcessingException {
		List<PhotoResponse> photoResponses = null;

		Pageable pageable = PageRequest.of(page - 1, 10);
		photoResponses = photoService.findAllByFollowedUser(pageable, Long.parseLong(userid));

		return ResponseEntity.ok(photoResponses);
	}

	@GetMapping("/photos/{photoid}")
	public ResponseEntity<?> getPhoto(@RequestParam(required = false, value = "username") String username,
			@PathVariable Long photoid) throws JsonMappingException, JsonProcessingException {
		PhotoResponse photoResponse = photoService.findById(photoid, username);
		return photoResponse == null ? new ResponseEntity<>(photoResponse, HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(photoResponse, HttpStatus.OK);
	}

	@GetMapping("/photos/{photoid}/like")
	public ResponseEntity<?> getLikedUsesrOfPhoto(@PathVariable Long photoid)
			throws JsonMappingException, JsonProcessingException {
		List<UserDTO> userDTOs = photoService.findAllLikedUsersById(photoid);
		return new ResponseEntity<>(userDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/photos/{username}/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> downloadPhoto(@PathVariable String username, @PathVariable String filename)
			throws IOException {
		final ByteArrayResource inputStream = new ByteArrayResource(
				Files.readAllBytes(Paths.get(storage + "\\" + username + "\\" + filename)));
		return ResponseEntity.status(HttpStatus.OK).contentLength(inputStream.contentLength()).body(inputStream);
	}

	@GetMapping("/users/{userid}/photos")
	public ResponseEntity<?> getPhotoOfUser(@RequestParam(required = false, value = "username") String username,
			@RequestParam(required = true, value = "page") int page, @PathVariable Long userid,
			@RequestParam(required = false, value = "sortParam") String sortParam)
			throws JsonMappingException, JsonProcessingException {
		List<PhotoResponse> photoResponses = null;
		Pageable pageable = PageRequest.of(page - 1, 10);
		photoResponses = photoService.findAllByUserId(pageable, userid, username);

		return ResponseEntity.ok(photoResponses);
	}
	
	@GetMapping("/users/{userid}/photos/like")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getlikedPhotosOfUser(@RequestParam(required = true, value = "page") int page, 
			@PathVariable Long userid)
			throws JsonMappingException, JsonProcessingException {
		List<PhotoResponse> photoResponses = new ArrayList<>();
		Pageable pageable = PageRequest.of(page - 1, 10);
		photoResponses = photoService.findAllLikedPhotoByUserId(pageable, userid);

		return ResponseEntity.ok(photoResponses);
	}
	
	
	@PostMapping(value = "/users/{userid}/photos", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> uploadPhoto(@RequestParam(required = true, value = "user") String requestUserDTO,
			@RequestParam(required = true, value = "fileInfors") String fileInfors,
			@RequestParam(required = true, value = "fileContents") MultipartFile[] fileList) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		UserDTO userDTO = UserUtil._JsonToObject(requestUserDTO);
		List<PhotoDTO> photoDTOs = objectMapper.readValue(fileInfors, new TypeReference<List<PhotoDTO>>() {
		});
		for (int i = 0; i < photoDTOs.size(); i++)
			photoDTOs.get(i).setMultipartFile(fileList[i]);
		photoService.save(userDTO, photoDTOs);
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
