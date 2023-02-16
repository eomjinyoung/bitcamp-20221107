package bitcamp.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.BoardDao;

public class DaoGenerator implements InvocationHandler {

  SqlSessionFactory sqlSessionFactory;

  public DaoGenerator(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @SuppressWarnings("unchecked")
  public <T> T getObject(Class<T> classInfo) {
    String className = classInfo.getName();

    return (T) Proxy.newProxyInstance(
        getClass().getClassLoader(), // 현재 클래스의 로딩을 담당한 관리자: 즉 클래스 로딩 관리자
        new Class[] {classInfo}, // 클래스가 구현해야 할 인터페이스 정보 목록
        this // InvocationHandler 객체
        );
  }

  // 자동 생성된 프록시 객체에 대해 메서드를 호출하면
  // 실제 InvocationHandler의 invoke()가 호출된다.
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    SqlSession sqlSession = sqlSessionFactory.openSession();

    String daoName = proxy.getClass().getInterfaces()[0].getSimpleName();
    String methodName = method.getName();
    String sqlStatementName = String.format("%s.%s", daoName, methodName);
    System.out.printf("%s.%s() 호출!\n", daoName, methodName);
    Class<?> returnType = method.getReturnType();

    if (returnType == int.class || returnType == void.class) {
      return args == null ? sqlSession.insert(sqlStatementName) :
        sqlSession.insert(sqlStatementName, args[0]);
    } else if (returnType == List.class) {
      return args == null ? sqlSession.selectList(sqlStatementName) :
        sqlSession.selectList(sqlStatementName, args[0]);
    } else {
      return args == null ? sqlSession.selectOne(sqlStatementName) :
        sqlSession.selectOne(sqlStatementName, args[0]);
    }
  }


  public static void main(String[] args) throws Exception {

    BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
        new SqlSessionFactoryBuilder().build(
            Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml")));

    DaoGenerator generator = new DaoGenerator(sqlSessionFactory);
    BoardDao dao = generator.getObject(BoardDao.class);

    //    Board b = new Board();
    //    b.setTitle("테스트1");
    //    b.setContent("테스트내용1");
    //    b.setPassword("1111");
    //    dao.insert(b);

    //    Board b = new Board();
    //    b.setNo(13);
    //    b.setTitle("테스트1xxx");
    //    b.setContent("테스트내용1xxxx");
    //    b.setPassword("1111");
    //    dao.update(b);

    //    dao.delete(13);

    //    List<Board> list = dao.findAll();
    //    for (Board b : list) {
    //      System.out.println(b);
    //    }

    //    System.out.println(dao.findByNo(13));
  }

}
