package bitcamp.myapp.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;
import bitcamp.util.StreamTool;

@WebServlet("/board/*")
public class BoardServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;

  public BoardServlet() {
    try {
      InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
          "bitcamp/myapp/config/mybatis-config.xml");
      SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
      BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
          builder.build(mybatisConfigInputStream));
      boardDao = new DaoGenerator(sqlSessionFactory).getObject(BoardDao.class);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String pathInfo = request.getPathInfo();

    if (pathInfo == null) {
      menu(request, response);
      return;
    }

    switch (pathInfo) {
      case "/list":
        printBoards(request, response);
        break;
      case "/view":
        printBoard(request, response);
        break;
    }
  }

  private void inputBoard(StreamTool streamTool) throws Exception {
    Board b = new Board();
    b.setTitle(streamTool.promptString("제목? "));
    b.setContent(streamTool.promptString("내용? "));
    b.setPassword(streamTool.promptString("암호? "));

    this.boardDao.insert(b);

    streamTool.println("입력했습니다!").send();
  }

  private void printBoards(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
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

    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("  <th>번호</th> <th>제목</th> <th>작성일</th> <th>조회수</th>");
    out.println("</tr>");

    List<Board> boards = this.boardDao.findAll();
    for (Board b : boards) {
      out.println("<tr>");
      out.printf("  <td>%d</td> <td><a href='view?no=%d'>%s</a></td> <td>%s</td> <td>%d</td>\n",
          b.getNo(), b.getNo(), b.getTitle(), b.getCreatedDate(), b.getViewCount());
      out.println("</tr>");
    }
    out.println("</table>");

    out.println("</body>");
    out.println("</html>");
  }

  private void printBoard(HttpServletRequest request, HttpServletResponse response)
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

      out.println("<table border='1'>");

      out.println("<tr>");
      out.println("  <th>제목</th>");
      out.printf("  <td><input type='text' value='%s'></td>\n",  b.getTitle());
      out.println("</tr>");

      out.printf("    내용: %s<br>\n", b.getContent());
      out.printf("  등록일: %s<br>\n", b.getCreatedDate());
      out.printf("  조회수: %d<br>\n", b.getViewCount());
      out.println("</table>");
    }

    out.println("</body>");
    out.println("</html>");
  }

  private void modifyBoard(StreamTool streamTool) throws Exception {
    int boardNo = streamTool.promptInt("게시글 번호? ");

    Board old = this.boardDao.findByNo(boardNo);

    if (old == null) {
      streamTool.println("해당 번호의 게시글이 없습니다.").send();
      return;
    }

    // 변경할 데이터를 저장할 인스턴스 준비
    Board b = new Board();
    b.setNo(old.getNo());
    b.setCreatedDate(old.getCreatedDate());
    b.setTitle(streamTool.promptString(String.format("제목(%s)? ", old.getTitle())));
    b.setContent(streamTool.promptString(String.format("내용(%s)? ", old.getContent())));
    b.setPassword(streamTool.promptString("암호? "));
    b.setViewCount(old.getViewCount());

    if (!old.getPassword().equals(b.getPassword())) {
      streamTool.println("암호가 맞지 않습니다!").send();
      return;
    }

    String str = streamTool.promptString("정말 변경하시겠습니까?(y/N) ");
    if (str.equalsIgnoreCase("Y")) {
      this.boardDao.update(b);
      streamTool.println("변경했습니다.");
    } else {
      streamTool.println("변경 취소했습니다.");
    }
    streamTool.send();
  }

  private void deleteBoard(StreamTool streamTool) throws Exception {
    int boardNo = streamTool.promptInt("게시글 번호? ");

    Board b = this.boardDao.findByNo(boardNo);

    if (b == null) {
      streamTool.println("해당 번호의 게시글이 없습니다.").send();
      return;
    }

    String password = streamTool.promptString("암호? ");
    if (!b.getPassword().equals(password)) {
      streamTool.println("암호가 맞지 않습니다!").send();
      return;
    }

    String str = streamTool.promptString("정말 삭제하시겠습니까?(y/N) ");
    if (!str.equalsIgnoreCase("Y")) {
      streamTool.println("삭제 취소했습니다.").send();
      return;
    }

    this.boardDao.delete(boardNo);

    streamTool.println("삭제했습니다.").send();

  }

  private void searchBoard(StreamTool streamTool) throws Exception {
    String keyword = streamTool.promptString("검색어? ");

    List<Board> boards = this.boardDao.findByKeyword(keyword);

    streamTool.println("번호\t제목\t작성일\t조회수");
    for (Board b : boards) {
      streamTool.printf("%d\t%s\t%s\t%d\n",
          b.getNo(), b.getTitle(), b.getCreatedDate(), b.getViewCount());
    }
    streamTool.send();
  }

  void menu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    out.println("<ul>");
    out.println("  <li><a href='board/add'>등록</a></li>");
    out.println("  <li><a href='board/list'>목록</a></li>");
    out.println("  <li><a href='board/view'>조회</a></li>");
    out.println("  <li><a href='board/update'>변경</a></li>");
    out.println("  <li><a href='board/delete'>삭제</a></li>");
    out.println("  <li><a href='board/search'>검색</a></li>");
    out.println("</ul>");
    out.println("</body>");
    out.println("</html>");
  }
}
