package bitcamp.myapp.servlet.board;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

@WebServlet("/board/filedelete")
public class BoardFileDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;
  private BoardFileDao boardFileDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
    boardFileDao = (BoardFileDao) ctx.getAttribute("boardFileDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 로그인 사용자의 정보를 가져온다.
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    int boardNo = Integer.parseInt(request.getParameter("boardNo"));

    Board old = boardDao.findByNo(boardNo);

    if (old.getWriter().getNo() != loginUser.getNo()) {
      response.sendRedirect("../auth/fail");
      return;
    }

    boardFileDao.delete(Integer.parseInt(request.getParameter("fileNo")));

    response.sendRedirect("view?no=" + boardNo);

  }
}
