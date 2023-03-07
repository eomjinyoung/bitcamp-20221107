package bitcamp.myapp.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import bitcamp.myapp.vo.Member;

public class AdminCheckInterceptor implements HandlerInterceptor {

  Logger log = LogManager.getLogger(getClass());

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.trace("preHandle() 호출됨!");
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (!loginUser.getEmail().equals("admin@test.com")) {
      response.sendRedirect(request.getContextPath() + "/app/auth/form");
      return false;
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    log.trace("postHandle() 호출됨!");
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    log.trace("afterCompletion() 호출됨!");
  }
}






