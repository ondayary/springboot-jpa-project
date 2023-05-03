package com.example.jpa.controller;

import com.example.jpa.dto.CommentDto;
import com.example.jpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity write(@ModelAttribute CommentDto commentDto) { // ajax요청이라 ResponseBody가 필요함
        System.out.println("commentDto = " + commentDto);
        Long saveResult = commentService.save(commentDto);
        if (saveResult != null) {
            // 작성 성공시 댓글 목록을 가져와서 리턴
            // 댓글 목록: 해당 게시글의 댓글 전체
            List<CommentDto> commentDtoList = commentService.findAll(commentDto.getBoardId());
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
