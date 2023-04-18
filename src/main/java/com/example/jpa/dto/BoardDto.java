package com.example.jpa.dto;

import com.example.jpa.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

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
        return boardDto;
    }
}
