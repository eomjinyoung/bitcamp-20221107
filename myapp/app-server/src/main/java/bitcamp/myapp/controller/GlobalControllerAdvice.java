package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 페이지 컨트롤러의 공통 설정을 수행하는 클래스
@ControllerAdvice
public class GlobalControllerAdvice {
  {
    System.out.println("GlobalControllerAdvice 생성!");
  }

  @ExceptionHandler
  public String handle(Exception e, HttpServletRequest request, Model model) {
    e.printStackTrace();
    model.addAttribute("url", request.getRequestURI());
    model.addAttribute("class", e.getClass().getName());
    model.addAttribute("message", e.getMessage());
    return "error";
  }
}
