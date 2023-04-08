package com.example.jpa.controller;

import com.example.jpa.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String join(@ModelAttribute MemberDto memberDto) {
        // name과 dto의 필드가 동일하다면 dto객체를 만들어서 객체의 setter메서드를 호출해 알아서 담아줌
        System.out.println("memberDto = " + memberDto);
        return "index";
    }
}
