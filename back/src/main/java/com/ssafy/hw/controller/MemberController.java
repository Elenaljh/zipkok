package com.ssafy.hw.controller;

import com.ssafy.hw.model.dto.LoginDto;
import com.ssafy.hw.model.dto.LoginOutputDto;
import com.ssafy.hw.model.dto.Member;
import com.ssafy.hw.model.dto.oauth.GoogleAuthResponseDto;
import com.ssafy.hw.model.dto.oauth.GoogleUserInfoResponseDto;
import com.ssafy.hw.model.dto.oauth.OauthCodeDto;
import com.ssafy.hw.model.service.MemberService;
import com.ssafy.hw.model.service.SocialLoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"}, allowCredentials = "true", allowedHeaders = "Access-Control-Allow-Origin")
public class MemberController {
	private final MemberService service;
	private final SocialLoginService socialLoginService;

	public MemberController(MemberService service, SocialLoginService socialLoginService) {
		this.service = service;
		this.socialLoginService = socialLoginService;
	}

	//회원가입
	@Operation(summary="회원가입", description="회원가입")
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
	@Operation(summary="회원정보조회, 아이디 중복 확인", description="userEmail을 쿼리파라미터로 보내면 아이디 중복 여부를 확인한다")
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
	@Operation(summary="회원정보 수정", description="회원 정보를 수정한다")
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
	@Operation(summary="회원탈퇴", description="계정을 삭제한다")
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
	@Operation(summary="로그인", description="로그인한 후, 세션에 이메일을 저장한다.")
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
			session.setAttribute("memberId", member.getMemberId());
			session.setMaxInactiveInterval(3600); //1시간동안 로그인 유지
			LoginOutputDto loginOutputDto = new LoginOutputDto(member.getName(), member.getEmail(), member.getMemberId());
			return new ResponseEntity<LoginOutputDto>(loginOutputDto, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	//로그아웃
	@Operation(summary="로그아웃", description="로그아웃 후 세션과 쿠키를 삭제한다.")
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
	@Operation(summary="회원인증", description="로그인한 상태인지 확인한다.")
	@GetMapping("/authorization")
	public Boolean authorize(@SessionAttribute(name="loginMember", required = false) String email) {
		if (email != null) {
			return true;
		} else {
			return false;
		}
	}

	@GetMapping("/oauth2/callback")
	public ResponseEntity<?> googleLogin(@RequestParam OauthCodeDto codeDto, HttpServletRequest request) {
		try {
			//소셜로그인 진행
			GoogleAuthResponseDto googleAuthResponseDto = socialLoginService.getAccessToken(codeDto.getCode()); //accessToken받기
			GoogleUserInfoResponseDto userInfo = socialLoginService.getUserInfo(googleAuthResponseDto.getAccess_token()); //유저 정보 받기

			//db에 회원 있나 조회
			Member member = service.selectMember(userInfo.getEmail());
			if (member == null) {
				service.insertSocialMember(userInfo.getEmail(), userInfo.getName());
				member = service.selectMember(userInfo.getEmail());
			}
			request.getSession().invalidate();
			HttpSession session = request.getSession(true);
			session.setAttribute("loginMember", userInfo.getEmail());
			session.setAttribute("memberId", member.getMemberId());
			session.setMaxInactiveInterval(3600); //1시간동안 로그인 유지
			LoginOutputDto loginOutputDto = new LoginOutputDto(member.getName(), member.getEmail(), member.getMemberId());
			return new ResponseEntity<LoginOutputDto>(loginOutputDto, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
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
