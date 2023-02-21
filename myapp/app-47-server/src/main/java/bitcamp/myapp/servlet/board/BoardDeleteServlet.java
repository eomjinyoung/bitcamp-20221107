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

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
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

    int boardNo = Integer.parseInt(request.getParameter("no"));
    String password = request.getParameter("password");

    Board old = boardDao.findByNo(boardNo);

    if (old == null) {
      request.setAttribute("error", "data");

    } else if (!old.getPassword().equals(password)) {
      request.setAttribute("error", "password");

    } else {
      boardDao.delete(boardNo);
    }

    request.getRequestDispatcher("/board/delete.jsp").forward(request, response);

  }
}
