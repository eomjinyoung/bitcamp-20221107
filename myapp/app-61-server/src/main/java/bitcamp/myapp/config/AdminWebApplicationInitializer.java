package bitcamp.myapp.config;

import javax.servlet.Filter;
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
  protected Filter[] getServletFilters() {
    return new Filter[] {new CharacterEncodingFilter("UTF-8")};
  }
}









