package com.example.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    // 게시글 하나의 여러개의 첨부 파일 1 : N
    // BoardFile 기준에서는 N : 1
    @ManyToOne(fetch = FetchType.LAZY) // EAGER: 부모 조회시 자식도 조회, LAZY: 필요한 상황에 호출해서 사용
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static BoardFileEntity toBoardFileEntity(BoardEntity boardEntity, String originalFileName, String storedFileName) {
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setStoredFileName(storedFileName);
        boardFileEntity.setBoardEntity(boardEntity); // pk 값이 아니라 부모 entity 객체를 넘겨줘야 함
        return boardFileEntity;
    }
}
