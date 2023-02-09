create table app_board (
  board_id int not null,
  title varchar(255) not null,
  content text not null,
  pwd varchar(10),
  created_date datetime default now(),
  view_cnt int default 0
);

alter table app_board
  add constraint primary key (board_id),
  modify column board_id int not null auto_increment;





-- create table app_student();
-- create table app_teacher();