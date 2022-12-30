package bitcamp.myapp;

import java.sql.Date;
import java.util.Scanner;

public class App {

  // static 으로 선언한 변수는 static 멤버끼리 공유할 수 있다.
  static final int SIZE = 100;
  static int count = 0;

  static int[] no = new int[SIZE];
  static String[] name = new String[SIZE];
  static String[] tel = new String[SIZE];
  static String[] postNo = new String[SIZE];
  static String[] basicAddress = new String[SIZE];
  static String[] detailAddress = new String[SIZE];
  static boolean[] working = new boolean[SIZE];
  static char[] gender = new char[SIZE];
  static byte[] level = new byte[SIZE];
  static String[] createdDate = new String[SIZE];

  public static void main(String[] args) {

    // 코드를 메서드로 분리했으면 호출하라!
    inputMembers();

    System.out.println();

    // 코드를 메서드로 분리했으면 호출하라!
    printMembers();

  } // main()

  static void inputMembers() {
    Scanner keyScanner = new Scanner(System.in);

    for (int i = 0; i < SIZE; i++) {

      no[i] = promptInt(keyScanner, "번호? ");
      name[i] = promptString(keyScanner, "이름? ");
      tel[i] = promptString(keyScanner, "전화? ");
      postNo[i] = promptString(keyScanner, "우편번호? ");
      basicAddress[i] = promptString(keyScanner, "주소1? ");
      detailAddress[i] = promptString(keyScanner, "주소2? ");

      String title = "0. 미취업\n1. 재직중\n재직자? ";
      working[i] = promptInt(keyScanner, title) == 1;

      title = "0. 남자\n1. 여자\n성별? ";
      gender[i] = promptInt(keyScanner, title) == 0 ? 'M' : 'W';

      title = "0. 비전공자\n1. 준전공자\n2. 전공자\n전공? ";
      level[i] = (byte) promptInt(keyScanner, title);

      Date today = new Date(System.currentTimeMillis());
      createdDate[i] = today.toString();

      count++;

      String str = promptString(keyScanner, "계속 입력하시겠습니까?(Y/n) ");
      if (!str.equalsIgnoreCase("Y") && str.length() != 0) {
        break;
      }
    }

    keyScanner.close();
  }

  static void printMembers() {
    for (int i = 0; i < count; i++) {
      System.out.printf("번호: %d\n", no[i]);
      System.out.printf("이름: %s\n", name[i]);
      System.out.printf("전화: %s\n", tel[i]);
      System.out.printf("우편번호: %s\n", postNo[i]);
      System.out.printf("주소1: %s\n", basicAddress[i]);
      System.out.printf("주소2: %s\n", detailAddress[i]);
      System.out.printf("재직자: %s\n", working[i] ? "예" : "아니오");
      System.out.printf("성별: %s\n", gender[i] == 'M' ? "남자" : "여자");

      String levelTitle;
      switch (level[i]) {
        case 0: levelTitle = "비전공자"; break;
        case 1: levelTitle = "준전공자"; break;
        default: levelTitle = "전공자";
      }
      System.out.printf("전공: %s\n", levelTitle);

      System.out.printf("가입일: %s\n", createdDate[i]);

      System.out.println("---------------------------------------");
    }
  }

  static String promptString(Scanner scanner, String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

  static int promptInt(Scanner scanner, String title) {
    return Integer.parseInt(promptString(scanner, title));
  }

} // class App









