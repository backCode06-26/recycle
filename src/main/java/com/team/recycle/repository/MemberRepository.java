package com.team.recycle.repository;

import com.team.recycle.domain.Member;
import com.team.recycle.domain.UserDAO;

import java.util.List;

public interface MemberRepository {

    // 사용자 정보 저장하기
    void save(Member member);

    // 점수 업
    int scoreUp(String email);

    // email 사용자 정보가져오기
    Member findByEmail(String email);

    // 사용자 이름, 가입 시간, 게임 점수을 출력함
    UserDAO getUserInfo(String email);

    // 페이징
    List<UserDAO> getAllUserInfo(int start, int page);

}
