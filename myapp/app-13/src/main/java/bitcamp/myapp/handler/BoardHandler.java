package bitcamp.myapp.handler;

import java.sql.Date;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

public class BoardHandler {

  private BoardDao boardDao = new BoardDao();
  private String title;

  // 인스턴스를 만들 때 프롬프트 제목을 반드시 입력하도록 강제한다.
  public BoardHandler(String title) {
    this.title = title;
  }

  private void inputBoard() {
    Board b = new Board();
    b.setNo(Prompt.inputInt("번호? "));
    b.setTitle(Prompt.inputString("제목? "));
    b.setContent(Prompt.inputString("내용? "));
    b.setPassword(Prompt.inputString("암호? "));
    b.setCreatedDate(new Date(System.currentTimeMillis()).toString());

    this.boardDao.insert(b);
  }

  private void printBoards() {
    System.out.println("번호\t제목\t작성일\t조회수");

    Board[] boards = this.boardDao.findAll();

    for (Board b : boards) {
      System.out.printf("%d\t%s\t%s\t%d\n",
          b.getNo(), b.getTitle(), b.getCreatedDate(), b.getViewCount());
    }
  }

  private void printBoard() {
    int boardNo = Prompt.inputInt("게시글 번호? ");

    Board b = this.boardDao.findByNo(boardNo);

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

  private void modifyBoard() {
    int boardNo = Prompt.inputInt("게시글 번호? ");

    Board old = this.boardDao.findByNo(boardNo);

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
      this.boardDao.update(b);
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }

  }

  private void deleteBoard() {
    int boardNo = Prompt.inputInt("게시글 번호? ");

    Board b = this.boardDao.findByNo(boardNo);

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

    this.boardDao.delete(b);

    System.out.println("삭제했습니다.");

  }

  private void searchBoard() {
    Board[] boards = this.boardDao.findAll();
    String keyword = Prompt.inputString("검색어? ");
    System.out.println("번호\t제목\t작성일\t조회수");
    for (Board b : boards) {
      if (b.getTitle().indexOf(keyword) != -1 ||
          b.getContent().indexOf(keyword) != -1) {
        System.out.printf("%d\t%s\t%s\t%d\n",
            b.getNo(), b.getTitle(), b.getCreatedDate(), b.getViewCount());
      }
    }
  }

  public void service() {
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
