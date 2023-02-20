package bitcamp.myapp.servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.vo.Student;

@WebServlet("/student/list")
public class StudentListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private StudentDao studentDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    studentDao = (StudentDao) ctx.getAttribute("studentDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
    out.println("<h1>학생</h1>");

    out.println("<div><a href='form'>새 학생</a></div>");

    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("  <th>번호</th> <th>이름</th> <th>전화</th> <th>재직</th> <th>전공</th>");
    out.println("</tr>");

    String keyword = request.getParameter("keyword");
    List<Student> students = this.studentDao.findAll(keyword);

    for (Student student : students) {
      out.println("<tr>");
      out.printf("  <td>%d</td> <td><a href='view?no=%d'>%s</a></td> <td>%s</td> <td>%s</td> <td>%s</td>\n",
          student.getNo(),
          student.getNo(),
          student.getName(),
          student.getTel(),
          student.isWorking() ? "예" : "아니오",
              getLevelText(student.getLevel())    );
      out.println("</tr>");
    }
    out.println("</table>");

    out.println("<form action='list' method='get'>");
    out.printf("<input type='text' name='keyword' value='%s'>\n", keyword != null ? keyword : "");
    out.println("<button>검색</button>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }

  private static String getLevelText(int level) {
    switch (level) {
      case 0: return "비전공자";
      case 1: return "준전공자";
      default: return "전공자";
    }
  }
}
