package com.photosharing.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.photosharing.dto.PhotoDTO;
import com.photosharing.dto.TagDTO;
import com.photosharing.dto.UserDTO;
import com.photosharing.entity.PhotoEntity;
import com.photosharing.entity.TagEntity;
import com.photosharing.payload.response.PhotoResponse;

@Component
public class PhotoUtil {

	@Value("${tuanhoang.app.storage}")
	private String storage;

	public static PhotoEntity _DTOtoEntity(PhotoDTO photoDTO) {
		PhotoEntity photoEntity = new PhotoEntity();
		photoEntity.setId(photoDTO.getId());
		photoEntity.setTitle(photoDTO.getTitle());
		photoEntity.setDescription(photoDTO.getDescription());
		photoEntity.setResolution(photoDTO.getResolution());
		photoEntity.setDevice(photoDTO.getDevice());
		photoEntity.setTimepost(photoDTO.getTimepost());
		List<TagEntity> tagEntities = new ArrayList<>();
		photoDTO.getTags().forEach(tag -> {
			TagEntity tagEntity = TagUtil._DTOtoEntity(tag);
			tagEntities.add(tagEntity);
		});
		photoEntity.setTags(tagEntities);
		return photoEntity;

	}

	public static PhotoDTO _EntitytoDTO(PhotoEntity photoEntity) {
		PhotoDTO photoDTO = new PhotoDTO();
		photoDTO.setId(photoEntity.getId());
		photoDTO.setTitle(photoEntity.getTitle());
		photoDTO.setDescription(photoEntity.getDescription());
		photoDTO.setTimepost(photoEntity.getTimepost());
		photoDTO.setResolution(photoEntity.getResolution());
		photoDTO.setDevice(photoEntity.getDevice());
		photoDTO.setPath(photoEntity.getPath());
		photoDTO.setTotalLike((long) photoEntity.getLikedUsers().size());
		List<TagDTO> tagDTOs = new ArrayList<>();
		photoEntity.getTags().forEach(tag -> {
			tagDTOs.add(TagUtil._EntitytoDTO(tag));
		});
		photoDTO.setTags(tagDTOs);
		return photoDTO;
	}

	public static List<PhotoResponse> setLikeNFollow(List<PhotoEntity> photoEntities, String username) {
		List<PhotoResponse> photoResponses = new ArrayList<>();
		for (PhotoEntity photoEntity : photoEntities) {
			UserDTO userDTO = UserUtil._EntitytoDTO(photoEntity.getUser());
			PhotoDTO photoDTO = PhotoUtil._EntitytoDTO(photoEntity);
			if(username != null) {
				if(UserUtil.isUserLiked(photoEntity.getLikedUsers(), username))
					photoDTO.setLikedByUser(true);
				else
					photoDTO.setLikedByUser(false);
//				if(UserUtil.isUserFollowed(photoEntity.getUser().getFollower(), username))
//					userDTO.setIsFollowing(true);
				if(UserUtil.isUserFollowed(photoEntity.getUser().getFollower(), username))
					userDTO.setIsFollowing(true);
				else
					userDTO.setIsFollowing(false);
			}
			else {
				photoDTO.setLikedByUser(false);
				userDTO.setIsFollowing(false);
			}

			photoResponses.add(new PhotoResponse(photoDTO, userDTO));
		}
		return photoResponses;
	}
	
//	private static String writeToDirectory(String directory, String base64, String filename) {
//		String[] strings = base64.split(",");
//		String extension;
//		switch (strings[0]) {// check image's extension
//		case "data:image/jpeg;base64":
//			extension = "jpeg";
//			break;
//		case "data:image/png;base64":
//			extension = "png";
//			break;
//		default:// should write cases for more images types
//			extension = "jpg";
//			break;
//		}
//		// convert base64 string to binary data
//		byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
//		String path = directory + "\\" + filename + "." + extension;
//		File file = new File(path);
//		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
//			outputStream.write(data);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return filename + "." + extension;
//	}
	public static String writeToDirectory(String directory, MultipartFile argFile, String filename) {
		 String orgName = argFile.getOriginalFilename();
		 String extension = FilenameUtils.getExtension(orgName);
		String path = directory + "\\" + filename + "." + extension;
		File file = new File(path);
		try {
			argFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
		
			e.printStackTrace();
		}
		return filename + "." + extension;
	}
//	private void readMetadata(String filename) {
//		File savedFile = new File(filename);
//		try {
//			Metadata metadata = ImageMetadataReader.readMetadata(savedFile);
//
//			for (Directory directory : metadata.getDirectories()) {
//
//				for (Tag tag : directory.getTags()) {
//					System.out.println(tag);
//				}
//
//				for (String error : directory.getErrors()) {
//					System.err.println("ERROR: " + error);
//				}
//			}
//		} catch (ImageProcessingException e) {
//			System.out.println(e);
//		} catch (IOException e) {
//			System.out.println(e);
//		}
//	}

	public List<PhotoEntity> save(UserDTO userDTO, List<PhotoDTO> photoDTOs) throws IOException {
		List<PhotoEntity> photoEntities = new ArrayList<>();

		String directoryString = storage + "\\" + userDTO.getUsername();
		System.out.println(directoryString);
		File directory = new File(directoryString);
		if (!directory.exists()) {
			directory.mkdir();
			System.out.print("No Folder");
			System.out.print("Folder created");
		}
		int fileCount = directory.list().length + 1;

		for (int i = 0; i < photoDTOs.size(); i++, fileCount++) {
			
			//base 64
//			String filename = writeToDirectory(directoryString, photoDTOs.get(i).getContent(),
//					Integer.toString(fileCount));
//			readMetadata(storage + "\\" + userDTO.getUsername() + "\\" + filename);
			
			
			String filename = writeToDirectory(directoryString, photoDTOs.get(i).getMultipartFile(), Integer.toString(fileCount));
			filename = userDTO.getUsername() + "\\" + filename;
			PhotoEntity photoEntity = _DTOtoEntity(photoDTOs.get(i));
			
			photoEntity.setPath(filename);
			photoEntity.setUser(UserUtil._DTOtoEntity(userDTO));
			photoEntities.add(photoEntity);
		}
		return photoEntities;
	}
}
