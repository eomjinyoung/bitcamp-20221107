-- 게시글 작성자의 번호를 저장하는 컬럼 추가한다.
-- 작성자 번호는 app_member 테이블의 PK 컬럼을 참조하는 FK로 만든다.
alter table app_board
  add column writer int,
  add constraint app_board_fk foreign key (writer) references app_member(member_id);


  