-- 관리자
insert into app_member(member_id, name, email, pwd)
values (1, 'admin', 'admin@test.com', sha2('1111',256));

insert into app_teacher(member_id, degree, wage)
values (1, 0, 0);

-- 학생
insert into app_member(member_id, name, email, pwd)
values (201, 'student', 'admin@test.com', sha2('1111',256));
