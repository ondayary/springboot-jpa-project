package com.example.jpa.dto;

import com.example.jpa.entity.BoardEntity;
import com.example.jpa.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto { // DTO(Data Transfer Object)
    private Long id; // 글 번호
    private String boardWriter; // 작성자
    private String boardPass; // 비밀번호
    private String boardTitle; // 제목
    private String boardContents; // 내용
    private int boardHits; // 조회수
    private LocalDateTime boardCreatedTime; // 작성시간
    private LocalDateTime boardUpdatedTime; // 수정시간

    // 파일 첨부 관련
    private List<MultipartFile> boardFile; // multifile이라는 interface, write.html -> Contoller 파일 담는 용도
                                           // 단일 첨부에서 다중 첨부로 변하면 List<MultipartFile>로 변경
    private List<String> originalFileName; // 원본 파일 이름
                                           // 파일이 여러개로 변경되어서 List로 선언해야 함
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    // 페이징 처리로 인해 페이지의 원하는 목록들만 보여주는 생성자 추가
    public BoardDto(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    // Entity -> DTO
    public static BoardDto toBoardDto(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(boardEntity.getId());
        boardDto.setBoardWriter(boardEntity.getBoardWriter());
        boardDto.setBoardPass(boardEntity.getBoardPass());
        boardDto.setBoardTitle(boardEntity.getBoardTitle());
        boardDto.setBoardContents(boardEntity.getBoardContents());
        boardDto.setBoardHits(boardEntity.getBoardHits());
        boardDto.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDto.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        if (boardEntity.getFileAttached() == 0) {
            boardDto.setFileAttached(boardEntity.getFileAttached()); // 0
        } else { // 파일이 있는 경우
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            boardDto.setFileAttached(boardEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 저장 경로에 있는 파일을 화면에 보여줄 수 있음
            // 현재 originalFileName과 storedFileName은 board_file_table(BoardFileEntity)안에 들어있음
            // 그런데 Dto 변환을 위해 Entity객체만 가져옴
            // 연관관계를 맺어놨기 때문에 join문법을 써서 가져올 수 있음
            // select * from board_table b, board_file_table bf where b.id=bf.board_id and where b.id=?
            // boardDto.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            // boardDto.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());

            // 다중 첨부로 변경하여 윗 두줄을 반복문으로 변경
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDto.setOriginalFileName(originalFileNameList);
            boardDto.setStoredFileName(storedFileNameList);
        }
        return boardDto;
    }
}
