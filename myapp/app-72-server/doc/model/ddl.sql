-- 강의
DROP TABLE IF EXISTS lms_lecture RESTRICT;

-- 학생
DROP TABLE IF EXISTS lms_student RESTRICT;

-- 강사
DROP TABLE IF EXISTS lms_teacher RESTRICT;

-- 매니저
DROP TABLE IF EXISTS lms_manager RESTRICT;

-- 강의실
DROP TABLE IF EXISTS lms_class RESTRICT;

-- 회원
DROP TABLE IF EXISTS lms_member RESTRICT;

-- 교육센터
DROP TABLE IF EXISTS lms_edu_center RESTRICT;

-- 부서
DROP TABLE IF EXISTS lms_department RESTRICT;

-- 학력
DROP TABLE IF EXISTS lms_degree RESTRICT;

-- 강의실사진
DROP TABLE IF EXISTS lms_class_photo RESTRICT;

-- 기본주소
DROP TABLE IF EXISTS lms_addr RESTRICT;

-- 수강신청
DROP TABLE IF EXISTS lms_application RESTRICT;

-- 강의배정
DROP TABLE IF EXISTS lms_lecture_teacher RESTRICT;

-- 신청상태
DROP TABLE IF EXISTS lms_application_state RESTRICT;

-- 강의
CREATE TABLE lms_lecture (
  lecture_id INTEGER      NOT NULL COMMENT '강의번호', -- 강의번호
  title      VARCHAR(255) NOT NULL COMMENT '강의명', -- 강의명
  content    LONGTEXT     NOT NULL COMMENT '내용', -- 내용
  start_dt   DATE         NOT NULL COMMENT '시작일', -- 시작일
  end_dt     DATE         NOT NULL COMMENT '종료일', -- 종료일
  price      INTEGER      NOT NULL COMMENT '강의료', -- 강의료
  hours      INTEGER      NOT NULL COMMENT '총강의시간', -- 총강의시간
  capacity   INTEGER      NOT NULL COMMENT '모집인원', -- 모집인원
  manager_id INTEGER      NULL     COMMENT '매니저번호', -- 매니저번호
  class_id   INTEGER      NULL     COMMENT '강의실번호' -- 강의실번호
)
COMMENT '강의';

-- 강의
ALTER TABLE lms_lecture
  ADD CONSTRAINT PK_lms_lecture -- 강의 기본키
  PRIMARY KEY (
  lecture_id -- 강의번호
  );

-- 강의 인덱스
CREATE INDEX IX_lms_lecture
  ON lms_lecture( -- 강의
    title ASC -- 강의명
  );

ALTER TABLE lms_lecture
  MODIFY COLUMN lecture_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의번호';

-- 학생
CREATE TABLE lms_student (
  student_id INTEGER NOT NULL COMMENT '학생번호', -- 학생번호
  working    INTEGER NOT NULL COMMENT '재직여부', -- 재직여부
  birthday   CHAR(6) NOT NULL COMMENT '생년월일' -- 생년월일
)
COMMENT '학생';

-- 학생
ALTER TABLE lms_student
  ADD CONSTRAINT PK_lms_student -- 학생 기본키
  PRIMARY KEY (
  student_id -- 학생번호
  );

-- 강사
CREATE TABLE lms_teacher (
  teacher_id INTEGER NOT NULL COMMENT '강사번호', -- 강사번호
  fulltime   INTEGER NOT NULL COMMENT '전임여부', -- 전임여부
  wage       INTEGER NOT NULL COMMENT '시강료' -- 시강료
)
COMMENT '강사';

-- 강사
ALTER TABLE lms_teacher
  ADD CONSTRAINT PK_lms_teacher -- 강사 기본키
  PRIMARY KEY (
  teacher_id -- 강사번호
  );

-- 매니저
CREATE TABLE lms_manager (
  manager_id    INTEGER     NOT NULL COMMENT '매니저번호', -- 매니저번호
  department_id INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
  fax           VARCHAR(30) NULL     COMMENT '팩스', -- 팩스
  position      VARCHAR(60) NULL     COMMENT '직위' -- 직위
)
COMMENT '매니저';

-- 매니저
ALTER TABLE lms_manager
  ADD CONSTRAINT PK_lms_manager -- 매니저 기본키
  PRIMARY KEY (
  manager_id -- 매니저번호
  );

