package bitcamp.myapp.listener;

import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;
import bitcamp.util.TransactionManager;

// 웹 애플리케이션이 시작/종료 될 때 실행되는 객체
@WebListener
// 서블릿 컨테이너에게 이 클래스가 리스너 구현체임을 알려줘야 한다.
// 그래야만 서블릿 컨테이너는 이 클래스의 인스턴스를 생성한다.
// 그리고 웹앱이 시작되거나 종료될 때 메서드를 호출해 준다.
public class ContextLoaderListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // 웹 애플리케이션이 시작될 때 서블릿 컨테이너가 호출한다.
    System.out.println("ContextLoaderListener.contextInitialized() 호출됨!");

    try {
      InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
          "bitcamp/myapp/config/mybatis-config.xml");
      SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
      BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
          builder.build(mybatisConfigInputStream));

      TransactionManager txManager = new TransactionManager(sqlSessionFactory);

      BoardDao boardDao = new DaoGenerator(sqlSessionFactory).getObject(BoardDao.class);
      MemberDao memberDao = new DaoGenerator(sqlSessionFactory).getObject(MemberDao.class);
      StudentDao studentDao = new DaoGenerator(sqlSessionFactory).getObject(StudentDao.class);
      TeacherDao teacherDao = new DaoGenerator(sqlSessionFactory).getObject(TeacherDao.class);

      // 서블릿 컨텍스트 보관소를 알아낸다.
      ServletContext ctx = sce.getServletContext();

      // 서블릿들이 공유할 객체를 이 보관소에 저장한다.
      ctx.setAttribute("txManager", txManager);

      ctx.setAttribute("boardDao", boardDao);
      ctx.setAttribute("memberDao", memberDao);
      ctx.setAttribute("studentDao", studentDao);
      ctx.setAttribute("teacherDao", teacherDao);

    } catch (Exception e) {
      System.out.println("웹 애플리케이션 자원을 준비하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // 웹 애플리케이션이 종료될 때 서블릿 컨테이너가 호출한다.
    System.out.println("ContextLoaderListener.contextDestroyed() 호출됨!");
  }
}
