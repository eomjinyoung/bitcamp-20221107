package bitcamp.myapp.config;

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

  {
    System.out.println("AppConfig 생성됨!");
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  @Bean
  public ViewResolver viewResolver() {
    // 페이지 컨트롤러가 jsp 경로를 리턴하면
    // viewResolver가 그 경로를 가지고 최종 jsp 경로를 계산한 다음에
    // JstlView를 통해 실행한다.
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class);
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    viewResolver.setOrder(2);
    return viewResolver;
  }

  @Bean
  public ViewResolver tilesViewResolver() {
    UrlBasedViewResolver vr = new UrlBasedViewResolver();

    // Tiles 설정에 따라 템플릿을 실행할 뷰 처리기를 등록한다.
    vr.setViewClass(TilesView.class);

    // request handler가 리턴한 view name 앞에 일반 페이지임을 표시하기 위해 접두사를 붙인다.
    vr.setPrefix("app/");

    // 뷰리졸버의 우선 순위를 InternalResourceViewResolver 보다 우선하게 한다.
    vr.setOrder(1);
    return vr;
  }

  // WebMvcConfigurer 규칙에 맞춰 인터셉터를 등록한다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    System.out.println("AppConfig.addInterceptors() 호출됨!");
    registry.addInterceptor(
        new AuthInterceptor()).addPathPatterns("/**/*insert", "/**/*update", "/**/*delete");
  }
}








