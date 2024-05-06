package org.recycle.service;

import org.junit.jupiter.api.Test;
import org.recycle.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void join() {
        Member member = new Member();
        member.setEmail("test@test123.com");
        member.setPassword("123412312");

        memberService.join(member);

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member);
        });
    }

    @Test
    void checkDuplicateMember() {
        // 테스트 코드 작성
    }

    @Test
    void loginCheckMember() {
        // 테스트 코드 작성
    }

    @Test
    void scoreUp() {
        // 테스트 코드 작성
    }

    @Test
    void getUserDAO() {
        // 테스트 코드 작성
    }

    @Test
    void getUserDAOList() {
        // 테스트 코드 작성
    }
}
