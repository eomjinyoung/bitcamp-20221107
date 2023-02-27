package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFormController implements PageController {

  public static String path = "/auth/form";
  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return "/auth/form.jsp";
  }
}








