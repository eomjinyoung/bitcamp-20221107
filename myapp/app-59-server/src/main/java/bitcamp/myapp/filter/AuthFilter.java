package bitcamp.myapp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Member;

@WebFilter("/*")
public class AuthFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String contextRoot = httpRequest.getContextPath();
    String servletPath = httpRequest.getServletPath().toLowerCase();

    if (servletPath.endsWith("insert") ||
        servletPath.endsWith("update") ||
        servletPath.endsWith("delete")) {
      // 로그인 사용자의 정보를 가져온다.
      Member loginUser = (Member) httpRequest.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        httpResponse.sendRedirect(contextRoot + "/auth/form");
        return;
      }
    }

    chain.doFilter(request, response); // 다음 필터 실행. 다음 필터가 없으면 최종 목적지인 서블릿 실행.
  }
}










