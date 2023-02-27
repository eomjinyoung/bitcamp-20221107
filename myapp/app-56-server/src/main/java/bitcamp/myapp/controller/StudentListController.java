package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;

@Controller
@RequestMapping("/student/list")
public class StudentListController implements PageController {

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
