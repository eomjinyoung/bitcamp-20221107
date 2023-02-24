package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Teacher;

@WebServlet("/teacher/insert")
public class TeacherInsertServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private TeacherService teacherService;

  @Override
  public void init() {
    teacherService = (TeacherService) getServletContext().getAttribute("teacherService");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Teacher teacher = new Teacher();
    teacher.setName(request.getParameter("name"));
    teacher.setEmail(request.getParameter("email"));
    teacher.setPassword(request.getParameter("password"));
    teacher.setTel(request.getParameter("tel"));
    teacher.setDegree(Integer.parseInt(request.getParameter("degree")));
    teacher.setSchool(request.getParameter("school"));
    teacher.setMajor(request.getParameter("major"));
    teacher.setWage(Integer.parseInt(request.getParameter("wage")));

    try {
      teacherService.add(teacher);

    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    request.setAttribute("view", "/teacher/insert.jsp");
  }

}
