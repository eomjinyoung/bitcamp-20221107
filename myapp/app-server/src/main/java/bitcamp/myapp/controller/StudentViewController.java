package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;

public class StudentViewController implements PageController {

  private StudentService studentService;

  public StudentViewController(StudentService studentService) {
    this.studentService = studentService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {

    request.setAttribute("student",
        studentService.get(Integer.parseInt(request.getParameter("no"))));
    return"/student/view.jsp";
  }

}
