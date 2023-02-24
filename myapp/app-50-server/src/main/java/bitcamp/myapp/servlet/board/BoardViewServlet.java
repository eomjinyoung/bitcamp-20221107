package bitcamp.myapp.servlet.board;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

@WebServlet("/board/view")
public class BoardViewServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;
  //  private BoardFileDao boardFileDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
    //    boardFileDao = (BoardFileDao) ctx.getAttribute("boardFileDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int boardNo = Integer.parseInt(request.getParameter("no"));
    Board b = boardDao.findByNo(boardNo);
    if (b != null) {
      boardDao.increaseViewCount(boardNo);
      request.setAttribute("board", b);

      // 게시글을 가져올 때 첨부파일 데이터까지 한 번에 가져오기 때문에 다음 코드를 필요 없다.
      //      List<BoardFile> boardFiles = boardFileDao.findAllOfBoard(boardNo);
      //      request.setAttribute("boardFiles", boardFiles);
    }
    request.getRequestDispatcher("/board/view.jsp").forward(request, response);
  }

}
