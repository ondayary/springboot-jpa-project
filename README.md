<div align="center">
  <h3>SpringBoot + mysql + Spring Data JPA를 활용한 프로젝트</h3>
</div>

<br>

## 개발환경
프로젝트 개발 환경은 다음과 같습니다.
- IDE: IntelliJ IDEA Ultimate
- Spring Boot 2.7.5
- JDK 11
- mysql
- Spring Data JPA
- Thymeleaf

<br>

## 회원 주요기능
1. 회원가입(/member/join)
2. 로그인(/member/login)
3. 회원목록(/member) 
4. 회원조회(/member/{id}) 
5. 회원정보 수정(/member/update) 
6. 회원삭제(/member/delete/{id}) 
7. 로그아웃(/member/logout)
8. 이메일(아이디) 중복체크(/member/email-check)

<br>

## 게시판 주요기능
1. 글쓰기(/board/write)
2. 글목록(/board/list)
3. 글조회(/board/{id})
4. 글수정(/board/update/{id})
    - 상세화면에서 수정 버튼 클릭
    - 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
    - 제목, 내용 수정 입력 받아서 서버로 요청
    - 수정 처리
5. 글삭제(/board/delete/{id})
6. 페이징처리(/board/paging)
    - /board/paging?page=2
    - /board/paging/2
7. 파일(이미지)첨부하기
    - 단일 파일 첨부
    - 다중 파일 첨부
    - 파일 첨부와 관련하여 추가된 부분들
        - /boards/write.html
        - BoardDto
        - BoardService.write()
        - BoardEntity
        - BoardFileEntity, BoardFileRepository 추가
        - /boards/detailList.html
    - board_table(부모) - board_file_table(자식)