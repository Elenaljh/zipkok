package com.ssafy.hw.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.hw.model.dao.CommentDao;
import com.ssafy.hw.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	private final CommentDao commentDao;
	public CommentServiceImpl(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	@Transactional
	public int insertComment(Comment comment) {
		return commentDao.insertComment(comment);
	}

	@Override
	public List<Comment> getComments(int boardId) {
		return commentDao.getComments(boardId);
	}

	@Override
	@Transactional
	public int updateComment(Comment comment) {
		return commentDao.updateComment(comment);
	}

	@Override
	@Transactional
	public int deleteComment(int commentId) {
		return commentDao.deleteComment(commentId);
	}

}
