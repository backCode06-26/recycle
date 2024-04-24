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

    //    저장하기
    public void join(MemberDTO memberDTO) {
        checkDuplicateMember(memberDTO);
        dataMemberRepository.save(memberDTO);
    }

    public void checkDuplicateMember(MemberDTO memberDTO) {
        Member findMember = dataMemberRepository.findByEmail(memberDTO.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    // 유저의 점수 올리기
    public void scoreUp(String email) {
        dataMemberRepository.scoreUp(email);
    }

//    유저 정보 조회
    public UserDAO getUserDAO(String email) {
        return dataMemberRepository.getUserInfo(email);
    }

//    유저 점수 리스트
    public List<UserDAO> getUserDAOList(int start, int page) {
        return dataMemberRepository.getAllUserInfo(start, page);
    }
}
