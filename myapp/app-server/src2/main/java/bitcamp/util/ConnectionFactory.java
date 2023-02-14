package bitcamp.util;

import java.sql.Connection;

public class ConnectionFactory {

  ConnectionPool connectionPool;
  ThreadLocal<Connection> conLocal = new ThreadLocal<>();

  public ConnectionFactory(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  public Connection getConnection() throws Exception {
    // 현재 이 명령을 실행하는 스레드에 마련된 conLocal 변수에서 값을 꺼낸다.
    Connection con = conLocal.get();

    if (con == null) {
      // 현재 스레드 변수에 Connection 객체가 들어 있지 않다면 커넥션풀에서 가져온다.
      con = connectionPool.getConnection(); // 새로 커넥션을 생성하거나 기존 커넥션을 리턴한다.

      // 현재 스레드가 다음에도 사용할 수 있도록 Connection 객체를 스레드 변수에 보관한다.
      conLocal.set(con);
    }

    System.out.printf("[%s] Connection 객체 리턴!\n", Thread.currentThread().getName());
    // 현재 스레드를 위해 준비한 Connection 객체를 리턴한다.
    return con;
  }

  public void closeConnection() {
    // 현재 스레드가 사용한 커넥션 객체를 꺼내 ConnectionPool에 반납한다.
    Connection con = conLocal.get();
    if (con != null) {
      connectionPool.returnConnection(con);
      System.out.printf("[%s] 커넥션 객체 반납했습니다.\n", Thread.currentThread().getName());
    }
  }
}







