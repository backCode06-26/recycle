package com.recycle.service;

import com.recycle.domain.UserDAO;
import com.recycle.repository.DataMemberRepository;

import java.util.List;

public class MemberService {

    private final DataMemberRepository dataMemberRepository;

    public MemberService(DataMemberRepository dataMemberRepository) {
        this.dataMemberRepository = dataMemberRepository;
    }

    //    저장하기
    public void join() {
        // 같은 계정이 있는지 확인하는 로직

    }
//    유저 정보 조회
    public UserDAO getUserDAO(Long id) {
        return dataMemberRepository.getUserInfo(id);
        // id는 post로 id값을 받아야함
    }

//    유저 점수 리스트
    public List<UserDAO> getUserDAOList() {
//        paging하는 로직이 필요함 repository에서 해야함
        return null;
    }
}
