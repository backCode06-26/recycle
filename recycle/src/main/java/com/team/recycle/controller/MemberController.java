package com.team.recycle.controller;

import com.team.recycle.domain.Member;
import com.team.recycle.domain.MemberDTO;
import com.team.recycle.domain.UserDAO;
import com.team.recycle.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    final private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signup() {
        return "members/signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDTO memberDTO, Model model) {
        try {
            Member member = new Member();
            member.setEmail(memberDTO.getEmail());
            member.setPassword(memberDTO.getPassword());
            memberService.join(member);
        } catch (IllegalStateException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            System.out.println(errorMessage);
            return "members/signup";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model) {
        try {
            Member member = new Member();
            member.setEmail(memberDTO.getEmail());
            member.setPassword(memberDTO.getPassword());
            memberService.checkMember(member);
        } catch (IllegalStateException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "members/login";
        }
        return "redirect:/";
    }

    @GetMapping("/userInfo")
    public String getUserInfo(Model model) {
        List<UserDAO> userDAOList = memberService.getUserDAOList(0, 10);
        model.addAttribute("userDAOList", userDAOList);
        return "members/userInfo";
    }

}