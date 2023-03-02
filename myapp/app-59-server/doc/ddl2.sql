drop table if exists app_student restrict;
drop table if exists  app_teacher restrict;
drop table if exists app_member restrict;

create table app_member(
  member_id int not null,
  name varchar(50) not null,
  email varchar(50) not null,
  pwd varchar(20) not null,
  tel varchar(20),
  created_date datetime default now()
);

alter table app_member
  add constraint primary key (member_id),
  modify column member_id int not null auto_increment;

alter table app_member
  add constraint app_member_uk unique (email);
  
alter table app_member
  modify column pwd varchar(100) not null;

  
create table app_student(
  member_id int not null,
  pst_no varchar(5),
  bas_addr varchar(255),
  det_addr varchar(255),
  work boolean,
  gender char(1),
  level int
);

alter table app_student
  add constraint primary key (member_id),
  add constraint app_student_fk foreign key (member_id) references app_member (member_id);

alter table app_student
  modify column work boolean not null,
  modify column level int not null;

create table app_teacher(
  member_id int not null,
  degree int,
  school varchar(50),
  major varchar(50),
  wage int
);

alter table app_teacher
  add constraint primary key (member_id),
  add constraint app_teacher_fk foreign key (member_id) references app_member (member_id);

alter table app_teacher
  modify column degree int not null,
  modify column wage int not null;






  