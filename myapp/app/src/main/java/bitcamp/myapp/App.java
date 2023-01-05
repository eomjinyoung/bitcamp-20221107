package bitcamp.myapp;

public class App {

  public static void main(String[] args) {
    goMainMenu();
    System.out.println("안녕히 가세요!");

    // 프로그램이 사용한 자원 해제하기
    Prompt.close();
  } // main()

  private static void goMainMenu() {
    while (true) {
      System.out.println("1. 회원관리");
      System.out.println("9. 종료");
      int menuNo = Prompt.inputInt("메뉴> ");

      if (menuNo == 1) {
        goMemberMenu();
      } else if (menuNo == 9) {
        break;
      } else {
        System.out.println("잘못된 메뉴 번호 입니다.");
      }
    }
  }

  private static void goMemberMenu() {
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
        MemberHandler.inputMember();
      } else if (menuNo == 2) {
        MemberHandler.printMembers();
      } else if (menuNo >= 3 && menuNo <= 5) {
        System.out.println("작업실행!");
      } else {
        System.out.println("잘못된 메뉴 번호 입니다.");
      }
    }
  }

} // class App









