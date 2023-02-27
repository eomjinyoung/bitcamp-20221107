package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardFormController implements PageController {

  public static String path = "/board/form";

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return "/board/form.jsp";
  }
}








