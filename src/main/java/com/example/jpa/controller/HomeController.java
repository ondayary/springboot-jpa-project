package com.example.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 기본페이지 요청 메서드
    @GetMapping("/") // 기본 주소를 요청했을 때 index 메서드가 실행됨
    public String index() {
        return "index"; // templates 폴더의 index.html을 찾아감
    }
}
