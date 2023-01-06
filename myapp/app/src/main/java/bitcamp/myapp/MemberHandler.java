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

    Member m = null;
    for (int i = 0; i < count; i++) {
      if (members[i].no == memberNo) {
        m = members[i];
        break;
      }
    }

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

      if (menuNo == 0) {
        break;
      } else if (menuNo == 1) {
        inputMember();
      } else if (menuNo == 2) {
        printMembers();
      } else if (menuNo == 3) {
        printMember();
      } else if (menuNo >= 4 && menuNo <= 5) {
        System.out.println("작업실행!");
      } else {
        System.out.println("잘못된 메뉴 번호 입니다.");
      }
    }
  }
}
