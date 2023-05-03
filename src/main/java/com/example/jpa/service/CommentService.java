package com.example.jpa.service;

import com.example.jpa.dto.CommentDto;
import com.example.jpa.entity.BoardEntity;
import com.example.jpa.entity.CommentEntity;
import com.example.jpa.repository.BoardRepository;
import com.example.jpa.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDto commentDto) {
        // 부모엔티티(BoardEntity) 먼저 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
        if (optionalBoardEntity.isPresent()) { // 부모엔티티가 조회가 된다면 댓글 저장처리를 출
            BoardEntity boardEntity = optionalBoardEntity.get();
            // Dto로 받아온 것을 Entity로 변환하는 작업이 필요
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto, boardEntity);
            // 변환해 온 것을 저장하는 단계
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDto> findAll(Long boardId) {
        // select * from comment_table where board_id=? order by id desc;
        BoardEntity boardEntity = boardRepository.findById(boardId).get(); // 부모엔티티 조회하는 부분
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        // EntityList -> DtoList로 변경
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            CommentDto commentDto = CommentDto.toCommentDto(commentEntity, boardId);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}
