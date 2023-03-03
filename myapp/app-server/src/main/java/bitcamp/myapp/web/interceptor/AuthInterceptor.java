package bitcamp.myapp.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import bitcamp.myapp.vo.Member;

public class AuthInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect(request.getContextPath() + "/app/auth/form");
      return false;
    }
    return true;
  }
}






