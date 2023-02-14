package bitcamp.myapp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.ConnectionFactory;

public class TeacherDaoImpl implements TeacherDao {

  ConnectionFactory conFactory;

  public TeacherDaoImpl(ConnectionFactory conFactory) {
    this.conFactory = conFactory;
  }

  @Override
  public void insert(Teacher s) {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "insert into app_teacher("
            + " member_id,"
            + " degree,"
            + " school,"
            + " major,"
            + " wage)"
            + " values(?, ?, ?, ?, ?)")) {

      stmt.setInt(1, s.getNo());
      stmt.setInt(2,  s.getDegree());
      stmt.setString(3,  s.getSchool());
      stmt.setString(4,  s.getMajor());
      stmt.setInt(5,  s.getWage());

      stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public List<Teacher> findAll() {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "select"
            + "  m.member_id,"
            + "  m.name,"
            + "  m.tel,"
            + "  t.degree,"
            + "  t.major,"
            + "  t.wage"
            + " from app_teacher t"
            + "   inner join app_member m on t.member_id = m.member_id"
            + " order by"
            + "   m.name asc");
        ResultSet rs = stmt.executeQuery()) {

      ArrayList<Teacher> list = new ArrayList<>();
      while (rs.next()) {
        Teacher s = new Teacher();
        s.setNo(rs.getInt("member_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setDegree(rs.getInt("degree"));
        s.setMajor(rs.getString("major"));
        s.setWage(rs.getInt("wage"));

        list.add(s);
      }
      return list;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Teacher findByNo(int no) {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "select"
            + "  m.member_id,"
            + "  m.name,"
            + "  m.email,"
            + "  m.tel,"
            + "  m.created_date,"
            + "  t.degree,"
            + "  t.school,"
            + "  t.major,"
            + "  t.wage"
            + " from app_teacher t"
            + "   inner join app_member m on t.member_id = m.member_id"
            + " where m.member_id=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Teacher s = new Teacher();
          s.setNo(rs.getInt("member_id"));
          s.setName(rs.getString("name"));
          s.setEmail(rs.getString("email"));
          s.setTel(rs.getString("tel"));
          s.setCreatedDate(rs.getDate("created_date"));
          s.setDegree(rs.getInt("degree"));
          s.setSchool(rs.getString("school"));
          s.setMajor(rs.getString("major"));
          s.setWage(rs.getInt("wage"));
          return s;
        }
        return null;
      }

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int update(Teacher t) {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "update app_teacher set "
            + " degree=?,"
            + " school=?,"
            + " major=?,"
            + " wage=?"
            + " where member_id=?")) {

      stmt.setInt(1, t.getDegree());
      stmt.setString(2, t.getSchool());
      stmt.setString(3, t.getMajor());
      stmt.setInt(4, t.getWage());
      stmt.setInt(5, t.getNo());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "delete from app_teacher"
            + " where member_id=?")) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

}























