package bitcamp.myapp;

public class App {
  public static void main(String[] args) {
    int no = 1;
    String name = "홍길동";
    String tel = "010-11110-2222";
    String postNo = "06656";
    String basicAddress = "서울시 서초구 반포대로23길";
    String detailAddress = "101동 201호";
    boolean working = false;
    char gender = 'M'; // M(남자), W(여자)
    byte level = 0; // 0(비전공자), 1(준전공자), 2(전공자)
    String createdDate = "2022-12-29";

    System.out.printf("번호: %d\n", no);
    System.out.printf("이름: %s\n", name);
    System.out.printf("전화: %s\n", tel);
    System.out.printf("우편번호: %s\n", postNo);
    System.out.printf("주소1: %s\n", basicAddress);
    System.out.printf("주소2: %s\n", detailAddress);
    System.out.printf("재직자: %s\n", working ? "예" : "아니오");
    System.out.printf("성별: %s\n", gender == 'M' ? "남자" : "여자");

    String levelTitle;
    switch (level) {
      case 0: levelTitle = "비전공자"; break;
      case 1: levelTitle = "준전공자"; break;
      default: levelTitle = "전공자";
    }

    System.out.printf("전공: %s\n", levelTitle);

    System.out.printf("가입일: %s\n", createdDate);
  }
}









