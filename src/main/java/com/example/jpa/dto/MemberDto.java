package com.example.jpa.dto;

import com.example.jpa.entity.MemberEntity;
import lombok.*;

// lombok 라이브러리를 이용해서 간접적으로 이용하게끔 함
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto { // 회원정보 정리
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDto toMemberDto(MemberEntity memberEntity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setMemberEmail(memberEntity.getMemberEmail());
        memberDto.setMemberPassword(memberEntity.getMemberPassword());
        memberDto.setMemberName(memberEntity.getMemberName());
        return memberDto;
    }
}
