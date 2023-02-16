package bitcamp.myapp.servlet;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

// 이 클래스를 서블릿 컨테이너에 등록한다.
// 클라이언트에서 /hello URL로 요청을 했을 때 이 클래스를 실행한다.
@WebServlet("/hello")
public class HelloServlet implements Servlet {

  ServletConfig config;

  @Override
  public void init(ServletConfig config) throws ServletException {
    System.out.println("HelloServlet.init() 호출됨!");
    this.config = config;
  }

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    System.out.println("HelloServlet.service() 호출됨!");
  }

  @Override
  public void destroy() {
    System.out.println("HelloServlet.destroy() 호출됨!");
  }

  @Override
  public String getServletInfo() {
    return "HelloServlet !!!";
  }

  @Override
  public ServletConfig getServletConfig() {
    return this.config;
  }
}






