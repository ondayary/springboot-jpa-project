package com.example.jpa.dto;

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
}
