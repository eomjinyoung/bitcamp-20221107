-- 관리자
insert into app_member(member_id, name, email, pwd)
values (1, 'admin', 'admin@test.com', sha2('1111',256));

insert into app_teacher(member_id, degree, wage)
values (1, 0, 0);

-- 학생
insert into app_member(member_id, name, email, pwd)
values (201, 's01', 's01@test.com', sha2('1111',256));
insert into app_student(member_id, pst_no, bas_addr, det_addr, work, gender, level)
values (201, '11111', '기본주소', '상세주소', true, 'M', 0);

insert into app_member(member_id, name, email, pwd)
values (202, 's02', 's02@test.com', sha2('1111',256));
insert into app_student(member_id, pst_no, bas_addr, det_addr, work, gender, level)
values (202, '11111', '기본주소', '상세주소', true, 'M', 0);

insert into app_member(member_id, name, email, pwd)
values (203, 's03', 's03@test.com', sha2('1111',256));
insert into app_student(member_id, pst_no, bas_addr, det_addr, work, gender, level)
values (203, '11111', '기본주소', '상세주소', true, 'M', 0);

insert into app_member(member_id, name, email, pwd)
values (204, 's04', 's04@test.com', sha2('1111',256));
insert into app_student(member_id, pst_no, bas_addr, det_addr, work, gender, level)
values (204, '11111', '기본주소', '상세주소', true, 'M', 0);