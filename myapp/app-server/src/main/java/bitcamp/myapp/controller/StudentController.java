package bitcamp.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;

@Controller
@RequestMapping("/student")
public class StudentController {

  @Autowired private StudentService studentService;

  @GetMapping("form")
  public String form() {
    return "/student/form.jsp";
  }

  @PostMapping("insert")
  public String insert(Student student, Model model) {
    try {
      studentService.add(student);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
    return "/student/insert.jsp";
  }

  @GetMapping("list")
  public String list(String keyword, Model model) {
    model.addAttribute("students", studentService.list(keyword));
    return "/student/list.jsp";
  }

  @GetMapping("view")
  public String view(
      int no,
      Model model) {
    model.addAttribute("student", studentService.get(no));
    return"/student/view.jsp";
  }

  @PostMapping("update")
  public String update(Student student, Model model) {
    try {
      studentService.update(student);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
    return "/student/update.jsp";
  }

  @PostMapping("delete")
  public String delete(int no, Model model) {
    try {
      studentService.delete(no);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
    return "/student/delete.jsp";
  }
}
