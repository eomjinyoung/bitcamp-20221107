package bitcamp.myapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import bitcamp.util.ErrorCode;
import bitcamp.util.RestResult;
import bitcamp.util.RestStatus;
import jakarta.servlet.http.HttpServletRequest;

// 페이지 컨트롤러의 공통 설정을 수행하는 클래스
@ControllerAdvice
public class GlobalControllerAdvice {

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("GlobalControllerAdvice 생성!");
  }

  @ExceptionHandler
  @ResponseBody
  public Object handle(Exception e, HttpServletRequest request) {
    log.error(request.getRequestURI() + " 요청 처리 중 오류 발생!", e);

    return new RestResult()
        .setStatus(RestStatus.FAILURE)
        .setErrorCode(ErrorCode.rest.CONTROLLER_EXCEPTION)
        .setData(String.format("url: %s\nclass: %s\nmessage: %s",
            request.getRequestURI(),
            e.getClass().getName(),
            e.getMessage()));
  }
}
