drop table if exists app_student restrict;
drop table if exists  app_teacher restrict;
drop table if exists app_member restrict;

create table app_member(
  member_id int not null,
  name varchar(50) not null,
  tel varchar(20),
  created_date datetime default now()
);

alter table app_member
  add constraint primary key (member_id),
  modify column member_id int not null auto_increment;


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
  

create table app_teacher(
  member_id int not null,
  email varchar(50),
  degree int,
  school varchar(50),
  major varchar(50),
  wage int
);

alter table app_teacher
  add constraint primary key (member_id),
  add constraint app_teacher_fk foreign key (member_id) references app_member (member_id);
  
  
  