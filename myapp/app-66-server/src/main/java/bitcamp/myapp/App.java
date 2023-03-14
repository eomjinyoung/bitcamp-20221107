package bitcamp.myapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
}