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

  {
    System.out.println("StudentController 생성됨!");
  }

  @Autowired private StudentService studentService;

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("insert")
  public void insert(Student student, Model model) {
    try {
      studentService.add(student);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
  }

  @GetMapping("list")
  public void list(String keyword, Model model) {
    model.addAttribute("students", studentService.list(keyword));
  }

  @GetMapping("view")
  public void view(
      int no,
      Model model) {
    model.addAttribute("student", studentService.get(no));
  }

  @PostMapping("update")
  public void update(Student student, Model model) {
    try {
      studentService.update(student);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
  }

  @PostMapping("delete")
  public void delete(int no, Model model) {
    try {
      studentService.delete(no);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
  }
}
