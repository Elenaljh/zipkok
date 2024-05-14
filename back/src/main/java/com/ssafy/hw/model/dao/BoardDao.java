package com.ssafy.hw.model.dao;

import java.util.List;

import com.ssafy.hw.model.dto.Board;
import com.ssafy.hw.model.dto.BoardInsertDto;
import com.ssafy.hw.model.dto.BoardUpdateDto;
import java.util.Map;

public interface BoardDao {
	int insertBoard(BoardInsertDto board);
	//new search
	List<Board> search(Map<String, String> params);
	List<Board> getListByType(int type);
	List<Board> selectAll();
	Board getBoard(int boardId);
	int updateBoard(BoardUpdateDto board);
	int deleteBoard(int boardId);
	int updateBoom(int boardId);
	int updateHit(int boardId);
}
