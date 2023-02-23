package bitcamp.myapp.servlet.board;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

@WebServlet("/board/insert")
public class BoardInsertServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private TransactionManager txManager;
  private BoardDao boardDao;
  private BoardFileDao boardFileDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
    boardFileDao = (BoardFileDao) ctx.getAttribute("boardFileDao");
    txManager = (TransactionManager) ctx.getAttribute("txManager");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 로그인 사용자의 정보를 가져온다.
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    Board board = new Board();
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));

    Member writer = new Member();
    writer.setNo(loginUser.getNo());
    board.setWriter(writer);

    txManager.startTransaction();
    try {
      boardDao.insert(board);

      String[] files = request.getParameterValues("files");
      for (String file : files) {
        if (file.length() == 0) {
          continue;
        }
        BoardFile boardFile = new BoardFile();
        // 다른 사람이 올린 파일 이름과 중복되지 않도록 하기 위해 임의의 파일명을 생성한다.
        boardFile.setFilepath(UUID.randomUUID().toString());
        // 원래의 파일 이름도 보관
        boardFile.setOriginalFilename(file);
        // 게시글 번호 지정
        boardFile.setBoardNo(board.getNo());
        boardFileDao.insert(boardFile);
      }
      txManager.commit();

    } catch (Exception e) {
      txManager.rollback();
      e.printStackTrace();
      request.setAttribute("error", "data");
    }

    request.getRequestDispatcher("/board/insert.jsp").forward(request, response);
  }

}
