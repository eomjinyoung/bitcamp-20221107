package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;

@WebServlet("/teacher/view")
public class TeacherViewServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private TeacherDao teacherDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    teacherDao = (TeacherDao) ctx.getAttribute("teacherDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int teacherNo = Integer.parseInt(request.getParameter("no"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프 - NCP 1기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>강사</h1>");

    Teacher teacher = this.teacherDao.findByNo(teacherNo);

    if (teacher == null) {
      out.println("<p>해당 번호의 강사가 없습니다.</p>");

    } else {
      out.println("<form id='teacher-form' action='update' method='post'>");

      out.println("<table border='1'>");

      out.println("<tr>");
      out.println("  <th>번호</th>");
      out.printf("  <td><input type='text' name='no' value='%d'></td>\n",  teacher.getNo());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>이름</th>");
      out.printf("  <td><input type='text' name='name' value='%s'></td>\n",  teacher.getName());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>이메일</th>");
      out.printf("  <td><input type='email' name='email' value='%s'></td>\n",  teacher.getEmail());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>암호</th>");
      out.println("  <td><input type='password' name='password'></td>");
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>전화</th>");
      out.printf("  <td><input type='tel' name='tel' value='%s'></td>\n",  teacher.getTel());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>학위</th>");
      out.printf("  <td><select name='degree'>\n"
          + "<option value='1' %s>고졸</option>\n"
          + "<option value='2' %s>전문학사</option>\n"
          + "<option value='3' %s>학사</option>\n"
          + "<option value='4' %s>석사</option>\n"
          + "<option value='5' %s>박사</option>\n"
          + "<option value='0' %s>기타</option>\n"
          + "</select></td>\n"
          , teacher.getDegree() == 1 ? "selected" : ""
            , teacher.getDegree() == 2 ? "selected" : ""
              , teacher.getDegree() == 3 ? "selected" : ""
                , teacher.getDegree() == 4 ? "selected" : ""
                  , teacher.getDegree() == 5 ? "selected" : ""
                    , teacher.getDegree() == 0 ? "selected" : "");
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>학교</th>");
      out.printf("  <td><input type='text' name='school' value='%s'></td>\n", teacher.getSchool());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>전공</th>");
      out.printf("  <td><input type='text' name='major' value='%s'></td>\n", teacher.getMajor());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>강의료(시급)</th>");
      out.printf("  <td><input type='number' name='wage' value='%s'></td>\n", teacher.getWage());
      out.println("</tr>");

      out.println("<tr>");
      out.println("  <th>등록일</th>");
      out.printf("  <td>%s</td>\n",  teacher.getCreatedDate());
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
    out.println("  var form = document.querySelector('#teacher-form');");
    out.println("  form.action = 'delete';");
    out.println("  form.submit();");
    out.println("}");
    out.println("</script>");

    out.println("</body>");
    out.println("</html>");

  }

}
