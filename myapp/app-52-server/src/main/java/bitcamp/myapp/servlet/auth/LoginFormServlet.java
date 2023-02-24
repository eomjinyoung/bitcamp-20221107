package bitcamp.myapp.servlet.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth/form")
public class LoginFormServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 웹브라우저가 보낸 email 쿠키 꺼내기
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("email")) {
          request.setAttribute("email", cookie.getValue());
          // => JSP에서는 ${email}로 값을 꺼낸다.
          break;
        }
      }
    }
    request.setAttribute("view", "/auth/form.jsp");
  }
}








