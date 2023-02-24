package bitcamp.myapp.servlet.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;

@WebServlet("/board/view")
public class BoardViewServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardService boardService;

  @Override
  public void init() {
    boardService = (BoardService) getServletContext().getAttribute("boardService");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("board",
        boardService.get(Integer.parseInt(request.getParameter("no"))));
    request.getRequestDispatcher("/board/view.jsp").forward(request, response);
  }

}







