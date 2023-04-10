package com.example.jpa.controller;

import com.example.jpa.dto.MemberDto;
import com.example.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

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
        memberService.join(memberDto);
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session) {
        MemberDto loginResult = memberService.login(memberDto);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail()); // 로그인한 이메일 정보를 session에 담아줌
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }
}
