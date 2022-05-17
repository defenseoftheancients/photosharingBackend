package com.photosharing.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photosharing.dto.CommentDTO;
import com.photosharing.service.ICommentService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class CommentAPI {

	@Autowired
	private ICommentService commentService;
	
	@GetMapping("/photos/{photoid}/comments")
	public ResponseEntity<?> getComment(@PathVariable Long photoid) {
		List<CommentDTO> commentDTOs = commentService.findAllByPhotoIdOrderByIdDesc(photoid);
		return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
	}
	
}
