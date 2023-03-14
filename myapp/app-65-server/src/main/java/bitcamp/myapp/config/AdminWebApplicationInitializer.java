package bitcamp.myapp.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AdminWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  // DispatcherServlet 의 서블릿이름을 설정한다.
  @Override
  protected String getServletName() {
    return "admin";
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {AdminConfig.class};
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    // ContextLoaderListener의 IoC 컨테이너 설정
    // ContextLoaderListener는 모든 서블릿이 공유하기 때문에
    // 여기에서 한 번 설정하면 다른 쪽에서는 설정할 필요가 없다.
    return new Class<?>[] {RootConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/admin/*"};
  }

  @Override
  protected void customizeRegistration(Dynamic registration) {
    registration.setMultipartConfig(new MultipartConfigElement(
        System.getProperty("java.io.tmpdir"), // 클라이언트가 보낸 파일을 임시 보관할 폴더
        1024 * 1024 * 20, // 한 파일의 최대 크기
        1024 * 1024 * 20 * 10, // 한 요청 당 최대 총 파일 크기
        1024 * 1024 * 1 // 클라이언트가 보낸 파일을 메모리에 임시 보관하는 최대 크기.
        // 최대 크기를 초과하면 파일에 내보낸다.
        ));
  }

  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {new CharacterEncodingFilter("UTF-8")};
  }
}









