package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;

@Controller
@RequestMapping("/teacher/form")
public class TeacherFormController implements PageController {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return "/teacher/form.jsp";
  }
}
