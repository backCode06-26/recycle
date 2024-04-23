package com.recycle.repository;

import com.recycle.domain.MemberDAO;
import com.recycle.domain.UserDAO;

import java.util.List;

public interface MemberRepository {

    // 사용자 정보 저장하기
    void save(MemberDAO memberDAO);
    // 사용자 이름, 가입 시간, 게임 점수을 출력함
    UserDAO getUserInfo(Long id);

    List<UserDAO> getAllUserInfo(int start, int page);

}
