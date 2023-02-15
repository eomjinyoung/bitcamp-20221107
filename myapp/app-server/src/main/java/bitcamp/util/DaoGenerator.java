package bitcamp.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.BoardDao;

public class DaoGenerator {

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
        new MyInvocationHandler() // InvocationHandler 객체
        );
  }

  // 자동 생성된 프록시 객체에 대해 메서드를 호출하면
  // 실제 InvocationHandler의 invoke()가 호출된다.
  //
  class MyInvocationHandler implements InvocationHandler {

    // 프록시 객체에 대해 메서드를 호출하면 이 메서드가 실행된다.
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      System.out.printf("%s() 메서드 호출했음!\n", method.getName());
      if (method.getReturnType() == int.class) {
        return 1;
      }
      return null;
    }
  }


  public static void main(String[] args) {
    DaoGenerator generator = new DaoGenerator(null);
    BoardDao dao = generator.getObject(BoardDao.class);

    dao.insert(null);
    dao.findAll();
    dao.findByNo(1);
    dao.update(null);
    dao.delete(1);
  }

}
