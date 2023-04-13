package com.example.jpa.controller;

import com.example.jpa.dto.BoardDto;
import com.example.jpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board") // 공통 주소 입력
public class BoardController {

    private final BoardService boardService; // DI

    // 글 작성
    @GetMapping("/write")
    public String writeForm() {
        return "write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute BoardDto boardDto) { // @RequestParam으로도 가능하지만 ModelAttribute로 진행
        System.out.println("boardDto = " + boardDto); // soutp + tab
        boardService.write(boardDto);
        return "index";
    }
}
