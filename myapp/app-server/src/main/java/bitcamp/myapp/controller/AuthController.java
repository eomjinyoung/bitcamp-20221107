package bitcamp.myapp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Member;
import bitcamp.util.RestResult;
import bitcamp.util.RestStatus;

@Controller
public class AuthController {

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("AuthController 생성됨!");
  }

  @Autowired private StudentService studentService;
  @Autowired private TeacherService teacherService;

  @GetMapping("/auth/form")
  public void form() {
  }

  @RequestMapping("/auth/login")
  public String login(
      String usertype,
      String email,
      String password,
      String saveEmail,
      HttpServletResponse response,
      HttpSession session,
      Model model) {

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
      session.removeAttribute("error");
      return "redirect:../../";
    } else {
      session.setAttribute("error", "loginfail");
      return "redirect:form";
    }

  }

  @RequestMapping("/auth/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:../../";
  }

  @RequestMapping("/auth/user")
  @ResponseBody
  public Object user(HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      return new RestResult()
          .setStatus(RestStatus.SUCCESS)
          .setData(loginUser);
    } else {
      return new RestResult()
          .setStatus(RestStatus.FAILURE);
    }
  }

}









