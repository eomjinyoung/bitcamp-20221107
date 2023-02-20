package bitcamp.myapp.servlet.board;

import java.io.IOException;
import java.io.PrintWriter;
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

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int boardNo = Integer.parseInt(request.getParameter("no"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프 - NCP 1기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시판</h1>");

    Board b = this.boardDao.findByNo(boardNo);

    if (b == null) {
      out.println("<p>해당 번호의 게시글 없습니다.</p>");

    } else {
      this.boardDao.increaseViewCount(boardNo);

      out.println("<form id='board-form' action='update' method='post'>");

      out.println("<table border='1'>");

      out.println("<tr>");
      out.println("  <th>번호</th>");
      out.printf("  <td><input type='text' name='no' value='%d' readonly></td>\n",  b.getNo());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>제목</th>");
      out.printf("  <td><input type='text' name='title' value='%s'></td>\n",  b.getTitle());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>내용</th>");
      out.printf("  <td><textarea name='content' rows='10' cols='60'>%s</textarea></td>\n",  b.getContent());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>암호</th>");
      out.println("  <td><input type='password' name='password'></td>");
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>등록일</th>");
      out.printf("  <td>%s</td>\n",  b.getCreatedDate());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>조회수</th>");
      out.printf("  <td>%d</td>\n",  b.getViewCount());
      out.println("</tr>");

      out.println("</table>");
    }

    out.println("<div>");
    out.println("  <button id='btn-list' type='button'>목록</button>");
    out.println("  <button>변경</button>");
    out.println("  <button id='btn-delete' type='button'>삭제</button>");
    out.println("</div>");

    out.println("</form>");

    out.println("<script>");
    out.println("document.querySelector('#btn-list').onclick = function() {");
    out.println("  location.href = 'list';");
    out.println("}");
    out.println("document.querySelector('#btn-delete').onclick = function() {");
    out.println("  var form = document.querySelector('#board-form');");
    out.println("  form.action = 'delete';");
    out.println("  form.submit();");
    out.println("}");
    out.println("</script>");

    out.println("</body>");
    out.println("</html>");

  }

}
