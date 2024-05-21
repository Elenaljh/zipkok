package com.ssafy.hw.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.hw.model.dao.BoardDao;
import com.ssafy.hw.model.dto.Board;
import com.ssafy.hw.model.dto.BoardInsertDto;
import com.ssafy.hw.model.dto.BoardUpdateDto;

@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardDao boardDao;
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	@Transactional
	public int insertBoard(BoardInsertDto board) {
		return boardDao.insertBoard(board);
	}

	//new search
	@Override
	public List<Board> search(Map<String, String> params) {
		return boardDao.search(params);
	}
	
	@Override
	public Board getBoard(int boardId) {
		return boardDao.getBoard(boardId);
	}

	@Override
	public List<Board> selectAll() {
		return boardDao.selectAll();
	}

	@Override
	@Transactional
	public int updateBoard(BoardUpdateDto board) {
		return boardDao.updateBoard(board);
	}

	@Override
	@Transactional
	public int deleteBoard(int boardId) {
		return boardDao.deleteBoard(boardId);
	}

	@Override
	@Transactional
	public int updateBoom(int boardId) {
		return boardDao.updateBoom(boardId);
	}

	@Override
	@Transactional
	public int updateHit(int boardId) {
		return boardDao.updateHit(boardId);
	}

	@Override
	public List<Board> getListByType(int type) {
		return boardDao.getListByType(type);
	}

	@Override
	public boolean checkInsertNotice(int memberId, int type) {
		if(type == 1 || type == 2) return true;
		return boardDao.checkAdmin(memberId) == 1;
	}

	@Override
	public boolean checkModDel(int memberId, int boardId) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("boardId", boardId);
		return boardDao.checkWriter(map);
	}

}
