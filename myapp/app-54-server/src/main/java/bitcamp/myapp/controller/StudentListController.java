package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;

public class StudentListController implements PageController {

  public static String path = "/student/list";
  private StudentService studentService;

  public StudentListController(StudentService studentService) {
    this.studentService = studentService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("students", studentService.list(request.getParameter("keyword")));
    return "/student/list.jsp";
  }

}
