package bitcamp.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Teacher;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

  {
    System.out.println("TeacherController 생성됨!");
  }

  @Autowired private TeacherService teacherService;

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("insert")
  public void insert(Teacher teacher, Model model) {
    try {
      teacherService.add(teacher);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
  }

  @GetMapping("list")
  public void list(Model model) {
    model.addAttribute("teachers", teacherService.list());
  }

  @GetMapping("view")
  public void view(int no, Model model) {
    model.addAttribute("teacher", teacherService.get(no));
  }

  @PostMapping("update")
  public void update(Teacher teacher, Model model) {
    try {
      teacherService.update(teacher);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
  }

  @PostMapping("delete")
  public void delete(int no, Model model) {
    try {
      teacherService.delete(no);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
  }

}
