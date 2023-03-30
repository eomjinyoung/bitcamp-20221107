package bitcamp.myapp.controller;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Member;
import bitcamp.myapp.vo.Student;
import bitcamp.util.RestResult;
import bitcamp.util.RestStatus;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("AuthController 생성됨!");
  }

  @Autowired private StudentService studentService;
  @Autowired private TeacherService teacherService;

  @PostMapping("login")
  public Object login(
      String usertype,
      String email,
      String password,
      HttpSession session) {

    Member member = null;
    switch (usertype) {
      case "student":
        member = studentService.get(email, password);
        break;
      case "teacher":
        member = teacherService.get(email, password);
        break;
    }

    if (member != null) {
      session.setAttribute("loginUser", member);
      return new RestResult()
          .setStatus(RestStatus.SUCCESS);
    } else {
      return new RestResult()
          .setStatus(RestStatus.FAILURE);
    }
  }

  @GetMapping("logout")
  public Object logout(HttpSession session) {
    session.invalidate();
    return new RestResult()
        .setStatus(RestStatus.SUCCESS);
  }

  @RequestMapping("user")
  public Object user(HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      return new RestResult()
          .setStatus(RestStatus.SUCCESS)
          .setData(loginUser);
    } else {
      return new RestResult()
          .setStatus(RestStatus.FAILURE);
    }
  }

  @PostMapping("facebookLogin")
  public Object facebookLogin(
      @RequestBody Map<String,String> jsonData,
      HttpSession session) throws Exception {

    // 클라이언트가 보낸 accessToken을 가지고
    // 페이스북 서버에 접속(AJAX 요청)하여 사용자를 정보를 가져온다.
    //
    RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("rawtypes")
    Map result = restTemplate.getForObject(
        "https://graph.facebook.com/v16.0/me?access_token={value1}&fields={value2}", // 요청할 URL
        Map.class, // 서버에서 받은 결과의 타입
        jsonData.get("accessToken"), // URL의 첫 번째 자리에 들어갈 값
        "id,name,email,gender" // 페이스북 측에 요청하는 로그인 사용자 정보
        );

    // 페이스북에서 받은 데이터에서 이메일과 이름을 꺼낸다.
    @SuppressWarnings("null")
    String email = (String) result.get("email");
    String name = (String) result.get("name");

    // 기존 회원 정보 가져오기
    Student user = studentService.get(email);
    if (user == null) {
      // 페이스북에서 받은 최소 정보를 가지고 회원 가입을 위한 객체를 준비한다.
      Student s = new Student();
      s.setEmail(email);
      s.setName(name);
      s.setPassword("bitcamp-nopassword");

      // 회원 가입을 수행한다.
      studentService.add(s);
    }
    user = studentService.get(email);

    // 세션에 로그인 사용자 정보 보관
    session.setAttribute("loginUser", user);

    return new RestResult()
        .setStatus(RestStatus.SUCCESS);
  }

}









