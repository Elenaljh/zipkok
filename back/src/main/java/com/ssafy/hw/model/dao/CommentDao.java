package com.ssafy.hw.model.dao;

import java.util.List;

import com.ssafy.hw.model.dto.Comment;

public interface CommentDao {
	int insertComment(Comment comment);
	List<Comment> getComments(int boardId);
	int updateComment(Comment comment);
	int deleteComment(int commentId);
}
