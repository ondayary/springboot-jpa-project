package com.example.jpa.controller;

import com.example.jpa.dto.CommentDto;
import com.example.jpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/write")
    public @ResponseBody String write(@ModelAttribute CommentDto commentDto) { // ajax요청이라 ResponseBody가 필요함
        System.out.println("commentDto = " + commentDto);
        return "요청 성공";
    }
}
