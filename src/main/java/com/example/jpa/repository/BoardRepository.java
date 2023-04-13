package com.example.jpa.repository;

import com.example.jpa.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> { // Entity 클래스만 받아줌

}
