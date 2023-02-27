package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;

@Controller
public class TeacherController {

  private TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @RequestMapping("/teacher/form")
  public String form(HttpServletRequest request, HttpServletResponse response) {
    return "/teacher/form.jsp";
  }

  @RequestMapping("/teacher/insert")
  public String insert(HttpServletRequest request, HttpServletResponse response) {
    Teacher teacher = new Teacher();
    teacher.setName(request.getParameter("name"));
    teacher.setEmail(request.getParameter("email"));
    teacher.setPassword(request.getParameter("password"));
    teacher.setTel(request.getParameter("tel"));
    teacher.setDegree(Integer.parseInt(request.getParameter("degree")));
    teacher.setSchool(request.getParameter("school"));
    teacher.setMajor(request.getParameter("major"));
    teacher.setWage(Integer.parseInt(request.getParameter("wage")));

    try {
      teacherService.add(teacher);

    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/teacher/insert.jsp";
  }

  @RequestMapping("/teacher/list")
  public String list(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("teachers", teacherService.list());
    return "/teacher/list.jsp";
  }

  @RequestMapping("/teacher/view")
  public String view(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("teacher",
        teacherService.get(Integer.parseInt(request.getParameter("no"))));
    return "/teacher/view.jsp";
  }

  @RequestMapping("/teacher/update")
  public String update(HttpServletRequest request, HttpServletResponse response) {
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

    try {
      teacherService.update(teacher);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/teacher/update.jsp";
  }

  @RequestMapping("/teacher/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) {
    try {
      teacherService.delete(Integer.parseInt(request.getParameter("no")));
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/teacher/delete.jsp";
  }

}
