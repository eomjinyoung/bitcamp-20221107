package bitcamp.myapp.handler;

import java.sql.Connection;
import java.util.List;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.vo.Student;
import bitcamp.util.ConnectionFactory;
import bitcamp.util.StreamTool;

public class StudentHandler {

  private ConnectionFactory conFactory;
  private MemberDao memberDao;
  private StudentDao studentDao;
  private String title;

  public StudentHandler(String title, ConnectionFactory conFactory, MemberDao memberDao, StudentDao studentDao) {
    this.title = title;
    this.conFactory = conFactory;
    this.memberDao = memberDao;
    this.studentDao = studentDao;
  }

  private void inputMember(StreamTool streamTool) throws Exception {
    Student s = new Student();
    s.setName(streamTool.promptString("이름? "));
    s.setEmail(streamTool.promptString("이메일? "));
    s.setPassword(streamTool.promptString("암호? "));
    s.setTel(streamTool.promptString("전화? "));
    s.setPostNo(streamTool.promptString("우편번호? "));
    s.setBasicAddress(streamTool.promptString("주소1? "));
    s.setDetailAddress(streamTool.promptString("주소2? "));
    s.setWorking(streamTool.promptInt("0. 미취업\n1. 재직중\n재직자? ") == 1);
    s.setGender(streamTool.promptInt("0. 남자\n1. 여자\n성별? ") == 0 ? 'M' : 'W');
    s.setLevel((byte) streamTool.promptInt("0. 비전공자\n1. 준전공자\n2. 전공자\n전공? "));

    // 현재 스레드가 갖고 있는 Connection 객체를 리턴 받는다.
    Connection con = conFactory.getConnection();
    con.setAutoCommit(false);
    try {
      memberDao.insert(s);
      studentDao.insert(s);

      Thread t = Thread.currentThread();
      System.out.printf("%s 스레드를 30초간 중지합니다!", t.getName());
      Thread.sleep(30000);

      con.commit();
      streamTool.println("입력했습니다!").send();

    } catch (Exception e) {
      con.rollback();
      streamTool.println("입력 실패입니다!").send();
      e.printStackTrace();

    } finally {
      con.setAutoCommit(true);
    }

  }

  private void printMembers(StreamTool streamTool) throws Exception {
    List<Student> members = this.studentDao.findAll();
    streamTool.println("번호\t이름\t전화\t재직\t전공");
    for (Student m : members) {
      streamTool.printf("%d\t%s\t%s\t%s\t%s\n",
          m.getNo(), m.getName(), m.getTel(),
          m.isWorking() ? "예" : "아니오",
              getLevelText(m.getLevel()));
    }
    streamTool.send();
  }

  private void printMember(StreamTool streamTool) throws Exception {
    int memberNo = streamTool.promptInt("회원번호? ");

    Student m = this.studentDao.findByNo(memberNo);

    if (m == null) {
      streamTool.println("해당 번호의 학생이 없습니다.").send();
      return;
    }

    streamTool
    .printf("    이름: %s\n", m.getName())
    .printf("    전화: %s\n", m.getTel())
    .printf("우편번호: %s\n", m.getPostNo())
    .printf("기본주소: %s\n", m.getBasicAddress())
    .printf("상세주소: %s\n", m.getDetailAddress())
    .printf("재직여부: %s\n", m.isWorking() ? "예" : "아니오")
    .printf("    성별: %s\n", m.getGender() == 'M' ? "남자" : "여자")
    .printf("    전공: %s\n", getLevelText(m.getLevel()))
    .printf("  등록일: %s\n", m.getCreatedDate())
    .send();

  }

  // 인스턴스 멤버(필드나 메서드)를 사용하지 않기 때문에
  // 그냥 스태틱 메서드로 두어라!
  private static String getLevelText(int level) {
    switch (level) {
      case 0: return "비전공자";
      case 1: return "준전공자";
      default: return "전공자";
    }
  }

