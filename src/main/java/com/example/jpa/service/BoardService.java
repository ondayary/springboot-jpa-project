package com.example.jpa.service;

import com.example.jpa.dto.BoardDto;
import com.example.jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository; // 생성자 주입

    public void write(BoardDto boardDto) {
        // save(): 매개변수를 entity 클래스로, return 할 때도 entity로 주게 되어있음
    }
}
