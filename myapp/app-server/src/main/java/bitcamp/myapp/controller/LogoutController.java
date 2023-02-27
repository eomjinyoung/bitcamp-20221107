package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutController implements PageController {

  public static String path = "/auth/logout";
  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.getSession().invalidate();
    return "redirect:../";
  }

}









