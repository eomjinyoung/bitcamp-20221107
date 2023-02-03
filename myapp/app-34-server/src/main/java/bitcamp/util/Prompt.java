package bitcamp.util;

import java.util.Scanner;

public class Prompt {
  private static Scanner scanner = new Scanner(System.in);

  public static String inputString(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

  public static int inputInt(String title) {
    return Integer.parseInt(inputString(title));
  }

  // Prompt 클래스를 다 사용한 후에 자원을 해제시킬 수 있는 메서드를 추가한다.
  public static void close() {
    scanner.close();
  }
}
