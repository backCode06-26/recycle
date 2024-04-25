package com.recycle.service;

import com.recycle.domain.Member;
import com.recycle.repository.DateMemberRepository;

public class MemberService {
    private final DateMemberRepository dateMemberRepository;

    public MemberService(DateMemberRepository dateMemberRepository) {
        this.dateMemberRepository = dateMemberRepository;
    }

    public void join(Member member) {
        
    }

    // 자신의 정보 조회

    // 사용자 정보 리스트 조회

}
