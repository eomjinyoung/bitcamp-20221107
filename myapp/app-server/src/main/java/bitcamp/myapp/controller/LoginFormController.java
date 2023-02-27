package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.util.RequestMapping;

@RequestMapping("/auth/form")
public class LoginFormController implements PageController {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return "/auth/form.jsp";
  }
}








