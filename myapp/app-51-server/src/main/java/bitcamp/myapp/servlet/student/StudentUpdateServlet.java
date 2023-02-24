package bitcamp.myapp.servlet.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;

@WebServlet("/student/update")
public class StudentUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private StudentService studentService;

  @Override
  public void init() {
    studentService = (StudentService) getServletContext().getAttribute("studentService");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Student student = new Student();
    student.setNo(Integer.parseInt(request.getParameter("no")));
    student.setName(request.getParameter("name"));
    student.setEmail(request.getParameter("email"));
    student.setPassword(request.getParameter("password"));
    student.setTel(request.getParameter("tel"));
    student.setPostNo(request.getParameter("postNo"));
    student.setBasicAddress(request.getParameter("basicAddress"));
    student.setDetailAddress(request.getParameter("detailAddress"));
    student.setWorking(request.getParameter("working") != null);
    student.setGender(request.getParameter("gender").charAt(0));
    student.setLevel(Byte.parseByte(request.getParameter("level")));

    try {
      studentService.update(student);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    request.getRequestDispatcher("/student/update.jsp").forward(request, response);
  }

}
