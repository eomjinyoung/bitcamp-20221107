package bitcamp.myapp.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  // DispatcherServlet 의 서블릿이름을 설정한다.
  @Override
  protected String getServletName() {
    System.out.println("DispatcherServlet: 서블릿 이름 준비");
    return "app";
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    System.out.println("DispatcherServlet: IoC 컨테이너 설정 클래스 준비");
    return new Class<?> [] {AppConfig.class};
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    System.out.println("ServletContext: IoC 컨테이너 설정 클래스 준비");
    return null;
  }

  @Override
  protected String[] getServletMappings() {
    System.out.println("DispatcherServlet: URL 설정");
    return new String[] {"/app/*"};
  }

  @Override
  protected void customizeRegistration(Dynamic registration) {
    System.out.println("DispatcherServlet: 멀티파트 설정");
    registration.setMultipartConfig(new MultipartConfigElement(
        System.getProperty("java.io.tmpdir"), // 클라이언트가 보낸 파일을 임시 보관할 폴더
        1024 * 1024 * 20, // 한 파일의 최대 크기
        1024 * 1024 * 20 * 10, // 한 요청 당 최대 총 파일 크기
        1024 * 1024 * 1 // 클라이언트가 보낸 파일을 메모리에 임시 보관하는 최대 크기.
        // 최대 크기를 초과하면 파일에 내보낸다.
        ));
  }

  // DispatcherServlet 실행 전후에 작업을 수행할 필터 설정
  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {new CharacterEncodingFilter("UTF-8")};
  }
}









