package com.example.jpa.service;

import com.example.jpa.dto.BoardDto;
import com.example.jpa.entity.BoardEntity;
import com.example.jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository; // 생성자 주입

    public void write(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDto);
        boardRepository.save(boardEntity); // insert query가 나가게 됨
        // save(): 매개변수를 entity 클래스로, return할 때도 entity로 주게 되어있음
    }

    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        // findAll하게 되면 대부분 Entity로 가져온다. 여기서는 Entity가 하나가 아니라 List형태의 Entity가 넘어 온다.
        // Entity로 넘어온 이 객체를 Dto객체로 옮겨담아서 다시 Controller로 리턴해줘야 한다.

        // 1. 리턴할 객체를 선언한다.
        List<BoardDto> boardDtoList = new ArrayList<>();
        // 2. boardEntityList에 담긴 내용을 boardDtoList에 담아야 한다.
        // 3. Entity객체를 Dto객체로 옮겨담는다. -> Dto클래스에서 생성

        for (BoardEntity boardEntity : boardEntityList) {
            boardDtoList.add(BoardDto.toBoardDto(boardEntity));
            // 반복문으로 접근하는 Entity 객체(boardEntity)를 Dto로 변환하고 변환된 객체를 boardDtoList에 넣는다.
        }
        return boardDtoList;
    }
}
