-- 게시글 첨부파일 정보를 저장하는 테이블 정의
create table app_board_file (
  boardfile_id int not null,
  filepath varchar(255) not null,
  origin_filename varchar(255) not null,
  mime_type varchar(30) not null,
  board_id int not null
);

alter table app_board_file
  add constraint primary key (boardfile_id),
  modify column boardfile_id int not null auto_increment;
  
alter table app_board_file
  add constraint app_board_file_fk foreign key (board_id) references app_board (board_id);

  