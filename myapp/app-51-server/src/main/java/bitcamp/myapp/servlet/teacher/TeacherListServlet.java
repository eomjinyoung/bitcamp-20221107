package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.TeacherService;

@WebServlet("/teacher/list")
public class TeacherListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private TeacherService teacherService;

  @Override
  public void init() {
    teacherService = (TeacherService) getServletContext().getAttribute("teacherService");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("teachers", teacherService.list());
    request.getRequestDispatcher("/teacher/list.jsp").forward(request, response);
  }
}
