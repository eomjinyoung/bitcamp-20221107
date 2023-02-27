package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentFormController implements PageController {

  public static String path = "/student/form";

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return "/student/form.jsp";
  }
}
