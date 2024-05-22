package com.ssafy.hw.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.hw.model.dto.Board;
import com.ssafy.hw.model.dto.BoardInsertDto;
import com.ssafy.hw.model.dto.BoardUpdateDto;

public interface BoardDao {
	int insertBoard(BoardInsertDto board);
	//new search
	List<Board> search(Map<String, Object> param);
	int getTotalArticleCount(Map<String, Object> param);
	List<Board> getListByType(int type);
	List<Board> selectAll();
	Board getBoard(int boardId);
	int updateBoard(BoardUpdateDto board);
	int deleteBoard(int boardId);
	int updateBoom(int boardId);
	int updateHit(int boardId);
	
	//유저 권한 확인
	int checkAdmin(int memberId);
	//글주인확인
	boolean checkWriter(Map<String, Object> map);
}
