package com.example.jpa.repository;

import com.example.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // interface이기 때문에 추상메서드가 정의된다.
    // Optional은 java.util에서 제공하는 null을 방지하는 클래스
    // repository에서 주고받는 객체는 다 Entity 객체여야 함

    // 이메일로 회원 정보 조회 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
