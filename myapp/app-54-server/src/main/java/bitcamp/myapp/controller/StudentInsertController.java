package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;

public class StudentInsertController implements PageController {

  public static String path = "/student/insert";
  private StudentService studentService;

  public StudentInsertController(StudentService studentService) {
    this.studentService = studentService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    Student student = new Student();
    student.setName(request.getParameter("name"));
    student.setEmail(request.getParameter("email"));
    student.setPassword(request.getParameter("password"));
    student.setTel(request.getParameter("tel"));
    student.setPostNo(request.getParameter("postNo"));
    student.setBasicAddress(request.getParameter("basicAddress"));
    student.setDetailAddress(request.getParameter("detailAddress"));
    student.setWorking(request.getParameter("working") != null);
    student.setGender(request.getParameter("gender").charAt(0));
    student.setLevel(Byte.parseByte(request.getParameter("level")));

    try {
      studentService.add(student);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/student/insert.jsp";
  }

}
