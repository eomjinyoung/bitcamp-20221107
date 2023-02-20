package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/teacher/form")
public class TeacherFormServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

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
    out.println("<h1>강사</h1>");
    out.println("<form action='insert' method='post'>");
    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("  <th>이름</th>");
    out.println("  <td><input type='text' name='name'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("  <th>이메일</th>");
    out.println("  <td><input type='email' name='email'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("  <th>암호</th>");
    out.println("  <td><input type='password' name='password'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("  <th>전화</th>");
    out.println("  <td><input type='tel' name='tel'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("  <th>학위</th>");
    out.println("  <td><select name='degree'>\n"
        + "<option value='1'>고졸</option>\n"
        + "<option value='2'>전문학사</option>\n"
        + "<option value='3'>학사</option>\n"
        + "<option value='4'>석사</option>\n"
        + "<option value='5'>박사</option>\n"
        + "<option value='0'>기타</option>\n"
        + "</select></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("  <th>학교</th>");
    out.println("  <td><input type='text' name='school'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("  <th>전공</th>");
    out.println("  <td><input type='text' name='major'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("  <th>강의료(시급)</th>");
    out.println("  <td><input type='number' name='wage'></td>");
    out.println("</tr>");

    out.println("</table>");

    out.println("<div>");
    out.println("  <button>등록</button>");
    out.println("  <button id='btn-cancel' type='button'>취소</button>");
    out.println("</div>");

    out.println("</form>");

    out.println("<script>");
    out.println("document.querySelector('#btn-cancel').onclick = function() {");
    out.println("  location.href = 'list';");
    out.println("}");
    out.println("</script>");

    out.println("</body>");
    out.println("</html>");

  }
}
