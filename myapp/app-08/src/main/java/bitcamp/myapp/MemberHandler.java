package bitcamp.myapp;

import java.sql.Date;

public class MemberHandler {

  static final int SIZE = 100;
  static int count = 0;

  // 레퍼런스 배열 준비
  static Member[] members = new Member[SIZE];

  static void inputMember() {
    Member m = new Member();
    m.no = Prompt.inputInt("번호? ");
    m.name = Prompt.inputString("이름? ");
    m.tel = Prompt.inputString("전화? ");
    m.postNo = Prompt.inputString("우편번호? ");
    m.basicAddress = Prompt.inputString("주소1? ");
    m.detailAddress = Prompt.inputString("주소2? ");
    m.working = Prompt.inputInt("0. 미취업\n1. 재직중\n재직자? ") == 1;
    m.gender = Prompt.inputInt("0. 남자\n1. 여자\n성별? ") == 0 ? 'M' : 'W';
    m.level = (byte) Prompt.inputInt("0. 비전공자\n1. 준전공자\n2. 전공자\n전공? ");
    m.createdDate = new Date(System.currentTimeMillis()).toString();

    members[count++] = m;
  }

  static void printMembers() {
    System.out.println("번호\t이름\t전화\t재직\t전공");

    for (int i = 0; i < count; i++) {
      Member m = members[i];
      System.out.printf("%d\t%s\t%s\t%s\t%s\n",
          m.no, m.name, m.tel,
          m.working ? "예" : "아니오",
              getLevelText(m.level));
    }
  }

  static void printMember() {
    int memberNo = Prompt.inputInt("회원번호? ");

    Member m = findByNo(memberNo);

    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    System.out.printf("    이름: %s\n", m.name);
    System.out.printf("    전화: %s\n", m.tel);
    System.out.printf("우편번호: %s\n", m.postNo);
    System.out.printf("기본주소: %s\n", m.basicAddress);
    System.out.printf("상세주소: %s\n", m.detailAddress);
    System.out.printf("재직여부: %s\n", m.working ? "예" : "아니오");
    System.out.printf("    성별: %s\n", m.gender == 'M' ? "남자" : "여자");
    System.out.printf("    전공: %s\n", getLevelText(m.level));
    System.out.printf("  등록일: %s\n", m.createdDate);
  }

  static String getLevelText(int level) {
    switch (level) {
      case 0: return "비전공자";
      case 1: return "준전공자";
      default: return "전공자";
    }
  }

  static void modifyMember() {
    int memberNo = Prompt.inputInt("회원번호? ");

    Member old = findByNo(memberNo);

    if (old == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    // 변경할 데이터를 저장할 인스턴스 준비
    Member m = new Member();
    m.no = old.no;
    m.createdDate = old.createdDate;
    m.name = Prompt.inputString(String.format("이름(%s)? ", old.name));
    m.tel = Prompt.inputString(String.format("전화(%s)? ", old.tel));
    m.postNo = Prompt.inputString(String.format("우편번호(%s)? ", old.postNo));
    m.basicAddress = Prompt.inputString(String.format("기본주소(%s)? ", old.basicAddress));
    m.detailAddress = Prompt.inputString(String.format("상세주소(%s)? ", old.detailAddress));
    m.working = Prompt.inputInt(String.format(
        "0. 미취업\n1. 재직중\n재직여부(%s)? ",
        old.working ? "재직중" : "미취업")) == 1;
    m.gender = Prompt.inputInt(String.format(
        "0. 남자\n1. 여자\n성별(%s)? ",
        old.gender == 'M' ? "남자" : "여자")) == 0 ? 'M' : 'W';
    m.level = (byte) Prompt.inputInt(String.format(
        "0. 비전공자\n1. 준전공자\n2. 전공자\n전공(%s)? ",
        getLevelText(old.level)));

    String str = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (str.equalsIgnoreCase("Y")) {
      members[indexOf(old)] = m;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }

  }

  static void deleteMember() {
    int memberNo = Prompt.inputInt("회원번호? ");

    Member m = findByNo(memberNo);

    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    String str = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!str.equalsIgnoreCase("Y")) {
      System.out.println("삭제 취소했습니다.");
      return;
    }

    for (int i = indexOf(m) + 1; i < count; i++) {
      members[i - 1] = members[i];
    }
    members[--count] = null; // 레퍼런스 카운트를 줄인다.

    System.out.println("삭제했습니다.");

  }

  static Member findByNo(int no) {
    for (int i = 0; i < count; i++) {
      if (members[i].no == no) {
        return members[i];
      }
    }
    return null;
  }

  static int indexOf(Member m) {
    for (int i = 0; i < count; i++) {
      if (members[i].no == m.no) {
        return i;
      }
    }
    return -1;
  }

  static void service() {
    while (true) {
      System.out.println("[회원 관리]");
      System.out.println("1. 등록");
      System.out.println("2. 목록");
      System.out.println("3. 조회");
      System.out.println("4. 변경");
      System.out.println("5. 삭제");
      System.out.println("0. 이전");
      int menuNo = Prompt.inputInt("회원관리> ");

      switch (menuNo) {
        case 0: return;
        case 1: inputMember(); break;
        case 2: printMembers(); break;
        case 3: printMember(); break;
        case 4: modifyMember(); break;
        case 5: deleteMember(); break;
        default:
          System.out.println("잘못된 메뉴 번호 입니다.");
      }
    }
  }
}
