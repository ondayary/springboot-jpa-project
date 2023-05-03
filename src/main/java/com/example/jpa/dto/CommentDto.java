package com.example.jpa.dto;

import com.example.jpa.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    // Entity -> Dto
    public static CommentDto toCommentDto(CommentEntity commentEntity, Long boardId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setCommentWriter(commentEntity.getCommentWriter());
        commentDto.setCommentContents(commentEntity.getCommentContents());
        commentDto.setCommentCreatedTime(commentEntity.getCreatedTime());
//        commentDto.setBoardId(commentEntity.getBoardEntity().getId()); // 사용시 Service 메서드에 @Transactional 필수
        commentDto.setBoardId(boardId);
        return commentDto;
    }
}
