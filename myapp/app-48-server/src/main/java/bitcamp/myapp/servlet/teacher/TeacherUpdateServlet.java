package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.TransactionManager;

@WebServlet("/teacher/update")
public class TeacherUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private TransactionManager txManager;
  private MemberDao memberDao;
  private TeacherDao teacherDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    txManager = (TransactionManager) ctx.getAttribute("txManager");
    memberDao = (MemberDao) ctx.getAttribute("memberDao");
    teacherDao = (TeacherDao) ctx.getAttribute("teacherDao");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Teacher teacher = new Teacher();
    teacher.setNo(Integer.parseInt(request.getParameter("no")));
    teacher.setName(request.getParameter("name"));
    teacher.setEmail(request.getParameter("email"));
    teacher.setPassword(request.getParameter("password"));
    teacher.setTel(request.getParameter("tel"));
    teacher.setDegree(Integer.parseInt(request.getParameter("degree")));
    teacher.setSchool(request.getParameter("school"));
    teacher.setMajor(request.getParameter("major"));
    teacher.setWage(Integer.parseInt(request.getParameter("wage")));

    txManager.startTransaction();
    try {
      if (memberDao.update(teacher) == 1 &&
          teacherDao.update(teacher) == 1) {
        txManager.commit();

      } else {
        request.setAttribute("error", "data");
      }
    } catch (Exception e) {
      txManager.rollback();
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    request.getRequestDispatcher("/teacher/update.jsp").forward(request, response);
  }

}
