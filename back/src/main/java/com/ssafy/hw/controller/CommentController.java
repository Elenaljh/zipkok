package com.ssafy.hw.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hw.model.dto.Comment;
import com.ssafy.hw.model.service.CommentService;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
public class CommentController {
	
	private final CommentService commentService;
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	//int insertComment(Comment comment);
	@PostMapping()
	public ResponseEntity<?> insert(@RequestBody Comment comment) {
		try {
			int result = commentService.insertComment(comment);
			return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//List<Comment> getComments(int boardId);
	@GetMapping()
	public ResponseEntity<?> getComments(@RequestParam int boardId) {
		try {
			List<Comment> list = commentService.getComments(boardId);
			if (list != null && list.size() > 0) {
				return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//int updateComment(Comment comment);
	@PutMapping()
	public ResponseEntity<?> updateContent(@RequestBody Comment comment) {
		try {
			int result = commentService.updateComment(comment);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//int deleteComment(int commentId);
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("id") int commentId) {
		try {
			int result = commentService.deleteComment(commentId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
