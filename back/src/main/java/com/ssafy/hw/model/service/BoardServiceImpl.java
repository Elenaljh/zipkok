package com.ssafy.hw.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.hw.model.dao.BoardDao;
import com.ssafy.hw.model.dto.Board;
import com.ssafy.hw.model.dto.BoardInsertDto;
import com.ssafy.hw.model.dto.BoardListDto;
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
	public BoardListDto search(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		param.put("key", map.get("key"));
		param.put("keyword", map.get("keyword"));
		param.put("type", map.get(String.valueOf("type")));
		System.out.println(param);
		System.out.println(map);
		List<Board> list = boardDao.search(param);

		int totalArticleCount = boardDao.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setBoards(list);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);
		
		return boardListDto;
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
