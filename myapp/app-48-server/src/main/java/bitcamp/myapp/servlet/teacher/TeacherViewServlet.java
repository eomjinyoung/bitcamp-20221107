package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
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
    Teacher teacher = this.teacherDao.findByNo(teacherNo);
    if (teacher != null) {
      request.setAttribute("teacher", teacher);
    }
    request.getRequestDispatcher("/teacher/view.jsp").forward(request, response);
  }

}
