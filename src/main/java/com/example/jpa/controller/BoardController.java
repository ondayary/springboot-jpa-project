package com.example.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board") // 공통 주소 입력
public class BoardController {

    // 글 작성
    @GetMapping("/write")
    public String writeForm() {
        return "write";
    }
}
