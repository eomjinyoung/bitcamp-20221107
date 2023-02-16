package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.StreamTool;
import bitcamp.util.TransactionManager;

public class TeacherHandler {

  private TransactionManager txManager;
  private MemberDao memberDao;
  private TeacherDao teacherDao;
  private String title;

  public TeacherHandler(String title, TransactionManager txManager, MemberDao memberDao, TeacherDao teacherDao) {
    this.title = title;
    this.txManager = txManager;
    this.memberDao = memberDao;
    this.teacherDao = teacherDao;
  }

  private void inputTeacher(StreamTool streamTool) throws Exception {
    Teacher m = new Teacher();
    m.setName(streamTool.promptString("이름? "));
    m.setEmail(streamTool.promptString("이메일? "));
    m.setPassword(streamTool.promptString("암호? "));
    m.setTel(streamTool.promptString("전화? "));
    m.setDegree(streamTool.promptInt("1. 고졸\n2. 전문학사\n3. 학사\n4. 석사\n5. 박사\n0. 기타\n학위? "));
    m.setSchool(streamTool.promptString("학교? "));
    m.setMajor(streamTool.promptString("전공? "));
    m.setWage(streamTool.promptInt("강의료(시급)? "));

    txManager.startTransaction();
    try {
      memberDao.insert(m);
      teacherDao.insert(m);
      txManager.commit();
      streamTool.println("입력했습니다!").send();

    } catch (Exception e) {
      txManager.rollback();
      streamTool.println("입력 실패했습니다!").send();
      e.printStackTrace();
    }
  }

  private void printTeachers(StreamTool streamTool) throws Exception {

    List<Teacher> teachers = this.teacherDao.findAll();

    streamTool.println("번호\t이름\t전화\t학위\t전공\t시강료");
    for (Teacher m : teachers) {
      streamTool.printf("%d\t%s\t%s\t%s\t%s\t%d\n",
          m.getNo(), m.getName(), m.getTel(),
          getDegreeText(m.getDegree()), m.getMajor(), m.getWage());
    }
    streamTool.send();
  }

  private void printTeacher(StreamTool streamTool) throws Exception {
    int teacherNo = streamTool.promptInt("강사번호? ");

    Teacher m = this.teacherDao.findByNo(teacherNo);
    if (m == null) {
      streamTool.println("해당 번호의 강사가 없습니다.").send();
      return;
    }

    streamTool.printf("    이름: %s\n", m.getName())
    .printf("  이메일: %s\n", m.getEmail())
    .printf("    전화: %s\n", m.getTel())
    .printf("    학위: %s\n", getDegreeText(m.getDegree()))
    .printf("    학교: %s\n", m.getSchool())
    .printf("    전공: %s\n", m.getMajor())
    .printf("  강의료: %s\n", m.getWage())
    .printf("  등록일: %s\n", m.getCreatedDate())
    .send();
  }

  private static String getDegreeText(int degree) {
    switch (degree) {
      case 1: return "고졸";
      case 2: return "전문학사";
      case 3: return "학사";
      case 4: return "석사";
      case 5: return "박사";
      default: return "기타";
    }
  }

  private void modifyTeacher(StreamTool streamTool) throws Exception {
    int teacherNo = streamTool.promptInt("강사번호? ");

    Teacher old = this.teacherDao.findByNo(teacherNo);
    if (old == null) {
      streamTool.println("해당 번호의 강사가 없습니다.");
      return;
    }

    // 변경할 데이터를 저장할 인스턴스 준비
    Teacher m = new Teacher();
    m.setNo(old.getNo());
    m.setCreatedDate(old.getCreatedDate());
    m.setName(streamTool.promptString(String.format("이름(%s)? ", old.getName())));
    m.setEmail(streamTool.promptString(String.format("이메일(%s)? ", old.getEmail())));
    m.setPassword(streamTool.promptString("암호? "));
    m.setTel(streamTool.promptString(String.format("전화(%s)? ", old.getTel())));
    m.setDegree(streamTool.promptInt(String.format(
        "1. 고졸\n2. 전문학사\n3. 학사\n4. 석사\n5. 박사\n0. 기타\n학위(%s)? ",
        getDegreeText(old.getDegree()))));
    m.setSchool(streamTool.promptString(String.format("학교(%s)? ", old.getSchool())));
    m.setMajor(streamTool.promptString(String.format("전공(%s)? ", old.getMajor())));
    m.setWage(streamTool.promptInt(String.format("강의료(시급)(%s)? ", old.getWage())));

    String str = streamTool.promptString("정말 변경하시겠습니까?(y/N) ");
    if (str.equalsIgnoreCase("Y")) {
      txManager.startTransaction();
      try {
        memberDao.update(m);
        teacherDao.update(m);
        txManager.commit();
        streamTool.println("변경했습니다.");

      } catch (Exception e) {
        txManager.rollback();
        streamTool.println("변경 실패했습니다.");
        e.printStackTrace();
      }
    } else {
      streamTool.println("변경 취소했습니다.");
    }
    streamTool.send();

  }

  private void deleteTeacher(StreamTool streamTool) throws Exception {
    int teacherNo = streamTool.promptInt("강사번호? ");

    Teacher m = this.teacherDao.findByNo(teacherNo);
    if (m == null) {
      streamTool.println("해당 번호의 강사가 없습니다.").send();
      return;
    }

    String str = streamTool.promptString("정말 삭제하시겠습니까?(y/N) ");
    if (!str.equalsIgnoreCase("Y")) {
      streamTool.println("삭제 취소했습니다.").send();
      return;
    }

    txManager.startTransaction();
    try {
      teacherDao.delete(teacherNo);
      memberDao.delete(teacherNo);
      txManager.commit();
      streamTool.println("삭제했습니다.").send();

    } catch (Exception e) {
      txManager.rollback();
      streamTool.println("삭제 실패했습니다.").send();
      e.printStackTrace();
    }
  }

  public void service(StreamTool streamTool) throws Exception {
    menu(streamTool);

    while (true) {

      String command = streamTool.readString();
      if (command.equals("menu")) {
        menu(streamTool);
        continue;
      }

      int menuNo;
      try {
        menuNo = Integer.parseInt(command);
      } catch (Exception e) {
        streamTool.println("메뉴 번호가 옳지 않습니다!").println().send();
        continue;
      }

      try {
        switch (menuNo) {
          case 0:
            streamTool.println("메인화면으로 이동!").send();
            return;
          case 1: this.inputTeacher(streamTool); break;
          case 2: this.printTeachers(streamTool); break;
          case 3: this.printTeacher(streamTool); break;
          case 4: this.modifyTeacher(streamTool); break;
          case 5: this.deleteTeacher(streamTool); break;
          default:
            streamTool.println("잘못된 메뉴 번호 입니다.").send();
        }
      } catch (Exception e) {
        streamTool.printf("명령 실행 중 오류 발생! - %s : %s\n",
            e.getMessage(),
            e.getClass().getSimpleName()).send();
      }
    }
  }

  void menu(StreamTool streamTool) throws Exception {
    streamTool.printf("[%s]\n", this.title)
    .println("1. 등록")
    .println("2. 목록")
    .println("3. 조회")
    .println("4. 변경")
    .println("5. 삭제")
    .println("0. 이전")
    .send();
  }
}
