package bitcamp.myapp.servlet.student;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.util.TransactionManager;

@WebServlet("/student/delete")
public class StudentDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private TransactionManager txManager;
  private MemberDao memberDao;
  private StudentDao studentDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    txManager = (TransactionManager) ctx.getAttribute("txManager");
    memberDao = (MemberDao) ctx.getAttribute("memberDao");
    studentDao = (StudentDao) ctx.getAttribute("studentDao");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int studentNo = Integer.parseInt(request.getParameter("no"));
    txManager.startTransaction();
    try {
      if (studentDao.delete(studentNo) == 1 &&
          memberDao.delete(studentNo) == 1) {
        txManager.commit();
      } else {
        request.setAttribute("error", "data");
      }
    } catch (Exception e) {
      txManager.rollback();
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    request.getRequestDispatcher("/student/delete.jsp").forward(request, response);
  }
}
