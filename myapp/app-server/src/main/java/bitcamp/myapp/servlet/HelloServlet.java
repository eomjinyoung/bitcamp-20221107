package bitcamp.myapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 이 클래스를 서블릿 컨테이너에 등록한다.
// 클라이언트에서 /hello URL로 요청을 했을 때 이 클래스를 실행한다.
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    System.out.println("HelloServlet.service() 호출됨3333!");
  }
}






