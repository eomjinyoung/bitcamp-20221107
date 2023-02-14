package bitcamp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

  String jdbcUrl;
  String username;
  String password;

  List<Connection> conList = new ArrayList<>();

  public ConnectionPool(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws Exception {
    if (conList.size() == 0) {
      System.out.println("Connection 생성!");
      return DriverManager.getConnection(jdbcUrl, username, password);
    }
    return conList.remove(0);
  }

  public void returnConnection(Connection con) {
    conList.add(con);
  }

}




