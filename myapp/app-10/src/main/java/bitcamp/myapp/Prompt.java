package bitcamp.myapp;

import java.util.Scanner;

public class Prompt {
  static Scanner scanner = new Scanner(System.in);

  static String inputString(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

  static int inputInt(String title) {
    return Integer.parseInt(inputString(title));
  }

  // Prompt 클래스를 다 사용한 후에 자원을 해제시킬 수 있는 메서드를 추가한다.
  static void close() {
    scanner.close();
  }
}
