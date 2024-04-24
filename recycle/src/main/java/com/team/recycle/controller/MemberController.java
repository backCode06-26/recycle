package com.team.recycle.controller;

import ch.qos.logback.core.model.Model;
import com.team.recycle.domain.Member;
import com.team.recycle.domain.MemberDTO;
import com.team.recycle.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    final private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // main 페이지 들어가기
    @PostMapping(value = "games/score")
    public String score(Model model) {
        memberService.scoreUp();
    }
}
