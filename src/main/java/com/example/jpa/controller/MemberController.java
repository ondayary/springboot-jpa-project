package com.example.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    // 회원가입 페이지 출력 요청
    @GetMapping("/member/join")
    public String joinForm() {
        return "join";
    }

    // join 정보를 받아주는 메서드
    @PostMapping("/member/join")
    public String join(@RequestParam("memberEmail") String memberEmail,
                       @RequestParam("memberPassword") String memberPassword,
                       @RequestParam("memberName") String memberName) {
        // RequestParam을 이용해서 ("")안에 name 값을 적어준다.
        // name에 담은 값을 memberEmail에 옮겨담는다.
        System.out.println("memberEmail = " + memberEmail + ", " + "memberPassword = " + memberPassword + ", memberName = " + memberName);
        return "index";
    }
}
