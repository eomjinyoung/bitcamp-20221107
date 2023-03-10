package bitcamp.myapp.config;

import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
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

// WebMVC 관련 설정을 처리하고 싶다면 다음 애노테이션을 추가하라!
// => WebMVC 관련 설정을 수행하는 클래스를 정의했으니,
//    WebMvcConfigurer 구현체를 찾아
//    해당 인터페이스에 정의된대로 메서드를 호출하여
//    설정을 수행하라는 의미다!
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

  @Bean
  public ViewResolver viewResolver() {
    log.trace("InternalResourceViewResolver 생성됨!");
    // 페이지 컨트롤러가 jsp 경로를 리턴하면
    // viewResolver가 그 경로를 가지고 최종 jsp 경로를 계산한 다음에
    // JstlView를 통해 실행한다.
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

    // Tiles 설정에 따라 템플릿을 실행할 뷰 처리기를 등록한다.
    vr.setViewClass(TilesView.class);

    // request handler가 리턴한 view name 앞에 일반 페이지임을 표시하기 위해 접두사를 붙인다.
    vr.setPrefix("app/");

    // 뷰리졸버의 우선 순위를 InternalResourceViewResolver 보다 우선하게 한다.
    vr.setOrder(2);
    return vr;
  }

  // 실행할 Thymeleaf 템플릿을 결정하는 일을 한다.
  @Bean
  public ThymeleafViewResolver viewResolver(ISpringTemplateEngine templateEngine){
    log.trace("ThymeleafViewResolver 생성됨!");
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine);

    // Content-Type을 설정한다.
    // => 만약 설정하지 않는다면 자바의 Uncode2(UTF-16)을 ISO-8859-1로 변환시킨다.
    //    즉 영어는 제대로 변환되지만 한글을 '?'로 변환된다.
    viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    viewResolver.setOrder(1);

    // 페이지 컨트롤러의 request handler가 무엇을 리턴하든지 간에
    // Thymeleaf 템플릿 엔진을 사용하겠다는 의미다!
    viewResolver.setViewNames(new String[] {"*"});
    //viewResolver.setViewNames(new String[] {".html", ".xhtml"});
    return viewResolver;
  }


  // WebMvcConfigurer 규칙에 맞춰 인터셉터를 등록한다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    log.trace("AppConfig.addInterceptors() 호출됨!");
    registry.addInterceptor(new AuthInterceptor()).excludePathPatterns("/auth/**");
  }
}








