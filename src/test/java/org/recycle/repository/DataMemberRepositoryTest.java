package org.recycle.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.recycle.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DataMemberRepositoryTest {

    @Autowired
    private DataMemberRepository dataMemberRepository;

    @Test
    void save() {
        // 회원 정보 생성
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPassword("1234");

        // 회원 정보 저장
        dataMemberRepository.save(member);

        // 저장된 회원 정보 조회
        Member savedMember = dataMemberRepository.findByEmail("test@test.com");

        // 저장된 회원 정보가 null이 아닌지 확인
        assertNotNull(savedMember);
        // 저장된 회원 정보의 이메일과 비밀번호가 입력한 값과 일치하는지 확인
        assertEquals("test@test.com", savedMember.getEmail());
        assertEquals("1234", savedMember.getPassword());
    }
}
