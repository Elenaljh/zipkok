package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dto.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberService {
    public int insertMember(Member member); //회원가입
    public int insertSocialMember(String email, String name); //소셜로그인 가입
    public Member selectMember(String email); //회원조회
    public Member loginMember(String email, String password); //회원가입
    public int updateMember(Member member); //회원정보 수정
    public int deleteMember(String email); //회원탈퇴
    public int updatePassword(String password, String email);
    public String hashPassword(String plainTextPassword); //암호화
    public boolean checkPass(String plainPassword, String hasedPassword); //암호화, 원문 매칭
}
