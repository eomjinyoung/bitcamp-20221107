package bitcamp.myapp.servlet.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private StudentDao studentDao;
  private TeacherDao teacherDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    studentDao = (StudentDao) ctx.getAttribute("studentDao");
    teacherDao = (TeacherDao) ctx.getAttribute("teacherDao");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String usertype = request.getParameter("usertype");

    Map<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", request.getParameter("email"));
    paramMap.put("password", request.getParameter("password"));

    Member member = null;

    switch (usertype) {
      case "student":
        member = studentDao.findByEmailAndPassword(paramMap);
        break;
      case "teacher":
        member = teacherDao.findByEmailAndPassword(paramMap);
        break;
    }

    if (member != null) {
      response.sendRedirect("../index.html");
      // 웹브라우저에게 전달하는 URL이다.
      // 응답 프로토콜 예:
      //     HTTP/1.1 302
      //     Location: /index.html
      //     ...
      //
    } else {
      request.setAttribute("error", "loginfail");
      request.getRequestDispatcher("/auth/form.jsp").forward(request, response);
    }

  }

}









