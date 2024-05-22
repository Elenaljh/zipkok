package com.ssafy.hw.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hw.model.dto.Board;
import com.ssafy.hw.model.dto.BoardInsertDto;
import com.ssafy.hw.model.dto.BoardListDto;
import com.ssafy.hw.model.dto.BoardUpdateDto;

public interface BoardService {
	int insertBoard(BoardInsertDto board);
	//new search
	BoardListDto search(Map<String, String> params);
	Board getBoard(int boardId);
	List<Board> getListByType(int type);
	List<Board> selectAll();
	int updateBoard(BoardUpdateDto board);
	int deleteBoard(int boardId);
	int updateBoom(int boardId);
	int updateHit(int boardId);
	// 유저 권한 확인
    public boolean checkInsertNotice(int memberId, int type);
    public boolean checkModDel(int memberId, int boardId);
}
