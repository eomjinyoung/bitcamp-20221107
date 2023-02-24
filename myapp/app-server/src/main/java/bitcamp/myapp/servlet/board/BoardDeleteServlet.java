package bitcamp.myapp.servlet.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

@MultipartConfig(maxFileSize = 1024 * 1024 * 50)
@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardService boardService;

  @Override
  public void init() {
    boardService = (BoardService) getServletContext().getAttribute("boardService");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      int boardNo = Integer.parseInt(request.getParameter("no"));

      Board old = boardService.get(boardNo);
      if (old.getWriter().getNo() != loginUser.getNo()) {
        response.sendRedirect("../auth/fail");
        return;
      }
      boardService.delete(boardNo);

    }  catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "data");
    }

    request.getRequestDispatcher("/board/delete.jsp").forward(request, response);
  }
}
