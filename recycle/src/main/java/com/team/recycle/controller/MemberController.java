package com.recycle.controller;

import com.recycle.domain.Member;
import com.recycle.domain.MemberDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping(value = "main")
    public String main() {
        return "main";
    }

    @GetMapping(value = "members/signup")
    public String signUp(MemberDAO memberDAO) {
        Member member = new Member();
        // 비밀번호 암호화, 사용 로직 sha_256
        memberDAO.encryptPassword();

        member.setEmail(memberDAO.getEmail());
        member.setPassword(memberDAO.getPassword());

        return "redirect:";
    }
}
