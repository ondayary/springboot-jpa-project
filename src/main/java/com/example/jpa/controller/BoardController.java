package com.example.jpa.controller;

import com.example.jpa.dto.BoardDto;
import com.example.jpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board") // 공통 주소 입력
public class BoardController {

    private final BoardService boardService; // DI

    // 글 작성
    @GetMapping("/write")
    public String writeForm() {
        return "boards/write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute BoardDto boardDto) { // @RequestParam으로도 가능하지만 ModelAttribute로 진행
        System.out.println("boardDto = " + boardDto); // soutp + tab
        boardService.write(boardDto);
        return "index";
    }

    // 글 목록
    @GetMapping("/list")
    public String findAll(Model model) { // 전체 목록을 DB로부터 받아와야 할 때는 model 객체를 사용함
        List<BoardDto> boardDtoList = boardService.findAll(); // 여러개를 가져올 때는 List 사용
        model.addAttribute("boardList", boardDtoList); // 가져온 객체를 model에 담음
        return "boards/list";
        // service에서 boardDtoList에 리턴해온 값을 model에 담아 boardList.html에 보여준다.
    }
}
