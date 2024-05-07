package com.team.recycle.repository;

import com.team.recycle.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataMemberRepositoryTest {

    @Autowired
    private DataMemberRepository dataMemberRepository;

    @Test
    void save() {
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPassword("1234");

        dataMemberRepository.save(member);
    }

    @Test
    void scoreUp() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void getUserInfo() {
    }

    @Test
    void getAllUserInfo() {
    }
}
