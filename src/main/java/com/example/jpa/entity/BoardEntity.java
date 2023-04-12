package com.example.jpa.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
}
