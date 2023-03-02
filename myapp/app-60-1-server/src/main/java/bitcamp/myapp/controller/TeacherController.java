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

  @Autowired private TeacherService teacherService;

  @GetMapping("form")
  public String form() {
    return "/teacher/form.jsp";
  }

  @PostMapping("insert")
  public String insert(Teacher teacher, Model model) {
    try {
      teacherService.add(teacher);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
    return "/teacher/insert.jsp";
  }

  @GetMapping("list")
  public String list(Model model) {
    model.addAttribute("teachers", teacherService.list());
    return "/teacher/list.jsp";
  }

  @GetMapping("view")
  public String view(int no, Model model) {
    model.addAttribute("teacher", teacherService.get(no));
    return "/teacher/view.jsp";
  }

  @PostMapping("update")
  public String update(Teacher teacher, Model model) {
    try {
      teacherService.update(teacher);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
    return "/teacher/update.jsp";
  }

  @PostMapping("delete")
  public String delete(int no, Model model) {
    try {
      teacherService.delete(no);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "other");
    }
    return "/teacher/delete.jsp";
  }

}
