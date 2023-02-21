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

@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));
    board.setPassword(request.getParameter("password"));

    Board old = boardDao.findByNo(board.getNo());

    if (old == null) {
      request.setAttribute("error", "data");

    } else if (!old.getPassword().equals(board.getPassword())) {
      request.setAttribute("error", "password");

    } else {
      boardDao.update(board);
    }

    request.getRequestDispatcher("/board/update.jsp").forward(request, response);
  }

}




