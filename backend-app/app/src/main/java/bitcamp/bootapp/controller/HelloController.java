package bitcamp.bootapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 다음 클래스가 클라이언트 요청을 처리하는 일을 한다는 것을 SpringBoot 에게 알리는 표시!
// => SpringBoot는 다음 클래스의 인스턴스를 생성해서 보관해 둔다.
// => "/hello" 라는 URL로 클라이언트 요청이 들어오면 해당 메서드를 호출한다.
@RestController
public class HelloController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello, world!";
  }
}
