package com.example.jpa.service;

import com.example.jpa.dto.BoardDto;
import com.example.jpa.entity.BoardEntity;
import com.example.jpa.entity.BoardFileEntity;
import com.example.jpa.repository.BoardFileRepository;
import com.example.jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository; // 생성자 주입
    private final BoardFileRepository boardFileRepository;

    public void write(BoardDto boardDto) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDto.getBoardFile().isEmpty()) {
            // 첨부 파일 없음
            BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDto);
            boardRepository.save(boardEntity); // insert query가 나가게 됨
            // save(): 매개변수를 entity 클래스로, return할 때도 entity로 주게 되어있음
        } else {
            // 첨부 파일 있음
            /*
                1. Dto에 담긴 파일을 꺼냄
                2. 파일의 이름을 가져옴
                3. 서버 저장용 이름을 추가
                4. 저장 경로 설정
                5. 해당 경로에 파일을 저장
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
            */
            MultipartFile boardFile = boardDto.getBoardFile(); // 1
            String originalFileName = boardFile.getOriginalFilename(); // 2
            String storedFilName = System.currentTimeMillis() + "_" + originalFileName; // 3
            String savePath = "/Users/daon/img/" + storedFilName; // 4 (mac기준)
            boardFile.transferTo(new File(savePath)); // 5

            // DB에 저장하는 작업
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto);
            Long savedId = boardRepository.save(boardEntity).getId(); // Entity를 저장하고 나서 id값을 얻어옴
            BoardEntity board = boardRepository.findById(savedId).get(); // DB에 저장하고 난 후의 id값을 얻어옴

            // BoardFileEntity로 변환하기 위한 작업
            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFileName, storedFilName);
            boardFileRepository.save(boardFileEntity);
        }
    }

    @Transactional // toBoardDto에서 BoardFileEntity를 접근하고 있기 때문에 붙여줘야 함
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

    @Transactional // toBoardDto에서 BoardFileEntity를 접근하고 있기 때문에 붙여줘야 함
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

    public Page<BoardDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; // 몇 페이지를 보고싶은지
        int pageLimit = 3; // 한 페이지당 몇 개의 게시물을 볼건지
        // 한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작하기 때문에 1개가 빠진 값이 요청이 되어야 한다. (사용자 7 DB 6)
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDto> boardDtos = boardEntities.map(board -> new BoardDto(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDtos;
    }
}
