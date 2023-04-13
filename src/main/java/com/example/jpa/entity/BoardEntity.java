package com.example.jpa.entity;

import com.example.jpa.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity { // DB의 테이블 역할을 하는 클래스
    @Id // pk 컬럼 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // default: 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    // DTO -> Entity
    public static BoardEntity toBoardEntity(BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();
        // dto에 담긴 값들을 entity 객체로 옮겨담는 작업
        boardEntity.setBoardWriter(boardEntity.getBoardWriter());
        boardEntity.setBoardPass(boardDto.getBoardPass());
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContents(boardDto.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;
    }
}
