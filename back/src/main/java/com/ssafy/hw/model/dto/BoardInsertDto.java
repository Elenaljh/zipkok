package com.ssafy.hw.model.dto;

public class BoardInsertDto {
	private String title;
	private String content;
	private int memberId;
	private int type;
	private String writer;
	public BoardInsertDto(String title, String content, int memberId, int type, String writer) {
		super();
		this.title = title;
		this.content = content;
		this.memberId = memberId;
		this.type = type;
		this.writer = writer;
	}
	
	public int getType() {
		return type;
	}
	
	
}
