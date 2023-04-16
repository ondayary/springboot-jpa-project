package com.example.jpa.service;

import com.example.jpa.dto.BoardDto;
import com.example.jpa.entity.BoardEntity;
import com.example.jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // 조회수 증가
    @Transactional // jpa에서 제공해주는 메서드가 아닌 별도로 추가된 메서드를 쓰는 경우에는 트랜잭션을 붙여줌(영속성 컨텍스트 처리)
    public void updateHits(Long id) {
        // 조회수 같이 특수한 쿼리들은 별도의 메서드를 정의할 필요가 있음
        boardRepository.updateHits(id);
    }

    public BoardDto findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDto boardDto = BoardDto.toBoardDto(boardEntity);
            return boardDto;
        } else {
            return null;
        }
    }

    public BoardDto update(BoardDto boardDto) {
        // repository에서 update를 위한 메서드는 제공되고 있지 않음
        // save 메서드를 가지고 insert, update 두 가지를 이용하는데
        // 구분하는 방법은 id 값이 있냐 없냐 id 값의 유무임

        // entity로 변환하는 과정 필요
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto);
        boardRepository.save(boardEntity);
        return findById(boardDto.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
