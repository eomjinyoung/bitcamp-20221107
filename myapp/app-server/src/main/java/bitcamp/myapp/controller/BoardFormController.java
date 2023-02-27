package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.util.RequestMapping;

@RequestMapping("/board/form")
public class BoardFormController implements PageController {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return "/board/form.jsp";
  }
}








