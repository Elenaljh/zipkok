package com.ssafy.hw.model.dto;

import java.sql.Timestamp;

public class Board {
	private int boardId;
	private String title;
	private String content;
	private Timestamp createdAt;
	private int memberId;
	private int type; //0: 공지, 1: 자게, 2: q&a
	private int hit;
	private int boom; //추천수
	private String writer;
	public Board() {
		super();
	}
	public Board(String title, String content, Timestamp createdAt, int memberId, int type, int hit, String writer) {
		super();
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.memberId = memberId;
		this.type = type;
		this.hit = hit;
		this.writer = writer;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	public int getBoom() {
		return boom;
	}
	public void setBoom(int boom) {
		this.boom = boom;
	}
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt
				+ ", memberId=" + memberId + ", type=" + type + ", hit=" + hit + ", boom=" + boom + "]";
	}
	
	
	
}
