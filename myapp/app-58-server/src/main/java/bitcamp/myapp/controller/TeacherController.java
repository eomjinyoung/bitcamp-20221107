package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;
import bitcamp.util.RequestParam;

@Controller
public class TeacherController {

  private TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @RequestMapping("/teacher/form")
  public String form() {
    return "/teacher/form.jsp";
  }

  @RequestMapping("/teacher/insert")
  public String insert(
      @RequestParam("name") String name,
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("tel") String tel,
      @RequestParam("degree") int degree,
      @RequestParam("school") String school,
      @RequestParam("major") String major,
      @RequestParam("wage") int wage,
      HttpServletRequest request) {

    Teacher teacher = new Teacher();
    teacher.setName(name);
    teacher.setEmail(email);
    teacher.setPassword(password);
    teacher.setTel(tel);
    teacher.setDegree(degree);
    teacher.setSchool(school);
    teacher.setMajor(major);
    teacher.setWage(wage);

    try {
      teacherService.add(teacher);

    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/teacher/insert.jsp";
  }

  @RequestMapping("/teacher/list")
  public String list(HttpServletRequest request) {
    request.setAttribute("teachers", teacherService.list());
    return "/teacher/list.jsp";
  }

  @RequestMapping("/teacher/view")
  public String view(
      @RequestParam("no") int no,
      HttpServletRequest request, HttpServletResponse response) {

    request.setAttribute("teacher", teacherService.get(no));
    return "/teacher/view.jsp";
  }

  @RequestMapping("/teacher/update")
  public String update(
      @RequestParam("no") int no,
      @RequestParam("name") String name,
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("tel") String tel,
      @RequestParam("degree") int degree,
      @RequestParam("school") String school,
      @RequestParam("major") String major,
      @RequestParam("wage") int wage,
      HttpServletRequest request) {

    Teacher teacher = new Teacher();
    teacher.setNo(no);
    teacher.setName(name);
    teacher.setEmail(email);
    teacher.setPassword(password);
    teacher.setTel(tel);
    teacher.setDegree(degree);
    teacher.setSchool(school);
    teacher.setMajor(major);
    teacher.setWage(wage);

    try {
      teacherService.update(teacher);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/teacher/update.jsp";
  }

  @RequestMapping("/teacher/delete")
  public String delete(
      @RequestParam("no") int no,
      HttpServletRequest request) {
    try {
      teacherService.delete(Integer.parseInt(request.getParameter("no")));
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/teacher/delete.jsp";
  }

}
