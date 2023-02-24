package bitcamp.myapp.servlet.board;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

// 게시글 삭제는 게시글 수정 폼을 그대로 사용하기 때문에
// 요청 데이터가 multipart/form-data 형식으로 넘어온다.
@MultipartConfig(maxFileSize = 1024 * 1024 * 50)
@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
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

    txManager.startTransaction();
    try {
      // 로그인 사용자의 정보를 가져온다.
      Member loginUser = (Member) request.getSession().getAttribute("loginUser");

      int boardNo = Integer.parseInt(request.getParameter("no"));

      Board old = boardDao.findByNo(boardNo);

      if (old.getWriter().getNo() != loginUser.getNo()) {
        response.sendRedirect("../auth/fail");
        return;
      }

      boardFileDao.deleteOfBoard(boardNo);
      if (boardDao.delete(boardNo) == 0) {
        throw new RuntimeException("게시글이 존재하지 않습니다!");
      }

      txManager.commit();

    }  catch (Exception e) {
      txManager.rollback();
      e.printStackTrace();
      request.setAttribute("error", "data");
    }

    request.getRequestDispatcher("/board/delete.jsp").forward(request, response);

  }
}
