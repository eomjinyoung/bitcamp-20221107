package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import java.io.PrintWriter;
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


    txManager.startTransaction();
    try {
      if (memberDao.update(teacher) == 1 &&
          teacherDao.update(teacher) == 1) {
        txManager.commit();
        out.println("<p>변경했습니다.</p>");

      } else {
        out.println("<p>해당 번호의 강사가 없습니다.</p>");
      }
    } catch (Exception e) {
      txManager.rollback();
      out.println("<p>변경 실패입니다.</p>");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");

    response.setHeader("Refresh", "1;url=list");
  }

}