-- 강의실
CREATE TABLE lms_class (
  class_id      INTEGER     NOT NULL COMMENT '강의실번호', -- 강의실번호
  edu_center_id INTEGER     NOT NULL COMMENT '교육센터번호', -- 교육센터번호
  name          VARCHAR(60) NOT NULL COMMENT '강의실명', -- 강의실명
  capacity      INTEGER     NOT NULL COMMENT '수용인원' -- 수용인원
)
COMMENT '강의실';

-- 강의실
ALTER TABLE lms_class
  ADD CONSTRAINT PK_lms_class -- 강의실 기본키
  PRIMARY KEY (
  class_id -- 강의실번호
  );

-- 강의실 인덱스
CREATE INDEX IX_lms_class
  ON lms_class( -- 강의실
    name ASC -- 강의실명
  );

ALTER TABLE lms_class
  MODIFY COLUMN class_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의실번호';

-- 회원
CREATE TABLE lms_member (
  member_id      INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
  name           VARCHAR(60)  NOT NULL COMMENT '이름', -- 이름
  tel            VARCHAR(30)  NOT NULL COMMENT '전화', -- 전화
  email          VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
  addr_id        INTEGER      NULL     COMMENT '기본주소번호', -- 기본주소번호
  detail_address VARCHAR(255) NULL     COMMENT '상세주소', -- 상세주소
  degree_id      INTEGER      NOT NULL COMMENT '학력번호', -- 학력번호
  school         VARCHAR(60)  NULL     COMMENT '학교', -- 학교
  major          VARCHAR(60)  NOT NULL COMMENT '전공' -- 전공
)
COMMENT '회원';

-- 회원
ALTER TABLE lms_member
  ADD CONSTRAINT PK_lms_member -- 회원 기본키
  PRIMARY KEY (
  member_id -- 회원번호
  );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_lms_member
  ON lms_member ( -- 회원
    email ASC -- 이메일
  );

-- 회원 인덱스
CREATE INDEX IX_lms_member
  ON lms_member( -- 회원
    name ASC -- 이름
  );

ALTER TABLE lms_member
  MODIFY COLUMN member_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 교육센터
CREATE TABLE lms_edu_center (
  edu_center_id  INTEGER      NOT NULL COMMENT '교육센터번호', -- 교육센터번호
  name           VARCHAR(60)  NOT NULL COMMENT '센터명', -- 센터명
  addr_id        INTEGER      NOT NULL COMMENT '기본주소번호', -- 기본주소번호
  detail_address VARCHAR(255) NOT NULL COMMENT '센터상세주소', -- 센터상세주소
  tel            VARCHAR(30)  NOT NULL COMMENT '센터전화', -- 센터전화
  fax            VARCHAR(30)  NULL     COMMENT '센터팩스' -- 센터팩스
)
COMMENT '교육센터';

-- 교육센터
ALTER TABLE lms_edu_center
  ADD CONSTRAINT PK_lms_edu_center -- 교육센터 기본키
  PRIMARY KEY (
  edu_center_id -- 교육센터번호
  );

-- 교육센터 유니크 인덱스
CREATE UNIQUE INDEX UIX_lms_edu_center
  ON lms_edu_center ( -- 교육센터
    name ASC -- 센터명
  );

ALTER TABLE lms_edu_center
  MODIFY COLUMN edu_center_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '교육센터번호';

-- 부서
CREATE TABLE lms_department (
  department_id INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
  dept_name     VARCHAR(60) NOT NULL COMMENT '부서명' -- 부서명
)
COMMENT '부서';

-- 부서
ALTER TABLE lms_department
  ADD CONSTRAINT PK_lms_department -- 부서 기본키
  PRIMARY KEY (
  department_id -- 부서번호
  );

-- 부서 유니크 인덱스
CREATE UNIQUE INDEX UIX_lms_department
  ON lms_department ( -- 부서
    dept_name ASC -- 부서명
  );

ALTER TABLE lms_department
  MODIFY COLUMN department_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '부서번호';

-- 학력
CREATE TABLE lms_degree (
  degree_id INTEGER     NOT NULL COMMENT '학력번호', -- 학력번호
  degr_name VARCHAR(60) NOT NULL COMMENT '최종학력' -- 최종학력
)
COMMENT '학력';

-- 학력
ALTER TABLE lms_degree
  ADD CONSTRAINT PK_lms_degree -- 학력 기본키
  PRIMARY KEY (
  degree_id -- 학력번호
  );

-- 학력 유니크 인덱스
CREATE UNIQUE INDEX UIX_lms_degree
  ON lms_degree ( -- 학력
    degr_name ASC -- 최종학력
  );

