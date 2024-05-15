package com.team.recycle.service;

import com.team.recycle.domain.Member;
import com.team.recycle.domain.dto.UserDAO;
import com.team.recycle.repository.DataMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final DataMemberRepository dataMemberRepository;

    @Autowired
    public MemberService(DataMemberRepository dataMemberRepository) {
        this.dataMemberRepository = dataMemberRepository;
    }

    public void join(Member member) {
        checkDuplicateMember(member);
        dataMemberRepository.save(member);
    }

    public void checkDuplicateMember(Member member) {
        Member findMember = dataMemberRepository.findByEmail(member.getEmail());
        if(findMember != null && findMember.getId() != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public void increaseScore(String email) { dataMemberRepository.scoreUp(email); }

    public UserDAO getUser(String email) {
        return dataMemberRepository.getUserInfo(email);
    }

    public List<UserDAO> getUserList(int start, int page) { return dataMemberRepository.getAllUserInfo(start, page); }
}
