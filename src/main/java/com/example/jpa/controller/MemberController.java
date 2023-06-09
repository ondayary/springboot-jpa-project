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
        return "members/join";
    }

    // join 정보를 받아주는 메서드
    @PostMapping("/member/join")
    public String join(@ModelAttribute MemberDto memberDto) {
        // name과 dto의 필드가 동일하다면 dto객체를 만들어서 객체의 setter메서드를 호출해 알아서 담아줌
        System.out.println("memberDto = " + memberDto);
        memberService.join(memberDto);
        return "members/login";
    }

    // 로그인 페이지 출력 요청
    @GetMapping("member/login")
    public String loginForm() {
        return "members/login";
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
            return "members/login";
        }
    }

    // 링크를 클릭하는 페이지는 무조건 Get
    // 회원 정보 페이지 출력 요청
    @GetMapping("/member")
    public String findAll(Model model) {
        List<MemberDto> memberDtoList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model사용
        model.addAttribute("memberList", memberDtoList);
        return "members/list";
    }

    // 회원 상세정보 페이지 출력 요청
    @GetMapping("/member/{id}") // {}:이 경로에 있는 값을 취함
    public String findById(@PathVariable Long id, Model model) {
        // query string: RequestParam, rest api: PathVariable
        MemberDto memberDto = memberService.findById(id); // 1명이니까 Dto 타입으로 받아줌
        model.addAttribute("member", memberDto);
        return "members/detailList";
    }

    // 회원 정보 수정페이지 출력 요청
    @GetMapping("/member/update")
    public String updateForm(HttpSession httpSession, Model model) {
        String myEmail = (String) httpSession.getAttribute("loginEmail"); // 담을 때는 set, 가져올 때는 get
        MemberDto memberDto = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDto);
        return "members/update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDto memberDto) {
        memberService.update(memberDto);
        return "redirect:/member/" + memberDto.getId();
        // 넘겨받은 Dto의 아이디
        // 수정이 완료된 나의 상세페이지를 띄워줌
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
        // 삭제처리하고 list페이지로 가려했으나 list로 갈 때에는 model에 데이터를 담아서 가기 때문에
        // redirect로 list페이지로 이동한다.
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate(); // session을 무효화함
        return "index";
    }

    // 이메일 중복 체크
    @ResponseBody
    @PostMapping("/member/email-check")
    public String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail: " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
        // checkResult로 return 해도 되는 이유
        // service에서 사용이 가능하면 ok, 아니라면 null 값을 반환하기로 했기 때문
    }
}
