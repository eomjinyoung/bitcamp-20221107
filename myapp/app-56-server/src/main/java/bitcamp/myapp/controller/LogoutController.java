package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;

@Controller
@RequestMapping("/auth/logout")
public class LogoutController implements PageController {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.getSession().invalidate();
    return "redirect:../";
  }

}









