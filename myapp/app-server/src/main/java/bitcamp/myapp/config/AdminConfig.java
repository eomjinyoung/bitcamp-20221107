package bitcamp.myapp.config;

import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
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

  @Bean
  public ViewResolver viewResolver() {
    log.trace("InternalResourceViewResolver 생성됨!");
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class);
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    viewResolver.setOrder(3);
    return viewResolver;
  }

  @Bean
  public ViewResolver tilesViewResolver() {
    log.trace("UrlBasedViewResolver 생성됨!");
    UrlBasedViewResolver vr = new UrlBasedViewResolver();
    vr.setViewClass(TilesView.class);
    vr.setPrefix("admin/");
    vr.setOrder(2);
    return vr;
  }

  @Bean
  public ThymeleafViewResolver viewResolver(ISpringTemplateEngine templateEngine){
    log.trace("ThymeleafViewResolver 생성됨!");
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine);
    viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    viewResolver.setOrder(1);
    viewResolver.setViewNames(new String[] {"*"});
    return viewResolver;
  }

  // WebMvcConfigurer 규칙에 맞춰 인터셉터를 등록한다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    log.trace("AdminConfig.addInterceptors() 호출됨!");
    registry.addInterceptor(new AuthInterceptor());
    registry.addInterceptor(new AdminCheckInterceptor());
  }
}








