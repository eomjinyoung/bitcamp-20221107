package bitcamp.myapp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;

@Controller
public class AuthController {

  private StudentService studentService;
  private TeacherService teacherService;

  public AuthController(StudentService studentService, TeacherService teacherService) {
    this.studentService = studentService;
    this.teacherService = teacherService;
  }

  @RequestMapping("/auth/form")
  public String form(HttpServletRequest request, HttpServletResponse response) {
    return "/auth/form.jsp";
  }

  @RequestMapping("/auth/login")
  public String login(HttpServletRequest request, HttpServletResponse response) {

    String usertype = request.getParameter("usertype");
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    if (request.getParameter("saveEmail") != null) {
      Cookie cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 30); // 30일 동안 유지
      response.addCookie(cookie);

    } else {
      Cookie cookie = new Cookie("email", "");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

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
      return "redirect:../";
    } else {
      request.setAttribute("error", "loginfail");
      return "/auth/form.jsp";
    }

  }

  @RequestMapping("/auth/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    request.getSession().invalidate();
    return "redirect:../";
  }

  @RequestMapping("/auth/fail")
  public String fail(HttpServletRequest request, HttpServletResponse response) {
    return "/auth/fail.jsp";
  }

}









