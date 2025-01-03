package com.ssafy.hw.model.dao;

import com.ssafy.hw.model.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface MemberDao {
    public int insertMember(Member member); //회원가입
    public int insertSocialMember(@Param("email") String email, @Param("name") String name); //소셜로그인 회원가입
    public Member selectMember(String email); //회원조회
    public Member loginMember(@Param("email") String email, @Param("password") String password); //회원가입
    public int updateMember(Member member); //회원정보 수정
    public int deleteMember(String email); //회원탈퇴
    public int updatePassword(@Param("password") String password, @Param("email") String email); //비번 업데이트
}
