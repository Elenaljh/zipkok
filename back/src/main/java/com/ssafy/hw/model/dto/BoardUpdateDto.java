package com.ssafy.hw.model.dto;

public class BoardUpdateDto {
	private int boardId;
	private String title;
	private String content;
	private int type;
	public BoardUpdateDto(int boardId, String title, String content, int type) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.type = type;
	}
	
}
