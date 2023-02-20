package bitcamp.myapp.servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.vo.Student;

@WebServlet("/student/view")
public class StudentViewServlet extends HttpServlet {
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

    int studentNo = Integer.parseInt(request.getParameter("no"));

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

    Student student = this.studentDao.findByNo(studentNo);

    if (student == null) {
      out.println("<p>해당 번호의 학생이 없습니다.</p>");

    } else {
      out.println("<form id='student-form' action='update' method='post'>");

      out.println("<table border='1'>");

      out.println("<tr>");
      out.println("  <th>번호</th>");
      out.printf("  <td><input type='text' name='no' value='%d'></td>\n",  student.getNo());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>이름</th>");
      out.printf("  <td><input type='text' name='name' value='%s'></td>\n",  student.getName());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>이메일</th>");
      out.printf("  <td><input type='email' name='email' value='%s'></td>\n",  student.getEmail());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>암호</th>");
      out.println("  <td><input type='password' name='password'></td>");
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>전화</th>");
      out.printf("  <td><input type='tel' name='tel' value='%s'></td>\n",  student.getTel());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>우편번호</th>");
      out.printf("  <td><input type='text' name='postNo' value='%s'></td>\n",  student.getPostNo());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>기본주소</th>");
      out.printf("  <td><input type='text' name='basicAddress' value='%s'></td>\n",  student.getBasicAddress());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>상세주소</th>");
      out.printf("  <td><input type='tel' name='detailAddress' value='%s'></td>\n",  student.getDetailAddress());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>재직여부</th>");
      out.printf("  <td><input type='checkbox' name='working' %s> 재직중</td>\n",  student.isWorking() ? "checked" : "");
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>성별</th>");
      out.printf("  <td><input type='radio' name='gender' value='M' %s> 남\n"
          + "<input type='radio' name='gender' value='W' %s> 여</td>\n"
          , student.getGender() == 'M' ? "checked" : ""
            , student.getGender() == 'W' ? "checked" : "");
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>전공</th>");
      out.printf("  <td><select name='level'>"
          + "<option value='0' %s>비전공자</option>"
          + "<option value='1' %s>준전공자</option>"
          + "<option value='2' %s>전공자</option>"
          + "</select></td>\n"
          , student.getLevel() == 0 ? "selected" : ""
            , student.getLevel() == 1 ? "selected" : ""
              , student.getLevel() == 2 ? "selected" : "");
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>등록일</th>");
      out.printf("  <td>%s</td>\n",  student.getCreatedDate());
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
    out.println("  var form = document.querySelector('#student-form');");
    out.println("  form.action = 'delete';");
    out.println("  form.submit();");
    out.println("}");
    out.println("</script>");

    out.println("</body>");
    out.println("</html>");

  }

}
