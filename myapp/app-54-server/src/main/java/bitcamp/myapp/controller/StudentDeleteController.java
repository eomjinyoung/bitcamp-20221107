package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;

public class StudentDeleteController implements PageController {

  public static String path = "/student/delete";
  private StudentService studentService;

  public StudentDeleteController(StudentService studentService) {
    this.studentService = studentService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    try {
      studentService.delete(Integer.parseInt(request.getParameter("no")));
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/student/delete.jsp";
  }
}
