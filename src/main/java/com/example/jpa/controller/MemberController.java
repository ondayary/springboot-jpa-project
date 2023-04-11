package com.example.jpa.controller;

import com.example.jpa.dto.MemberDto;
import com.example.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    // 로그인 페이지 출력 요청
    @GetMapping("member/login")
    public String loginForm() {
        return "login";
    }

    // login 정보를 받아주는 메서드
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

    // 링크를 클릭하는 페이지는 무조건 Get
    // 회원 정보 페이지 출력 요청
    @GetMapping("/member")
    public String findAll(Model model) {
        List<MemberDto> memberDtoList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model사용
        model.addAttribute("memberList", memberDtoList);
        return "list";
    }

    // 회원 상세정보 페이지 출력 요청
    @GetMapping("/member/{id}") // {}:이 경로에 있는 값을 취함
    public String findById(@PathVariable Long id, Model model) {
        // query string: RequestParam, rest api: PathVariable
        MemberDto memberDto = memberService.findById(id); // 1명이니까 Dto 타입으로 받아줌
        model.addAttribute("member", memberDto);
        return "detailList";
    }

    // 회원 정보 수정페이지 출력 요청
    @GetMapping("/member/update")
    public String updateForm(HttpSession httpSession, Model model) {
        String myEmail = (String) httpSession.getAttribute("loginEmail"); // 담을 때는 set, 가져올 때는 get
        MemberDto memberDto = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDto);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDto memberDto) {
        memberService.update(memberDto);
        return "redirect:/member/" + memberDto.getId();
        // 넘겨받은 Dto의 아이디
        // 수정이 완료된 나의 상세페이지를 띄워줌
    }
}
