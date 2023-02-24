package bitcamp.myapp.servlet.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;

@WebServlet("/student/view")
public class StudentViewServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private StudentService studentService;

  @Override
  public void init() {
    studentService = (StudentService) getServletContext().getAttribute("studentService");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setAttribute("student",
        studentService.get(Integer.parseInt(request.getParameter("no"))));
    request.getRequestDispatcher("/student/view.jsp").forward(request, response);
  }

}
