package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.TeacherService;

public class TeacherListController implements PageController {

  private TeacherService teacherService;

  public TeacherListController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("teachers", teacherService.list());
    return "/teacher/list.jsp";
  }
}
