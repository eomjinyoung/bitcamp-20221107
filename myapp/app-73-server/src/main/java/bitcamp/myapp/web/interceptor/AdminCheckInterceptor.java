package bitcamp.myapp.web.interceptor;

import java.io.PrintWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;
import bitcamp.myapp.vo.Member;
import bitcamp.util.ErrorCode;
import bitcamp.util.RestResult;
import bitcamp.util.RestStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminCheckInterceptor implements HandlerInterceptor {

  Logger log = LogManager.getLogger(getClass());

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.trace("preHandle() 호출됨!");

    if (request.getMethod().equals("GET")) {
      return true;
    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (!loginUser.getEmail().equals("admin@test.com")) {
      response.setContentType("application/json;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.print(new ObjectMapper().writeValueAsString(
          new RestResult()
          .setStatus(RestStatus.FAILURE)
          .setErrorCode(ErrorCode.rest.UNAUTHORIZED)
          .setData("권한이 없습니다.")));
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






