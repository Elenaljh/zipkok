package com.ssafy.hw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ssafy.hw.model.dto.Board;
import com.ssafy.hw.model.dto.BoardInsertDto;
import com.ssafy.hw.model.dto.BoardUpdateDto;
import com.ssafy.hw.model.service.BoardService;

@RestController
@RequestMapping("/board")
@CrossOrigin(originPatterns = {"*"} ,allowCredentials = "true")
public class BoardController {
	
	private final BoardService boardService;
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	//insertBoard(Board board)
	@PostMapping
	public ResponseEntity<?> insert(@SessionAttribute(name="memberId", required = false) String memberId, @RequestBody BoardInsertDto board) {
		try {
			System.out.println("memberID="+memberId);
			if(boardService.checkInsertNotice(Integer.parseInt(memberId), board.getType())) {
				int result = boardService.insertBoard(board);
				return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
			}
			return new ResponseEntity<String>("공지는 관리자만 작성할 수 있습니다.", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	//param으로 title, keyword, writer / type이 들어갈 예정
	@GetMapping("/list")
	public ResponseEntity<?> search(@RequestParam("key") String key, @RequestParam("keyword") String keyword, @RequestParam("type") int type) {
		try {
			Map<String, String> params = new HashMap<>();
			params.put("key", key);
			params.put("keyword", keyword);
			params.put("type", String.valueOf(type));
			System.out.println(params);
			List<Board> list = boardService.search(params);
			return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	//조건 없이 list 받음
	@GetMapping("/listAll")
	public ResponseEntity<?> get() {
		try {
			System.out.println("Hi");
			List<Board> list = boardService.selectAll();
			System.out.println("list "+list.size());
			if (list!=null && list.size()>0) {
				System.out.println("list "+list.size());
				return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	//개별 게시글 읽기
	@GetMapping("/{id}")
	public ResponseEntity<?> getBoard(@PathVariable("id") int id) {
		try {
			System.out.println("getBoard");
			boardService.updateHit(id);
			Board board = boardService.getBoard(id);
			if (board != null) {
				System.out.println("board "+board);
				return new ResponseEntity<Board>(board, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	//개별 게시글 읽기 (수정용)
	@GetMapping("/modify/{id}")
	public ResponseEntity<?> modifyGetBoard(@PathVariable("id") int id) {
		try {
			System.out.println("getBoard");
			Board board = boardService.getBoard(id);
			if (board != null) {
				System.out.println("board "+board);
				return new ResponseEntity<Board>(board, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	//int updateBoard(Board board);
	@PutMapping
	public ResponseEntity<?> updateBoard(@RequestBody BoardUpdateDto board) {
		try {
			int result = boardService.updateBoard(board);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	
	//int deleteBoard(int boardId);
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBoard(@SessionAttribute(name="memberId", required = false) String memberId, @PathVariable("id") int boardId) {
		try {
			if(boardService.checkModDel(Integer.parseInt(memberId), boardId)) {
				int result = boardService.deleteBoard(boardId);
				return new ResponseEntity<Integer>(result, HttpStatus.OK);
			}
			return new ResponseEntity<String>("권한이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//int updateBoom(int boardId);
	//int updateHit(int boardId);
	@GetMapping("/updateBoom/{id}")
	public ResponseEntity<?> updateBoom(@PathVariable("id") int boardId) {
		try {
			int result = boardService.updateBoom(boardId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/checkAuth")
	public ResponseEntity<?> checkAuth(@SessionAttribute(name="memberId", required = false) String memberId, @RequestParam("boardId") String boardId) {
		try {
			if(boardService.checkModDel(Integer.parseInt(memberId), Integer.parseInt(boardId))) {
				System.out.println("ㅇㅇ");
				return new ResponseEntity<String>("작성 가능", HttpStatus.OK);
			}
			System.out.println("no");
			return new ResponseEntity<String>("권한이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
