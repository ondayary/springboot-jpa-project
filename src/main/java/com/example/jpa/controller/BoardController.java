package com.example.jpa.controller;

import com.example.jpa.dto.BoardDto;
import com.example.jpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board") // 공통 주소 입력
public class BoardController {

    private final BoardService boardService; // DI

    // 게시글 작성
    @GetMapping("/write")
    public String writeForm() {
        return "boards/write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute BoardDto boardDto) throws IOException { // @RequestParam으로도 가능하지만 ModelAttribute로 진행
        System.out.println("boardDto = " + boardDto); // soutp + tab
        boardService.write(boardDto);
        return "index";
    }

    // 게시글 목록
    @GetMapping("/list")
    public String findAll(Model model) { // 전체 목록을 DB로부터 받아와야 할 때는 model 객체를 사용함
        List<BoardDto> boardDtoList = boardService.findAll(); // 여러개를 가져올 때는 List 사용
        model.addAttribute("boardList", boardDtoList); // 가져온 객체를 model에 담음
        return "boards/list";
        // service에서 boardDtoList에 리턴해온 값을 model에 담아 boardList.html에 보여준다.
    }

    // 게시글 한개 조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) { // @PathVariable: 경로상에 있는 값을 가져올시
        // 1. 해당 게시글의 조회수를 하나 올리고
        // 2. 게시글 데이터를 가져와서 detailList.html에 출력
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);
        // model 객체를 board라는 파라미터에 담아서 detailList.html로 보낸다.
        model.addAttribute("page", pageable.getPageNumber());
        return "boards/detailList";
    }

    // 게시글 수정
    /*
        1. 상세화면에서 수정 버튼 클릭
        2. 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
        3. 수정된 제목, 내용을 입력 받아 서버로 요청
        4. 수정 처리
     */
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "boards/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model) {
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);
        // service에서 수정이 끝난 내용이 board에 반영이 되고 그 객체를 가지고 model에 넣어 detailList로 간다.
        return "boards/detailList";
//        return "redirect:/board" + boardDto.getId(); // 조회수 증가가 될 우려
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }

    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) { // RequestParam 이용해도 됨
//        pageable.getPageNumber();
        Page<BoardDto> boardList = boardService.paging(pageable);

        int blockLimit = 3; // 페이지 3개를 보여줄 변수값
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boards/paging";

    }
}
