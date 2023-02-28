package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;

@Controller
public class StudentController {

  @Autowired private StudentService studentService;

  @RequestMapping("/student/form")
  public String form() {
    return "/student/form.jsp";
  }

  @RequestMapping("/student/insert")
  public String insert(
      @RequestParam("name") String name,
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("tel") String tel,
      @RequestParam("postNo") String postNo,
      @RequestParam("basicAddress") String basicAddress,
      @RequestParam("detailAddress") String detailAddress,
      @RequestParam("working") boolean working,
      @RequestParam("gender") char gender,
      @RequestParam("level") byte level,
      HttpServletRequest request) {

    Student student = new Student();
    student.setName(name);
    student.setEmail(email);
    student.setPassword(password);
    student.setTel(tel);
    student.setPostNo(postNo);
    student.setBasicAddress(basicAddress);
    student.setDetailAddress(detailAddress);
    student.setWorking(working);
    student.setGender(gender);
    student.setLevel(level);

    try {
      studentService.add(student);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/student/insert.jsp";
  }

  @RequestMapping("/student/list")
  public String list(
      @RequestParam("keyword") String keyword,
      HttpServletRequest request) {
    request.setAttribute("students", studentService.list(keyword));
    return "/student/list.jsp";
  }

  @RequestMapping("/student/view")
  public String view(
      @RequestParam("no") int no,
      HttpServletRequest request) {
    request.setAttribute("student", studentService.get(no));
    return"/student/view.jsp";
  }

  @RequestMapping("/student/update")
  public String update(
      @RequestParam("no") int no,
      @RequestParam("name") String name,
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("tel") String tel,
      @RequestParam("postNo") String postNo,
      @RequestParam("basicAddress") String basicAddress,
      @RequestParam("detailAddress") String detailAddress,
      @RequestParam("working") boolean working,
      @RequestParam("gender") char gender,
      @RequestParam("level") byte level,
      HttpServletRequest request) {

    Student student = new Student();
    student.setNo(no);
    student.setName(name);
    student.setEmail(email);
    student.setPassword(password);
    student.setTel(tel);
    student.setPostNo(postNo);
    student.setBasicAddress(basicAddress);
    student.setDetailAddress(detailAddress);
    student.setWorking(working);
    student.setGender(gender);
    student.setLevel(level);

    try {
      studentService.update(student);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/student/update.jsp";
  }

  @RequestMapping("/student/delete")
  public String delete(
      @RequestParam("no") int no,
      HttpServletRequest request) {
    try {
      studentService.delete(no);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "other");
    }
    return "/student/delete.jsp";
  }
}
