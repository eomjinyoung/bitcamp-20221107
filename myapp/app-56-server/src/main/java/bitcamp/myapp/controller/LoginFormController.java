package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;

@Controller
@RequestMapping("/auth/form")
public class LoginFormController implements PageController {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return "/auth/form.jsp";
  }
}








