package com.example.jpa.repository;

import com.example.jpa.entity.BoardEntity;
import com.example.jpa.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? : findAllByBoardEntity
    // order by id desc; : OrderByIdDesc
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
