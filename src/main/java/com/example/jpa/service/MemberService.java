package com.example.jpa.service;

import com.example.jpa.dto.MemberDto;
import com.example.jpa.entity.MemberEntity;
import com.example.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // Spring Bean으로 등록
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(MemberDto memberDto) {
        // Controller 에서 dto 객체를 받아옴
        // repository에서는 entity 객체로 넣어줘야 함

        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출 (조건. entity객체를 넘겨줘야 함)
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        memberRepository.save(memberEntity); // jpa가 제공해주는 메서드 : save
    }
}
