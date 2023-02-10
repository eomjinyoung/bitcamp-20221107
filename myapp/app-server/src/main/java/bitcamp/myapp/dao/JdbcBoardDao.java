package bitcamp.myapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import bitcamp.myapp.vo.Board;

public class JdbcBoardDao implements BoardDao {

  Connection con;

  // 의존객체 Connection 을 생성자에서 받는다.
  public JdbcBoardDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Board b) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format("insert into app_board(title, content, pwd) values('%s', '%s', '%s')",
          b.getTitle(), b.getContent(), b.getPassword());

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Board[] findAll() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select board_id, title, created_date, view_cnt from app_board order by board_id desc")) {

      ArrayList<Board> list = new ArrayList<>();
      while (rs.next()) {
        Board b = new Board();
        b.setNo(rs.getInt("board_id"));
        b.setTitle(rs.getString("title"));
        b.setCreatedDate(rs.getString("created_date"));
        b.setViewCount(rs.getInt("view_cnt"));

        list.add(b);
      }

      Board[] boards = new Board[list.size()];
      list.toArray(boards);

      return boards;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Board findByNo(int no) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select board_id, title, content, pwd, created_date, view_cnt from app_board where board_id=" + no)) {

      if (rs.next()) {
        Board b = new Board();
        b.setNo(rs.getInt("board_id"));
        b.setTitle(rs.getString("title"));
        b.setContent(rs.getString("content"));
        b.setPassword(rs.getString("pwd"));
        b.setCreatedDate(rs.getString("created_date"));
        b.setViewCount(rs.getInt("view_cnt"));
        return b;
      }

      return null;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public void update(Board b) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format("update app_board set title='%s', content='%s' where board_id=%d",
          b.getTitle(), b.getContent(), b.getNo());

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public boolean delete(Board b) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format("delete from app_board where board_id=%d", b.getNo());

      return stmt.executeUpdate(sql) == 1;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}























