package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.TeacherService;

public class TeacherViewController implements PageController {

  private TeacherService teacherService;

  public TeacherViewController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("teacher",
        teacherService.get(Integer.parseInt(request.getParameter("no"))));
    return "/teacher/view.jsp";
  }

}
