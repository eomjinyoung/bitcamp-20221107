package bitcamp.myapp.servlet.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

@WebServlet("/board/filedelete")
public class BoardFileDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardService boardService;

  @Override
  public void init() {
    boardService = (BoardService) getServletContext().getAttribute("boardService");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int boardNo = Integer.parseInt(request.getParameter("boardNo"));

    Board old = boardService.get(boardNo);
    if (old.getWriter().getNo() != loginUser.getNo()) {
      response.sendRedirect("../auth/fail");
      return;
    }

    boardService.deleteFile(Integer.parseInt(request.getParameter("fileNo")));
    response.sendRedirect("view?no=" + boardNo);
  }
}
