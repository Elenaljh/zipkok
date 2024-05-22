package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dao.MemberDao;
import com.ssafy.hw.model.dto.Member;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberDao dao;

    public MemberServiceImpl(MemberDao dao) {
        this.dao = dao;
    }

    @Override
    public int insertMember(Member member) {
        String hasedPassword = hashPassword(member.getPassword());
        member.setPassword(hasedPassword);
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
        Member member = dao.selectMember(email);
        if (checkPass(password, member.getPassword())) {
            return member;
        } else {
            return null;
        }
    }

    @Override
    public int updateMember(Member member) {
        String hasedPassword = hashPassword(member.getPassword());
        member.setPassword(hasedPassword);
        return dao.updateMember(member);
    }

    @Override
    public int deleteMember(String email) {
        return dao.deleteMember(email);
    }

    @Override
    public int updatePassword(String password, String email) {
        String hasedPassword = hashPassword(password);
        return dao.updatePassword(hasedPassword, email);
    }

    @Override
    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override
    public boolean checkPass(String plainPassword, String hasedPassword) {
        if (BCrypt.checkpw(plainPassword, hasedPassword)) {
            return true;
        } else {
            return false;
        }
    }
}
