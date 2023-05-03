package com.example.jpa.entity;

import com.example.jpa.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    // board의 자식이기 때문에 Board:Comment = 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static CommentEntity toSaveEntity(CommentDto commentDto, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity(); // 내부적으로 쓰기위해 호출
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setCommentContents(commentDto.getCommentContents());
        commentEntity.setBoardEntity(boardEntity); // 부모게시글 번호로 조회한 엔티티값 호출
        return commentEntity;
    }
}
