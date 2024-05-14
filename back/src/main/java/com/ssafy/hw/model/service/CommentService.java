package com.ssafy.hw.model.service;

import java.util.List;

import com.ssafy.hw.model.dto.Comment;

public interface CommentService {
	int insertComment(Comment comment);
	List<Comment> getComments(int boardId);
	int updateComment(Comment comment);
	int deleteComment(int commentId);
}