ALTER TABLE lms_degree
  MODIFY COLUMN degree_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '학력번호';

-- 강의실사진
CREATE TABLE lms_class_photo (
  class_photo_id  INTEGER      NOT NULL COMMENT '강의실사진번호', -- 강의실사진번호
  photo_path      VARCHAR(255) NOT NULL COMMENT '사진', -- 사진
  origin_filename VARCHAR(60)  NOT NULL COMMENT '원래사진파일명', -- 원래사진파일명
  mimetype        VARCHAR(60)  NOT NULL COMMENT 'MIMETYPE', -- MIMETYPE
  class_id        INTEGER      NOT NULL COMMENT '강의실번호' -- 강의실번호
)
COMMENT '강의실사진';

-- 강의실사진
ALTER TABLE lms_class_photo
  ADD CONSTRAINT PK_lms_class_photo -- 강의실사진 기본키
  PRIMARY KEY (
  class_photo_id -- 강의실사진번호
  );

ALTER TABLE lms_class_photo
  MODIFY COLUMN class_photo_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의실사진번호';

-- 기본주소
CREATE TABLE lms_addr (
  addr_id       INTEGER      NOT NULL COMMENT '기본주소번호', -- 기본주소번호
  postno        VARCHAR(10)  NOT NULL COMMENT '우편번호', -- 우편번호
  basic_address VARCHAR(255) NOT NULL COMMENT '기본주소' -- 기본주소
)
COMMENT '기본주소';

-- 기본주소
ALTER TABLE lms_addr
  ADD CONSTRAINT PK_lms_addr -- 기본주소 기본키
  PRIMARY KEY (
  addr_id -- 기본주소번호
  );

-- 기본주소 인덱스
CREATE INDEX IX_lms_addr
  ON lms_addr( -- 기본주소
    postno ASC -- 우편번호
  );

ALTER TABLE lms_addr
  MODIFY COLUMN addr_id INTEGER NOT NULL AUTO_INCREMENT COMMENT '기본주소번호';

-- 수강신청
CREATE TABLE lms_application (
  lecture_id           INTEGER     NOT NULL COMMENT '강의번호', -- 강의번호
  student_id           INTEGER     NOT NULL COMMENT '학생번호', -- 학생번호
  created_dt           DATETIME    NOT NULL DEFAULT now() COMMENT '신청일', -- 신청일
  application_state_id VARCHAR(10) NULL     COMMENT '신청상태번호' -- 신청상태번호
)
COMMENT '수강신청';

-- 수강신청
ALTER TABLE lms_application
  ADD CONSTRAINT PK_lms_application -- 수강신청 기본키
  PRIMARY KEY (
  lecture_id, -- 강의번호
  student_id  -- 학생번호
  );

-- 강의배정
CREATE TABLE lms_lecture_teacher (
  teacher_id INTEGER NOT NULL COMMENT '강사번호', -- 강사번호
  lecture_id INTEGER NOT NULL COMMENT '강의번호' -- 강의번호
)
COMMENT '강의배정';

-- 강의배정
ALTER TABLE lms_lecture_teacher
  ADD CONSTRAINT PK_lms_lecture_teacher -- 강의배정 기본키
  PRIMARY KEY (
  teacher_id, -- 강사번호
  lecture_id  -- 강의번호
  );

-- 신청상태
CREATE TABLE lms_application_state (
  application_state_id VARCHAR(10) NOT NULL COMMENT '신청상태번호', -- 신청상태번호
  status_name          VARCHAR(60) NULL     COMMENT '상태명' -- 상태명
)
COMMENT '신청상태';

-- 신청상태
ALTER TABLE lms_application_state
  ADD CONSTRAINT PK_lms_application_state -- 신청상태 기본키
  PRIMARY KEY (
  application_state_id -- 신청상태번호
  );

-- 강의
ALTER TABLE lms_lecture
  ADD CONSTRAINT FK_lms_manager_TO_lms_lecture -- 매니저 -> 강의
  FOREIGN KEY (
  manager_id -- 매니저번호
  )
  REFERENCES lms_manager ( -- 매니저
  manager_id -- 매니저번호
  );

-- 강의
ALTER TABLE lms_lecture
  ADD CONSTRAINT FK_lms_class_TO_lms_lecture -- 강의실 -> 강의
  FOREIGN KEY (
  class_id -- 강의실번호
  )
  REFERENCES lms_class ( -- 강의실
  class_id -- 강의실번호
  );

