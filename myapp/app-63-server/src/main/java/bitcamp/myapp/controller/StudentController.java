package bitcamp.myapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("StudentController 생성됨!");
  }

  @Autowired private StudentService studentService;

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("insert")
  public void insert(Student student, Model model) {
    studentService.add(student);
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
    studentService.update(student);
  }

  @PostMapping("delete")
  public void delete(int no, Model model) {
    studentService.delete(no);
  }


}
