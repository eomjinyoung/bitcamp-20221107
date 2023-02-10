package bitcamp.myapp.handler;

import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.Prompt;

public class TeacherHandler {

  private TeacherDao teacherDao;
  private String title;

  public TeacherHandler(String title, TeacherDao teacherDao) {
    this.title = title;
    this.teacherDao = teacherDao;
  }

  private void inputTeacher() {
    Teacher m = new Teacher();
    m.setName(Prompt.inputString("이름? "));
    m.setTel(Prompt.inputString("전화? "));
    m.setEmail(Prompt.inputString("이메일? "));
    m.setDegree(Prompt.inputInt("1. 고졸\n2. 전문학사\n3. 학사\n4. 석사\n5. 박사\n0. 기타\n학위? "));
    m.setSchool(Prompt.inputString("학교? "));
    m.setMajor(Prompt.inputString("전공? "));
    m.setWage(Prompt.inputInt("강의료(시급)? "));

    this.teacherDao.insert(m);
  }

  private void printTeachers() {

    Object[] teachers = this.teacherDao.findAll();

    System.out.println("번호\t이름\t전화\t학위\t전공\t시강료");

    for (Object obj : teachers) {
      Teacher m = (Teacher) obj;
      System.out.printf("%d\t%s\t%s\t%s\t%s\t%d\n",
          m.getNo(), m.getName(), m.getTel(),
          getDegreeText(m.getDegree()), m.getMajor(), m.getWage());
    }
  }

  private void printTeacher() {
    int teacherNo = Prompt.inputInt("강사번호? ");

    Teacher m = this.teacherDao.findByNo(teacherNo);

    if (m == null) {
      System.out.println("해당 번호의 강사가 없습니다.");
      return;
    }

    System.out.printf("    이름: %s\n", m.getName());
    System.out.printf("    전화: %s\n", m.getTel());
    System.out.printf("  이메일: %s\n", m.getEmail());
    System.out.printf("    학위: %s\n", getDegreeText(m.getDegree()));
    System.out.printf("    학교: %s\n", m.getSchool());
    System.out.printf("    전공: %s\n", m.getMajor());
    System.out.printf("  강의료: %s\n", m.getWage());
    System.out.printf("  등록일: %s\n", m.getCreatedDate());
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

  private void modifyTeacher() {
    int teacherNo = Prompt.inputInt("강사번호? ");

    Teacher old = this.teacherDao.findByNo(teacherNo);

    if (old == null) {
      System.out.println("해당 번호의 강사가 없습니다.");
      return;
    }

    // 변경할 데이터를 저장할 인스턴스 준비
    Teacher m = new Teacher();
    m.setNo(old.getNo());
    m.setCreatedDate(old.getCreatedDate());
    m.setName(Prompt.inputString(String.format("이름(%s)? ", old.getName())));
    m.setTel(Prompt.inputString(String.format("전화(%s)? ", old.getTel())));
    m.setEmail(Prompt.inputString(String.format("이메일(%s)? ", old.getEmail())));
    m.setDegree(Prompt.inputInt(String.format(
        "1. 고졸\n2. 전문학사\n3. 학사\n4. 석사\n5. 박사\n0. 기타\n학위(%s)? ",
        getDegreeText(old.getDegree()))));
    m.setSchool(Prompt.inputString(String.format("학교(%s)? ", old.getSchool())));
    m.setMajor(Prompt.inputString(String.format("전공(%s)? ", old.getMajor())));
    m.setWage(Prompt.inputInt(String.format("강의료(시급)(%s)? ", old.getWage())));

    String str = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (str.equalsIgnoreCase("Y")) {
      this.teacherDao.update(m);
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }

  }

  private void deleteTeacher() {
    int teacherNo = Prompt.inputInt("강사번호? ");

    Teacher m = this.teacherDao.findByNo(teacherNo);

    if (m == null) {
      System.out.println("해당 번호의 강사가 없습니다.");
      return;
    }

    String str = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!str.equalsIgnoreCase("Y")) {
      System.out.println("삭제 취소했습니다.");
      return;
    }

    teacherDao.delete(m);

    System.out.println("삭제했습니다.");

  }

  public void service() {

    while (true) {
      System.out.printf("[%s]\n", this.title);
      System.out.println("1. 등록");
      System.out.println("2. 목록");
      System.out.println("3. 조회");
      System.out.println("4. 변경");
      System.out.println("5. 삭제");
      System.out.println("0. 이전");
      int menuNo = Prompt.inputInt(String.format("%s> ", this.title));

      switch (menuNo) {
        case 0: return;
        case 1: this.inputTeacher(); break;
        case 2: this.printTeachers(); break;
        case 3: this.printTeacher(); break;
        case 4: this.modifyTeacher(); break;
        case 5: this.deleteTeacher(); break;
        default:
          System.out.println("잘못된 메뉴 번호 입니다.");
      }
    }
  }
}
