package bitcamp.myapp.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import bitcamp.myapp.controller.StudentController;
import bitcamp.myapp.controller.TeacherController;
import bitcamp.myapp.web.interceptor.AuthInterceptor;

//@Configuration

@ComponentScan(
    value = "bitcamp.myapp.controller",
    excludeFilters = {
        @Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = {StudentController.class, TeacherController.class})
    })

@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("AppConfig 생성됨!");
  }

  @Bean
  public MultipartResolver multipartResolver() {
    log.trace("MultipartResolver 생성됨!");
    return new StandardServletMultipartResolver();
  }

  // WebMvcConfigurer 규칙에 맞춰 인터셉터를 등록한다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    log.trace("AppConfig.addInterceptors() 호출됨!");
    registry.addInterceptor(new AuthInterceptor()).excludePathPatterns("/auth/**");
  }
}








