package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dto.Member;

public interface MemberService {
    public int insertMember(Member member); //회원가입
    public Member selectMember(String email); //회원조회
    public Member loginMember(String email, String password); //회원가입
    public int updateMember(Member member); //회원정보 수정
    public int deleteMember(String email); //회원탈퇴
}
