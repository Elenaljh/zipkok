package com.ssafy.hw.controller;

import com.ssafy.hw.model.dto.LoginDto;
import com.ssafy.hw.model.dto.LoginOutputDto;
import com.ssafy.hw.model.dto.Member;
import com.ssafy.hw.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(originPatterns = {"*"} ,allowCredentials = "true")
public class MemberController {
	private final MemberService service;

	public MemberController(MemberService service) {
		this.service = service;
	}

	//회원가입
	@PostMapping("/member")
	public ResponseEntity<?> join(@RequestBody Member member, HttpServletRequest request) {
		try {
			service.insertMember(member);
			return new ResponseEntity<String>("회원가입 완료", HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}


	//회원정보조회 - 아이디 중복 확인용 api도 겸업
	//아이디 중복 확인 -> 쿼리 파라미터로 userEmail이 붙어야 함
	@GetMapping("/member")
	public ResponseEntity<?> searchMemberById(
			@RequestParam(required = false) String userEmail,
			@SessionAttribute(name="loginMember", required = false) String email
	) {
		try {
			Member member = null;
			if (userEmail!=null) { //아이디 중복확인인 경우
				member = service.selectMember(userEmail);
				if (member == null) {
					return new ResponseEntity<String>("아이디 생성 가능", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("중복된 아이디", HttpStatus.BAD_REQUEST);
				}
			} else { //단순 회원정보 조회용인 경우
				if (email == null) return loginTimeout(); //로그인한 상태가 아닌 경우
				member = service.selectMember(email);
			}
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}


	//회원 정보 수정
	@PutMapping("/member")
	public ResponseEntity<?> updateMember(@RequestBody Member member, @SessionAttribute(name="loginMember", required = false) String email) {
		try {
			if (email== null) return loginTimeout();
			service.updateMember(member);
			return new ResponseEntity<String>("회원정보 수정 성공", HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}


	//회원 탈퇴
	@DeleteMapping("/member")
	public ResponseEntity<?> deleteMember(@SessionAttribute(name="loginMember", required = false) String email, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (email == null) return loginTimeout();
			service.deleteMember(email);
			HttpSession session = request.getSession(false);  // Session이 없으면 null return
			if(session != null) {
				session.invalidate();
			}
			// JSESSIONID 쿠키 삭제
			Cookie cookie = new Cookie("JSESSIONID", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			return new ResponseEntity<String>("회원탈퇴 완료", HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}


	//로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto dto, HttpServletRequest request) {
		try {
			Member member = service.loginMember(dto.getEmail(), dto.getPassword());
			if (member == null) {
				return new ResponseEntity<String>("로그인 실패", HttpStatus.BAD_REQUEST);
			}
			request.getSession().invalidate();
			HttpSession session = request.getSession(true);
			session.setAttribute("loginMember", member.getEmail());
			session.setMaxInactiveInterval(3600); //1시간동안 로그인 유지
			LoginOutputDto loginOutputDto = new LoginOutputDto(member.getName(), member.getEmail(), member.getMemberId());
			return new ResponseEntity<LoginOutputDto>(loginOutputDto, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	//로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> logout(@SessionAttribute(name="loginMember", required = false) String email, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);  // Session이 없으면 null return
			if(session != null) {
				session.invalidate();
			}
			// JSESSIONID 쿠키 삭제
			Cookie cookie = new Cookie("JSESSIONID", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			return new ResponseEntity<String>("로그아웃 완료", HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	//회원인증
	@GetMapping("/authorization")
	public Boolean authorize(@SessionAttribute(name="loginMember", required = false) String email) {
		if (email != null) {
			return true;
		} else {
			return false;
		}
	}



	private ResponseEntity<String> loginTimeout() {
		return new ResponseEntity<String>("다시 로그인하세요", HttpStatus.UNAUTHORIZED);
	}


	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
