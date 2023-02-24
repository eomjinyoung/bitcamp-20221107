package bitcamp.myapp.servlet.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private StudentService studentService;
  private TeacherService teacherService;

  @Override
  public void init() {
    studentService = (StudentService) getServletContext().getAttribute("studentService");
    teacherService = (TeacherService) getServletContext().getAttribute("teacherService");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String usertype = request.getParameter("usertype");
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    List<Cookie> cookies = new ArrayList<>();
    if (request.getParameter("saveEmail") != null) {
      Cookie cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 30); // 30일 동안 유지

      // include 되는 서블릿에서는 응답 헤더를 변경할 수 없다.
      // 따라서 응답헤더에 쿠키를 추가할 수 없다.
      // response.addCookie(cookie);
      cookies.add(cookie);

    } else {
      Cookie cookie = new Cookie("email", "");
      cookie.setMaxAge(0);
      //response.addCookie(cookie);
      cookies.add(cookie);
    }
    request.setAttribute("cookies", cookies);

    Member member = null;
    switch (usertype) {
      case "student":
        member = studentService.get(email, password);
        break;
      case "teacher":
        member = teacherService.get(email, password);
        break;
    }

    if (member != null) {
      HttpSession session = request.getSession();
      session.setAttribute("loginUser", member);
      request.setAttribute("view", "redirect:../");
    } else {
      request.setAttribute("error", "loginfail");
      request.setAttribute("view", "/auth/form.jsp");
    }

  }

}









