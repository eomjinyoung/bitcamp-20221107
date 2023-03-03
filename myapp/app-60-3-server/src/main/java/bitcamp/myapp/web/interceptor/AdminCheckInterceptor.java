package bitcamp.myapp.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import bitcamp.myapp.vo.Member;

public class AdminCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (!loginUser.getEmail().equals("admin@test.com")) {
      response.sendRedirect(request.getContextPath() + "/app/auth/form");
      return false;
    }
    return true;
  }
}






