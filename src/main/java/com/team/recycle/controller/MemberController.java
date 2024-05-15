package com.team.recycle.controller;

import com.team.recycle.domain.Member;
import com.team.recycle.domain.dto.MemberDTO;
import com.team.recycle.domain.dto.UserDAO;
import com.team.recycle.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;

    @Autowired
    public MemberController(BCryptPasswordEncoder bCryptPasswordEncoder, MemberService memberService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberService = memberService;
    }

    @GetMapping("/join")
    public String signup() {
        return "members/join";
    }

    @PostMapping("/join")
    public String signup(MemberDTO memberDTO, Model model) {
        try {
            Member member = new Member();
            member.setEmail(memberDTO.getEmail());
            member.setPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword()));
            memberService.join(member);
        } catch (IllegalStateException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            System.out.println(errorMessage);
            return "members/join";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "members/login";
    }

    @GetMapping("/userInfo")
    public String getUserInfo(Model model) {
        List<UserDAO> userDAOList = memberService.getUserDAOList(1, 10);
        model.addAttribute("userDAOList", userDAOList);
        return "members/userInfo";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("email", email);

        return "members/user";
    }

}