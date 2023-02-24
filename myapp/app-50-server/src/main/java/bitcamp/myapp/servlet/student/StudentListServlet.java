package bitcamp.myapp.servlet.student;

import java.io.IOException;
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
    String keyword = request.getParameter("keyword");
    List<Student> students = this.studentDao.findAll(keyword);
    request.setAttribute("students", students);
    request.getRequestDispatcher("/student/list.jsp").forward(request, response);
  }

}
