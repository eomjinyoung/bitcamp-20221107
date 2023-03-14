package bitcamp.myapp.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import bitcamp.myapp.controller.AuthController;
import bitcamp.myapp.controller.BoardController;
import bitcamp.myapp.controller.DownloadController;
import bitcamp.myapp.web.interceptor.AdminCheckInterceptor;
import bitcamp.myapp.web.interceptor.AuthInterceptor;

//@Configuration

@ComponentScan(
    value = "bitcamp.myapp.controller",
    excludeFilters = {
        @Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = {AuthController.class, BoardController.class, DownloadController.class})
    })
@EnableWebMvc // 프론트 컨트롤러 각각에 대해 설정해야 한다.
public class AdminConfig implements WebMvcConfigurer {

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("AdminConfig 생성됨!");
  }

  // WebMvcConfigurer 규칙에 맞춰 인터셉터를 등록한다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    log.trace("AdminConfig.addInterceptors() 호출됨!");
    registry.addInterceptor(new AuthInterceptor());
    registry.addInterceptor(new AdminCheckInterceptor());
  }
}








