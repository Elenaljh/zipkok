package com.ssafy.hw.model.dto;

public class Comment {
	private int commentId;
	private String content;
	private int boardId;
	private int memberId;
	private String writer;
	public Comment() {
		super();
	}
	public Comment(String content, int boardId) {
		super();
		this.content = content;
		this.boardId = boardId;
	}
	public Comment(int commentId, String content, int boardId, int memberId) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.boardId = boardId;
		this.memberId = memberId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int id) {
		this.commentId = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	@Override
	public String toString() {
		return "Comment [comment_id=" + commentId + ", content=" + content + ", boardId=" + boardId + " memberId="+memberId+"]";
	}
	
}
