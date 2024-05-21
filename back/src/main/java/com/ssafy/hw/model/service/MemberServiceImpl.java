package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dao.MemberDao;
import com.ssafy.hw.model.dto.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberDao dao;

    public MemberServiceImpl(MemberDao dao) {
        this.dao = dao;
    }

    @Override
    public int insertMember(Member member) {
        return dao.insertMember(member);
    }

    @Override
    public int insertSocialMember(String email, String name) {
        return dao.insertSocialMember(email, name);
    }

    @Override
    public Member selectMember(String email) {
        return dao.selectMember(email);
    }

    @Override
    public Member loginMember(String email, String password) {
        return dao.loginMember(email, password);
    }

    @Override
    public int updateMember(Member member) {
        return dao.updateMember(member);
    }

    @Override
    public int deleteMember(String email) {
        return dao.deleteMember(email);
    }
}
