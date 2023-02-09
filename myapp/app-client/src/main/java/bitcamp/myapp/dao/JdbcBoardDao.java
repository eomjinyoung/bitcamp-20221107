package bitcamp.myapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import com.google.gson.Gson;
import bitcamp.myapp.vo.Board;

public class JdbcBoardDao implements BoardDao {

  @Override
  public void insert(Board b) {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format("", null);
      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Board[] findAll() {
    return new Gson().fromJson(daoStub.fetch("board", "findAll"), Board[].class);
  }

  @Override
  public Board findByNo(int no) {
    return new Gson().fromJson(daoStub.fetch("board", "findByNo", no), Board.class);
  }

  @Override
  public void update(Board b) {
    daoStub.fetch("board", "update", b);
  }

  @Override
  public boolean delete(Board b) {
    daoStub.fetch("board", "delete", b);
    return true;
  }
}























