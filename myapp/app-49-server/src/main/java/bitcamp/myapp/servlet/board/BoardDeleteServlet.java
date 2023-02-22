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
import bitcamp.myapp.vo.Member;

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

    // 로그인 사용자의 정보를 가져온다.
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    int boardNo = Integer.parseInt(request.getParameter("no"));

    Board old = boardDao.findByNo(boardNo);

    if (old.getWriterNo() != loginUser.getNo()) {
      response.sendRedirect("../auth/fail");
      return;
    } else if (boardDao.delete(boardNo) == 0) {
      request.setAttribute("error", "data");
    }

    request.getRequestDispatcher("/board/delete.jsp").forward(request, response);

  }
}
