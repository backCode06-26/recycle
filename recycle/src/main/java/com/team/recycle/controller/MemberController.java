package com.team.recycle.controller;

import com.team.recycle.domain.Member;
import com.team.recycle.domain.MemberDTO;
import com.team.recycle.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {
    final private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signup() {
        return "members/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute MemberDTO memberDTO, Model model) {
        try {
            Member member = new Member();
            member.setEmail(memberDTO.getEmail());
            member.setPassword(memberDTO.getPassword());
            memberService.join(member);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "members/signup"; // 회원가입 페이지로 다시 이동
        }
        return "redirect:/"; // 회원가입 후 메인 페이지로 리다이렉트
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO()); // 빈 MemberDTO를 모델에 추가하여 폼에 사용
        return "members/login"; // 뷰 이름만 반환
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model) {
        try {
            Member member = new Member();
            member.setEmail(memberDTO.getEmail());
            member.setPassword(memberDTO.getPassword());
            memberService.checkMember(member);
        } catch (IllegalStateException e) {
            // 예외 처리 - 가입되지 않은 회원일 경우
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "members/login"; // 로그인 페이지로 다시 이동
        }
        return "redirect:/"; // 로그인 성공 시 메인 페이지로 리다이렉트
    }
}