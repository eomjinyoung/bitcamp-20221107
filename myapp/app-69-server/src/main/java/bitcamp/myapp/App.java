package bitcamp.myapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import bitcamp.myapp.web.interceptor.AdminCheckInterceptor;
import bitcamp.myapp.web.interceptor.AuthInterceptor;

@EnableTransactionManagement
@SpringBootApplication
public class App implements WebMvcConfigurer {

  Logger log = LogManager.getLogger(getClass());

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    log.info("App.addInterceptors() 호출됨!");
    registry.addInterceptor(new AuthInterceptor()).excludePathPatterns("/auth/**");
    registry.addInterceptor(new AdminCheckInterceptor()).addPathPatterns("/students/**");
    registry.addInterceptor(new AdminCheckInterceptor()).addPathPatterns("/teachers/**");
  }

  // Cross-Origin 관련해서 기본 값 외에 추가로 설정할 게 있다면 이 메서드를 정의한다.
  // 스프링부트가 시작되면 이 메서드를 호출하여 CrossOrigin을 설정할 것이다.
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
    .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500")
    .allowedMethods("*");
  }
}