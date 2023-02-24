package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.TeacherService;

@WebServlet("/teacher/view")
public class TeacherViewServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private TeacherService teacherService;

  @Override
  public void init() {
    teacherService = (TeacherService) getServletContext().getAttribute("teacherService");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setAttribute("teacher",
        teacherService.get(Integer.parseInt(request.getParameter("no"))));
    request.getRequestDispatcher("/teacher/view.jsp").forward(request, response);
  }

}
