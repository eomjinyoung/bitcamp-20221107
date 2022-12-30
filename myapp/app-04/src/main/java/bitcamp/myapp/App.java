package bitcamp.myapp;

import java.sql.Date;
import java.util.Scanner;

public class App {
  public static void main(String[] args) {

    Scanner keyScanner = new Scanner(System.in);

    final int SIZE = 100;
    int count = 0;

    int[] no = new int[SIZE];
    String[] name = new String[SIZE];
    String[] tel = new String[SIZE];
    String[] postNo = new String[SIZE];
    String[] basicAddress = new String[SIZE];
    String[] detailAddress = new String[SIZE];
    boolean[] working = new boolean[SIZE];
    char[] gender = new char[SIZE];
    byte[] level = new byte[SIZE];
    String[] createdDate = new String[SIZE];

    for (int i = 0; i < SIZE; i++) {
      System.out.print("번호? ");
      no[i] = Integer.parseInt(keyScanner.nextLine());

      System.out.print("이름? ");
      name[i] = keyScanner.nextLine();

      System.out.print("전화? ");
      tel[i] = keyScanner.nextLine();

      System.out.print("우편번호? ");
      postNo[i] = keyScanner.nextLine();

      System.out.print("주소1? ");
      basicAddress[i] = keyScanner.nextLine();

      System.out.print("주소2? ");
      detailAddress[i] = keyScanner.nextLine();

      System.out.println("0. 미취업");
      System.out.println("1. 재직중");
      System.out.print("재직자? ");
      working[i] = Integer.parseInt(keyScanner.nextLine()) == 1;

      System.out.println("0. 남자");
      System.out.println("1. 여자");
      System.out.print("성별? ");
      gender[i] = Integer.parseInt(keyScanner.nextLine()) == 0 ? 'M' : 'W';

      System.out.println("0. 비전공자");
      System.out.println("1. 준전공자");
      System.out.println("2. 전공자");
      System.out.print("전공? ");
      level[i] = Byte.parseByte(keyScanner.nextLine()); // 0(비전공자), 1(준전공자), 2(전공자)

      Date today = new Date(System.currentTimeMillis());
      createdDate[i] = today.toString();

      count++;

      System.out.print("계속 입력하시겠습니까?(Y/n) ");
      String str = keyScanner.nextLine();
      if (!str.equalsIgnoreCase("Y") && str.length() != 0) {
        break;
      }
    }

    keyScanner.close();

    System.out.println();

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

  } // main()
} // class App









