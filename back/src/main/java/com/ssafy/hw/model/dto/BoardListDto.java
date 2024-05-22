package com.ssafy.hw.model.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BoardListDto {
	private List<Board> boards;
	private int currentPage;
	private int totalPageCount;

}
