package com.example.jpa.repository;

import com.example.jpa.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> { // Entity 클래스만 받아줌
    // update board_table set board_hits=board_hits+1 whre id=?
    // 해당 게시물에 대해 기존의 조회수에서 하나를 올리는 쿼리

    @Modifying // @Query 어노테이션을 통해 작성된 insert, update, delete 쿼리에서 사용되는 어노테이션
    @Query(value = "update BoardEntity be set be.boardHits=be.boardHits+1 where be.id=:id")
    void updateHits(@Param("id") Long id);
}
