package bitcamp.myapp.dao.impl;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.vo.Board;
import bitcamp.util.ConnectionFactory;

public class BoardDaoImpl implements BoardDao {

  ConnectionFactory conFactory;

  public BoardDaoImpl(ConnectionFactory conFactory) {
    this.conFactory = conFactory;
  }

  @Override
  public void insert(Board b) {
    try (Statement stmt = conFactory.getConnection().createStatement()) {

      String sql = String.format("insert into app_board(title, content, pwd) values('%s', '%s', '%s')",
          b.getTitle(), b.getContent(), b.getPassword());

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public List<Board> findAll() {
    try (Statement stmt = conFactory.getConnection().createStatement();
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
      return list;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Board findByNo(int no) {
    try (Statement stmt = conFactory.getConnection().createStatement();
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
  public void increaseViewCount(int no) {
    try (Statement stmt = conFactory.getConnection().createStatement()) {

      String sql = String.format(
          "update app_board set"
              + " view_cnt = view_cnt + 1"
              + " where board_id=%d",
              no);

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public List<Board> findByKeyword(String keyword) {
    try (Statement stmt = conFactory.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(
            "select board_id, title, created_date, view_cnt"
                + " from app_board"
                + " where title like('%" + keyword + "%')"
                + " or content like('%" + keyword + "%')"
                + " order by board_id desc")) {

      ArrayList<Board> list = new ArrayList<>();
      while (rs.next()) {
        Board b = new Board();
        b.setNo(rs.getInt("board_id"));
        b.setTitle(rs.getString("title"));
        b.setCreatedDate(rs.getString("created_date"));
        b.setViewCount(rs.getInt("view_cnt"));

        list.add(b);
      }
      return list;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int update(Board b) {
    try (Statement stmt = conFactory.getConnection().createStatement()) {

      String sql = String.format("update app_board set title='%s', content='%s' where board_id=%d",
          b.getTitle(), b.getContent(), b.getNo());

      return stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (Statement stmt = conFactory.getConnection().createStatement()) {

      String sql = String.format("delete from app_board where board_id=%d", no);

      return stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}























