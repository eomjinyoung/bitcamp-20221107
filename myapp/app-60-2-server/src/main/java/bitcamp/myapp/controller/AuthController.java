package bitcamp.myapp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Member;

@Controller
public class AuthController {

  @Autowired private StudentService studentService;
  @Autowired private TeacherService teacherService;

  @RequestMapping("/auth/form")
  public String form() {
    return "auth/form";
  }

  @RequestMapping("/auth/login")
  public String login(
      @RequestParam("usertype") String usertype,
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("saveEmail") String saveEmail,
      HttpServletRequest request,
      HttpServletResponse response,
      HttpSession session) {

    if (saveEmail != null) {
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
      session.setAttribute("loginUser", member);
      return "redirect:../../";
    } else {
      request.setAttribute("error", "loginfail");
      return "auth/form";
    }

  }

  @RequestMapping("/auth/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:../../";
  }

  @RequestMapping("/auth/fail")
  public String fail() {
    return "auth/fail";
  }

}









