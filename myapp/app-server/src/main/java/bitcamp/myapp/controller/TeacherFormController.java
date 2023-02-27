package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TeacherFormController implements PageController {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return "/teacher/form.jsp";
  }
}
