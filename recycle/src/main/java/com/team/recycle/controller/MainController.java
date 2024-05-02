package com.team.recycle.controller;

import com.team.recycle.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public String main() {
        return "main";
    }
}
