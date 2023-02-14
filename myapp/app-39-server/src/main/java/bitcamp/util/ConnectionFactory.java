package bitcamp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
  String jdbcUrl;
  String username;
  String password;

  // 스레드 전용 변수
  // => 스레드 마다 별도로 존재하는 변수다.
  // => Connection 객체를 담는 변수다.
  ThreadLocal<Connection> conLocal = new ThreadLocal<>();

  public ConnectionFactory(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws Exception {
    // 현재 이 명령을 실행하는 스레드에 마련된 conLocal 변수에서 값을 꺼낸다.
    Connection con = conLocal.get();

    if (con == null) {
      // 현재 스레드 변수에 값이 Connection 객체가 들어 있지 않다면 새로 만든다.
      con = DriverManager.getConnection(jdbcUrl, username, password);

      // 현재 스레드가 다음에도 사용할 수 있도록 Connection 객체를 스레드 변수에 보관한다.
      conLocal.set(con);

      System.out.printf("[%s] Connection 객체 생성!\n", Thread.currentThread().getName());
    }

    System.out.printf("[%s] Connection 객체 리턴!\n", Thread.currentThread().getName());
    // 현재 스레드를 위해 준비한 Connection 객체를 리턴한다.
    return con;
  }
}