  private void modifyMember(StreamTool streamTool) throws Exception {
    int memberNo = streamTool.promptInt("회원번호? ");

    Student old = this.studentDao.findByNo(memberNo);

    if (old == null) {
      streamTool.println("해당 번호의 회원이 없습니다.").send();
      return;
    }

    // 변경할 데이터를 저장할 인스턴스 준비
    Student m = new Student();
    m.setNo(old.getNo());
    m.setCreatedDate(old.getCreatedDate());
    m.setName(streamTool.promptString(String.format("이름(%s)? ", old.getName())));
    m.setEmail(streamTool.promptString(String.format("이메일(%s)? ", old.getEmail())));
    m.setPassword(streamTool.promptString("암호? "));
    m.setTel(streamTool.promptString(String.format("전화(%s)? ", old.getTel())));
    m.setPostNo(streamTool.promptString(String.format("우편번호(%s)? ", old.getPostNo())));
    m.setBasicAddress(streamTool.promptString(String.format("기본주소(%s)? ", old.getBasicAddress())));
    m.setDetailAddress(streamTool.promptString(String.format("상세주소(%s)? ", old.getDetailAddress())));
    m.setWorking(streamTool.promptInt(String.format(
        "0. 미취업\n1. 재직중\n재직여부(%s)? ",
        old.isWorking() ? "재직중" : "미취업")) == 1);
    m.setGender(streamTool.promptInt(String.format(
        "0. 남자\n1. 여자\n성별(%s)? ",
        old.getGender() == 'M' ? "남자" : "여자")) == 0 ? 'M' : 'W');
    m.setLevel((byte) streamTool.promptInt(String.format(
        "0. 비전공자\n1. 준전공자\n2. 전공자\n전공(%s)? ",
        getLevelText(old.getLevel()))));

    String str = streamTool.promptString("정말 변경하시겠습니까?(y/N) ");
    if (str.equalsIgnoreCase("Y")) {
      // 현재 스레드가 갖고 있는 Connection 객체를 리턴 받는다.
      Connection con = conFactory.getConnection();
      con.setAutoCommit(false);
      try {
        memberDao.update(m);
        studentDao.update(m);
        con.commit();
        streamTool.println("변경했습니다.");

      } catch (Exception e) {
        con.rollback();
        streamTool.println("변경 실패했습니다!");
        e.printStackTrace();

      } finally {
        con.setAutoCommit(true);
      }
    } else {
      streamTool.println("변경 취소했습니다.");
    }
    streamTool.send();
  }

  private void deleteMember(StreamTool streamTool) throws Exception {
    int memberNo = streamTool.promptInt("회원번호? ");

    Student m = this.studentDao.findByNo(memberNo);

    if (m == null) {
      streamTool.println("해당 번호의 회원이 없습니다.").send();
      return;
    }

    String str = streamTool.promptString("정말 삭제하시겠습니까?(y/N) ");
    if (!str.equalsIgnoreCase("Y")) {
      streamTool.println("삭제 취소했습니다.").send();
      return;
    }

    // 현재 스레드가 갖고 있는 Connection 객체를 리턴 받는다.
    Connection con = conFactory.getConnection();
    con.setAutoCommit(false);
    try {
      studentDao.delete(memberNo);
      memberDao.delete(memberNo);
      con.commit();
      streamTool.println("삭제했습니다.").send();

    } catch (Exception e) {
      con.rollback();
      streamTool.println("삭제 실패했습니다.").send();

    } finally {
      con.setAutoCommit(true);
    }
  }

  private void searchMember(StreamTool streamTool) throws Exception {
    String keyword = streamTool.promptString("검색어? ");

    List<Student> students = this.studentDao.findByKeyword(keyword);

    streamTool.println("번호\t이름\t전화\t재직\t전공");
    for (Student m : students) {
      streamTool.printf("%d\t%s\t%s\t%s\t%s\n",
          m.getNo(), m.getName(), m.getTel(),
          m.isWorking() ? "예" : "아니오",
              getLevelText(m.getLevel()));
    }
    streamTool.send();
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
          case 1: this.inputMember(streamTool); break;
          case 2: this.printMembers(streamTool); break;
          case 3: this.printMember(streamTool); break;
          case 4: this.modifyMember(streamTool); break;
          case 5: this.deleteMember(streamTool); break;
          case 6: this.searchMember(streamTool); break;
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
    .println("6. 검색")
    .println("0. 이전")
    .send();
  }
}











