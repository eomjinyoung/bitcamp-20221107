package bitcamp.myapp.servlet.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private StudentDao studentDao;
  private TeacherDao teacherDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    studentDao = (StudentDao) ctx.getAttribute("studentDao");
    teacherDao = (TeacherDao) ctx.getAttribute("teacherDao");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String usertype = request.getParameter("usertype");

    Map<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", request.getParameter("email"));
    paramMap.put("password", request.getParameter("password"));

    Member member = null;

    switch (usertype) {
      case "student":
        member = studentDao.findByEmailAndPassword(paramMap);
        break;
      case "teacher":
        member = teacherDao.findByEmailAndPassword(paramMap);
        break;
    }

    if (member != null) {

      // 로그인 사용자 정보를 저장할 세션 객체를 준비한다.
      // 1) 요청 프로토콜에 세션ID가 쿠키로 넘어 왔다면,
      //    - 세션ID에 해당하는 객체를 찾는다.
      //      1-1) 세션 객체가 유효한 경우,
      //         - 찾은 세션 객체를 리턴한다.
      //         - 응답할 때 세션ID를 쿠키로 전달하지 않는다.
      //      1-2) 세션 객체가 타임아웃 되어 유효하지 않은 경우,
      //         - 2) 를 수행한다.
      // 2) 요청 프로토콜에 세션ID가 쿠키로 넘어오지 않았다면,
      //    - 새 세션 객체를 생성한 후 리턴한다.
      //    - 응답할 때 새로 생성한 세션의 ID를 쿠키로 웹브라우저에게 전달한다.
      //
      HttpSession session = request.getSession();

      // 로그인 사용자 정보를 세션에 보관한다.
      session.setAttribute("loginUser", member);

      response.sendRedirect("../"); // ===> http://localhost:8080/web/
      // 웹브라우저에게 전달하는 URL이다.
      // 응답 프로토콜 예:
      //     HTTP/1.1 302
      //     Location: /index.html
      //     ...
      //
    } else {
      request.setAttribute("error", "loginfail");
      request.getRequestDispatcher("/auth/form.jsp").forward(request, response);
    }

  }

}









