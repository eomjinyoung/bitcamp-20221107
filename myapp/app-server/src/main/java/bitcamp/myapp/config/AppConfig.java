package bitcamp.myapp.config;

import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;
import bitcamp.util.TransactionManager;

//@Configuration

// Spring IoC 컨테이너가 자동 생성할 클래스를 찾을 수 있도록 패키지를 지정한다.
@ComponentScan("bitcamp.myapp")
public class AppConfig {

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    System.out.println("SqlSessionFactory 객체 생성!");
    InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
        "bitcamp/myapp/config/mybatis-config.xml");
    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
    return new BitcampSqlSessionFactory(
        builder.build(mybatisConfigInputStream));
  }

  @Bean
  public TransactionManager transactionManager(SqlSessionFactory sqlSessionFactory) throws Exception {
    System.out.println("TransactionManager 객체 생성! ");
    return new TransactionManager((BitcampSqlSessionFactory) sqlSessionFactory);
  }

  @Bean
  public BoardDao boardDao(SqlSessionFactory sqlSessionFactory) {
    return new DaoGenerator(sqlSessionFactory).getObject(BoardDao.class);
  }

  @Bean
  public MemberDao memberDao(SqlSessionFactory sqlSessionFactory) {
    return new DaoGenerator(sqlSessionFactory).getObject(MemberDao.class);
  }

  @Bean
  public StudentDao studentDao(SqlSessionFactory sqlSessionFactory) {
    return new DaoGenerator(sqlSessionFactory).getObject(StudentDao.class);
  }

  @Bean
  public TeacherDao teacherDao(SqlSessionFactory sqlSessionFactory) {
    return new DaoGenerator(sqlSessionFactory).getObject(TeacherDao.class);
  }

  @Bean
  public BoardFileDao boardFileDao(SqlSessionFactory sqlSessionFactory) {
    return new DaoGenerator(sqlSessionFactory).getObject(BoardFileDao.class);
  }

  // Servlet3.0의 멀티파트 요청 데이터를 처리하려면
  // 1) 서블릿에 멀티파트 파일 처리에 관한 설정 정보를 담은 MultipartConfigElement를 등록해야 한다.
  // 2) 스프링 WebMVC에는 Servlet3.0 API를 이용해 멀티파트 데이터를 처리한 후에
  //    페이지 컨트롤러에게 그 값들을 전달해주는 StandardServletMultipartResolver를 등록해야 한다.
  // 주의!
  //    이때 MultipartResolver 구현체 bean 이름은 "multipartResolver" 여야 한다.
  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  // Apache commons-fileupload 라이브러리로 멀티파트 요청 데이터를 처리하려면
  // 1) 프로젝트에 commons-fileupload 라이브러리를 별도로 추가해야 한다.
  // 2) 스프링 WebMVC에는 이 라이브러리를 이용해 멀티파트 데이터를 처리한 후에
  //    페이지 컨트롤러에게 그 값들을 전달해주는 CommonsMultipartResolver를 등록해야 한다.
  // 주의!
  //    이때 MultipartResolver 구현체 bean 이름은 "multipartResolver" 여야 한다.
  //  @Bean
  //  public MultipartResolver multipartResolver() {
  //    return new CommonsMultipartResolver();
  //  }
}








