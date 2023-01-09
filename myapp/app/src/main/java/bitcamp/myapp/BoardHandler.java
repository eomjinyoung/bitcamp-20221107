package bitcamp.myapp;

import java.sql.Date;

public class BoardHandler {

  // 모든 인스턴스가 공유하는 데이터를 스태틱 필드로 만든다.
  // 특히 데이터를 조회하는 용으로 사용하는 final 변수는 스태틱 필드로 만들어야 한다.
  static final int SIZE = 100;

  int count;
  Board[] boards = new Board[SIZE];
  String title;

  // 인스턴스를 만들 때 프롬프트 제목을 반드시 입력하도록 강제한다.
  BoardHandler(String title) {
    this.title = title;
  }

  void inputBoard() {
    Board b = new Board();
    b.setNo(Prompt.inputInt("번호? "));
    b.setTitle(Prompt.inputString("제목? "));
    b.setContent(Prompt.inputString("내용? "));
    b.setPassword(Prompt.inputString("암호? "));
    b.setCreatedDate(new Date(System.currentTimeMillis()).toString());

    this.boards[count++] = b;
  }

  void printBoards() {
    System.out.println("번호\t제목\t작성일\t조회수");

    for (int i = 0; i < this.count; i++) {
      Board b = this.boards[i];
      System.out.printf("%d\t%s\t%s\t%d\n",
          b.getNo(), b.getTitle(), b.getCreatedDate(), b.getViewCount());
    }
  }

  void printBoard() {
    int boardNo = Prompt.inputInt("게시글 번호? ");

    Board b = this.findByNo(boardNo);

    if (b == null) {
      System.out.println("해당 번호의 게시글 없습니다.");
      return;
    }

    System.out.printf("    제목: %s\n", b.getTitle());
    System.out.printf("    내용: %s\n", b.getContent());
    System.out.printf("  등록일: %s\n", b.getCreatedDate());
    System.out.printf("  조회수: %d\n", b.getViewCount());
    b.setViewCount(b.getViewCount() + 1);
  }

  void modifyBoard() {
    int boardNo = Prompt.inputInt("게시글 번호? ");

    Board old = this.findByNo(boardNo);

    if (old == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    // 변경할 데이터를 저장할 인스턴스 준비
    Board b = new Board();
    b.setNo(old.getNo());
    b.setCreatedDate(old.getCreatedDate());
    b.setTitle(Prompt.inputString(String.format("제목(%s)? ", old.getTitle())));
    b.setContent(Prompt.inputString(String.format("내용(%s)? ", old.getContent())));
    b.setPassword(Prompt.inputString("암호? "));

    if (!old.getPassword().equals(b.getPassword())) {
      System.out.println("암호가 맞지 않습니다!");
      return;
    }

    String str = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (str.equalsIgnoreCase("Y")) {
      this.boards[this.indexOf(old)] = b;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }

  }

  void deleteBoard() {
    int boardNo = Prompt.inputInt("게시글 번호? ");

    Board b = this.findByNo(boardNo);

    if (b == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String password = Prompt.inputString("암호? ");
    if (!b.getPassword().equals(password)) {
      System.out.println("암호가 맞지 않습니다!");
      return;
    }

    String str = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!str.equalsIgnoreCase("Y")) {
      System.out.println("삭제 취소했습니다.");
      return;
    }

    for (int i = this.indexOf(b) + 1; i < this.count; i++) {
      this.boards[i - 1] = this.boards[i];
    }
    this.boards[--this.count] = null; // 레퍼런스 카운트를 줄인다.

    System.out.println("삭제했습니다.");

  }

  Board findByNo(int no) {
    for (int i = 0; i < this.count; i++) {
      if (this.boards[i].getNo() == no) {
        return this.boards[i];
      }
    }
    return null;
  }

  int indexOf(Board b) {
    for (int i = 0; i < this.count; i++) {
      if (this.boards[i].getNo() == b.getNo()) {
        return i;
      }
    }
    return -1;
  }

  void searchBoard() {
    String keyword = Prompt.inputString("검색어? ");

    System.out.println("번호\t제목\t작성일\t조회수");

    for (int i = 0; i < this.count; i++) {
      Board b = this.boards[i];
      if (b.getTitle().indexOf(keyword) != -1 ||
          b.getContent().indexOf(keyword) != -1) {
        System.out.printf("%d\t%s\t%s\t%d\n",
            b.getNo(), b.getTitle(), b.getCreatedDate(), b.getViewCount());
      }
    }
  }

  void service() {
    while (true) {
      System.out.printf("[%s]\n", this.title);
      System.out.println("1. 등록");
      System.out.println("2. 목록");
      System.out.println("3. 조회");
      System.out.println("4. 변경");
      System.out.println("5. 삭제");
      System.out.println("6. 검색");
      System.out.println("0. 이전");
      int menuNo = Prompt.inputInt(String.format("%s> ", this.title));

      switch (menuNo) {
        case 0: return;
        case 1: this.inputBoard(); break;
        case 2: this.printBoards(); break;
        case 3: this.printBoard(); break;
        case 4: this.modifyBoard(); break;
        case 5: this.deleteBoard(); break;
        case 6: this.searchBoard(); break;
        default:
          System.out.println("잘못된 메뉴 번호 입니다.");
      }
    }
  }
}
