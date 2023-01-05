package bitcamp.myapp;

public class App {

  public static void main(String[] args) {
    while (true) {
      System.out.println("1. 회원관리");
      System.out.println("9. 종료");
      int menuNo = Prompt.inputInt("메뉴> ");

      if (menuNo == 1) {
        System.out.println("회원 관리입니다.");
      } else if (menuNo == 9) {
        break;
      } else {
        System.out.println("잘못된 메뉴 번호 입니다.");
      }
    }

    System.out.println("안녕히 가세요!");
  } // main()

} // class App









