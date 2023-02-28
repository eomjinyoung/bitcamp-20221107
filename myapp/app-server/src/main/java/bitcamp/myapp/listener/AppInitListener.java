package bitcamp.myapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import bitcamp.myapp.config.AppConfig;

@WebListener
public class AppInitListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // Spring IoC 컨테이너 준비
    AnnotationConfigWebApplicationContext iocContainer = new AnnotationConfigWebApplicationContext();
    iocContainer.register(AppConfig.class);

    // DispatcherServlet 프론트 컨트롤러 준비
    DispatcherServlet dispatcherServlet = new DispatcherServlet(iocContainer);
    sce.getServletContext().addServlet("app", dispatcherServlet);

  }
}









