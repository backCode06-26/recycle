package com.team.recycle.controller;

import com.team.recycle.service.MemberService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
    private final MemberService memberService;

    public GameController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 점수 올리는 메서드
    @GetMapping("/increaseScore")
    public String increaseScore() {

        // email session
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        memberService.increaseScore(email);

        return "redirect:/";
    }

    // gameMain 페이지 들어가기
    @GetMapping("/gameMain")
    public String gameMain() {

        return "games/gameMain.html";
    }

    // game1 페이지 들어가기
    @GetMapping("/game1")
    public String game1() {

        return "games/game1.html";
    }

    // game2 페이지 들어가기
    @GetMapping("/game2")
    public String game2() {

        return "games/game2.html";
    }
}
