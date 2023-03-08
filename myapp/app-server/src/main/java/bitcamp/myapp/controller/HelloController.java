package bitcamp.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

  @GetMapping(value = "/hello", produces = "text/plain;charset=UTF-8")
  @ResponseBody
  public String hello() throws Exception {
    Thread.sleep(5000);
    return "Hello, world! (안녕!)";
  }
}