-- 학생
ALTER TABLE lms_student
  ADD CONSTRAINT FK_lms_member_TO_lms_student -- 회원 -> 학생
  FOREIGN KEY (
  student_id -- 학생번호
  )
  REFERENCES lms_member ( -- 회원
  member_id -- 회원번호
  );

-- 강사
ALTER TABLE lms_teacher
  ADD CONSTRAINT FK_lms_member_TO_lms_teacher -- 회원 -> 강사
  FOREIGN KEY (
  teacher_id -- 강사번호
  )
  REFERENCES lms_member ( -- 회원
  member_id -- 회원번호
  );

-- 매니저
ALTER TABLE lms_manager
  ADD CONSTRAINT FK_lms_member_TO_lms_manager -- 회원 -> 매니저
  FOREIGN KEY (
  manager_id -- 매니저번호
  )
  REFERENCES lms_member ( -- 회원
  member_id -- 회원번호
  );

-- 매니저
ALTER TABLE lms_manager
  ADD CONSTRAINT FK_lms_department_TO_lms_manager -- 부서 -> 매니저
  FOREIGN KEY (
  department_id -- 부서번호
  )
  REFERENCES lms_department ( -- 부서
  department_id -- 부서번호
  );

-- 강의실
ALTER TABLE lms_class
  ADD CONSTRAINT FK_lms_edu_center_TO_lms_class -- 교육센터 -> 강의실
  FOREIGN KEY (
  edu_center_id -- 교육센터번호
  )
  REFERENCES lms_edu_center ( -- 교육센터
  edu_center_id -- 교육센터번호
  );

-- 회원
ALTER TABLE lms_member
  ADD CONSTRAINT FK_lms_degree_TO_lms_member -- 학력 -> 회원
  FOREIGN KEY (
  degree_id -- 학력번호
  )
  REFERENCES lms_degree ( -- 학력
  degree_id -- 학력번호
  );

-- 회원
ALTER TABLE lms_member
  ADD CONSTRAINT FK_lms_addr_TO_lms_member -- 기본주소 -> 회원
  FOREIGN KEY (
  addr_id -- 기본주소번호
  )
  REFERENCES lms_addr ( -- 기본주소
  addr_id -- 기본주소번호
  );

-- 교육센터
ALTER TABLE lms_edu_center
  ADD CONSTRAINT FK_lms_addr_TO_lms_edu_center -- 기본주소 -> 교육센터
  FOREIGN KEY (
  addr_id -- 기본주소번호
  )
  REFERENCES lms_addr ( -- 기본주소
  addr_id -- 기본주소번호
  );

-- 강의실사진
ALTER TABLE lms_class_photo
  ADD CONSTRAINT FK_lms_class_TO_lms_class_photo -- 강의실 -> 강의실사진
  FOREIGN KEY (
  class_id -- 강의실번호
  )
  REFERENCES lms_class ( -- 강의실
  class_id -- 강의실번호
  );

-- 수강신청
ALTER TABLE lms_application
  ADD CONSTRAINT FK_lms_lecture_TO_lms_application -- 강의 -> 수강신청
  FOREIGN KEY (
  lecture_id -- 강의번호
  )
  REFERENCES lms_lecture ( -- 강의
  lecture_id -- 강의번호
  );

-- 수강신청
ALTER TABLE lms_application
  ADD CONSTRAINT FK_lms_student_TO_lms_application -- 학생 -> 수강신청
  FOREIGN KEY (
  student_id -- 학생번호
  )
  REFERENCES lms_student ( -- 학생
  student_id -- 학생번호
  );

-- 수강신청
ALTER TABLE lms_application
  ADD CONSTRAINT FK_lms_application_state_TO_lms_application -- 신청상태 -> 수강신청
  FOREIGN KEY (
  application_state_id -- 신청상태번호
  )
  REFERENCES lms_application_state ( -- 신청상태
  application_state_id -- 신청상태번호
  );

-- 강의배정
ALTER TABLE lms_lecture_teacher
  ADD CONSTRAINT FK_lms_teacher_TO_lms_lecture_teacher -- 강사 -> 강의배정
  FOREIGN KEY (
  teacher_id -- 강사번호
  )
  REFERENCES lms_teacher ( -- 강사
  teacher_id -- 강사번호
  );

-- 강의배정
ALTER TABLE lms_lecture_teacher
  ADD CONSTRAINT FK_lms_lecture_TO_lms_lecture_teacher -- 강의 -> 강의배정
  FOREIGN KEY (
  lecture_id -- 강의번호
  )
  REFERENCES lms_lecture ( -- 강의
  lecture_id -- 강의번호
  );