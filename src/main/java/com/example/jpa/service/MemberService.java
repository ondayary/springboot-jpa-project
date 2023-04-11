package com.example.jpa.service;

import com.example.jpa.dto.MemberDto;
import com.example.jpa.entity.MemberEntity;
import com.example.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public MemberDto login(MemberDto memberDto) {
        // 1. 회원이 입력한 이메일로 DB에서 조회
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDto.getMemberEmail());

        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDto.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                MemberDto dto = MemberDto.toMemberDto(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인 실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원 정보가 없다)
            return null;
        }
    }

    public List<MemberDto> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        // repository와 관련된 것은 무조건 entity 객체로 주고 받음
        // EntityList객체를 DtoList로 변환해서 Controller로 보내줘야 함
        List<MemberDto> memberDtoList = new ArrayList<>();
        // Entity가 여러개 담긴 List객체를 Dto가 여러개 담긴 List객체로 옮겨담는 것
        // 그래서 memberEntityList를 하나하나 memberDtoList에 옮겨담는 과정이 필요 -> for문
        for (MemberEntity memberEntity : memberEntityList) {
            memberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }
        return memberDtoList;
    }
}
