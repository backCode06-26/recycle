package com.team.recycle.service;

import com.team.recycle.domain.Member;
import com.team.recycle.domain.MemberDTO;
import com.team.recycle.domain.UserDAO;
import com.team.recycle.repository.DataMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private final DataMemberRepository dataMemberRepository;

    public MemberService(DataMemberRepository dataMemberRepository) {
        this.dataMemberRepository = dataMemberRepository;
    }

    // 저장하기
    public void join(Member member) {
        checkDuplicateMember(member);
        dataMemberRepository.save(member);
    }

    // 이미 존재하는 데이터인지 채크
    public void checkDuplicateMember(Member member) {
        Member findMember = dataMemberRepository.findByEmail(member.getEmail());
        if(findMember != null && findMember.getId() != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public void checkMember(Member member) {
        Member findMember = dataMemberRepository.findByEmail(member.getEmail());
        if(findMember == null && findMember.getId() == null) {
             throw new IllegalStateException("가입이 되지 않은 회원입니다.");
        }
    }

    // 유저의 점수 올리기
    public void scoreUp(String email) {
        dataMemberRepository.scoreUp(email);
    }

    // 유저 정보 조회
    public UserDAO getUserDAO(String email) {
        return dataMemberRepository.getUserInfo(email);
    }

    // 유저 점수 리스트
    public List<UserDAO> getUserDAOList(int start, int page) {
        return dataMemberRepository.getAllUserInfo(start, page);
    }
}
