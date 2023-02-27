package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;

@Controller
public class StudentController {

  private StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @RequestMapping("/student/form")
  public String form(HttpServletRequest request, HttpServletResponse response) {
    return "/student/form.jsp";
  }

  @RequestMapping("/student/insert")
  public String insert(HttpServletRequest request, HttpServletResponse response) {
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

  @RequestMapping("/student/list")
  public String list(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("students", studentService.list(request.getParameter("keyword")));
    return "/student/list.jsp";
  }

  @RequestMapping("/student/view")
  public String view(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("student",
        studentService.get(Integer.parseInt(request.getParameter("no"))));
    return"/student/view.jsp";
  }

  @RequestMapping("/student/update")
  public String update(HttpServletRequest request, HttpServletResponse response) {
    Student student = new Student();
    student.setNo(Integer.parseInt(request.getParameter("no")));
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
      studentService.update(student);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/student/update.jsp";
  }

  @RequestMapping("/student/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) {
    try {
      studentService.delete(Integer.parseInt(request.getParameter("no")));
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/student/delete.jsp";
  }
}
