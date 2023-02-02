package bitcamp.myapp.servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.LinkedList;
import com.google.gson.Gson;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

public class BoardServlet {

  private BoardDao boardDao = new BoardDao(new LinkedList<Board>());

  private void inputBoard(DataInputStream in, DataOutputStream out) {
    Board b = new Board();
    b.setTitle(Prompt.inputString("제목? "));
    b.setContent(Prompt.inputString("내용? "));
    b.setPassword(Prompt.inputString("암호? "));

    this.boardDao.insert(b);
  }

  private void printBoards(DataInputStream in, DataOutputStream out) throws Exception {
    out.writeUTF("200");
    out.writeUTF(new Gson().toJson(this.boardDao.findAll()));
  }

  private void printBoard(DataInputStream in, DataOutputStream out) {
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

  private void modifyBoard(DataInputStream in, DataOutputStream out) {
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

  private void deleteBoard(DataInputStream in, DataOutputStream out) {
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

  private void searchBoard(DataInputStream in, DataOutputStream out) {
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

  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    boardDao.load("board.json");

    try {
      // 클라이언트가 요구하는 액션을 읽는다.
      String action = in.readUTF();

      switch (action) {
        case "insert": this.inputBoard(in, out); break;
        case "findAll": this.printBoards(in, out); break;
        case "findByNo": this.printBoard(in, out); break;
        case "update": this.modifyBoard(in, out); break;
        case "delete": this.deleteBoard(in, out); break;
        default:
          System.out.println("잘못된 메뉴 번호 입니다.");
      }
    } catch (Exception e) {
      out.writeUTF("500");
    }

    boardDao.save("board.json");
  }
}
