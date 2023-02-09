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


create table app_student(
  student_id int not null,
  name varchar(50) not null,
  tel varchar(20),
  created_date datetime default now(),
  pst_no varchar(5),
  bas_addr varchar(255),
  det_addr varchar(255),
  work boolean,
  gender char(1),
  level int
);

alter table app_student
  add constraint primary key (student_id),
  modify column student_id int not null auto_increment;

  
create table app_teacher(
  teacher_id int not null,
  name varchar(50) not null,
  tel varchar(20),
  created_date datetime default now(),
  email varchar(50),
  degree int,
  school varchar(50),
  major varchar(50),
  wage int
);

alter table app_teacher
  add constraint primary key (teacher_id),
  modify column teacher_id int not null auto_increment;
  
  
  