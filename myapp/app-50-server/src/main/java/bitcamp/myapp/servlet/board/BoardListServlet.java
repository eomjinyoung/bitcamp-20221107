package bitcamp.myapp.servlet.board;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String keyword = request.getParameter("keyword");
    List<Board> boards = boardDao.findAll(keyword);

    // JSP 에서 사용할 수 있도록 ServletRequest 보관소에 저장한다.
    request.setAttribute("boards", boards);

    // JSP로 실행을 위임시킨다.
    // => JSP를 실행한 다음에 다시 되돌아 온다는 것을 잊지 말라!

    RequestDispatcher dispatcher = request.getRequestDispatcher("/board/list.jsp");
    // 파라미터에서 '/'는 현재 웹 애플리케이션을 가리킨다.
    // 즉 Context Root 경로(/web)를 가리킨다.
    // 서버에서 사용하는 / 경로는 기본이 현재 웹 애플리케이션(http://localhost:8080/web/)이다.
    // 클라이언트에서 사용하는 / 경로는 서버 루트(http://localhost:8080/)를 의미한다.

    dispatcher.forward(request, response); // JSP 실행! 주의! JSP를 실행한 후 되돌아 온다.

  }
}








