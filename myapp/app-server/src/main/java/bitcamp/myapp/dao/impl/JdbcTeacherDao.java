package bitcamp.myapp.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;

public class JdbcTeacherDao implements TeacherDao {

  Connection con;

  // 의존객체 Connection 을 생성자에서 받는다.
  public JdbcTeacherDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Teacher s) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format(
          "insert into app_teacher(name, tel, email, degree, school, major, wage)"
              + " values('%s','%s','%s',%d,'%s','%s',%d)",
              s.getName(),
              s.getTel(),
              s.getEmail(),
              s.getDegree(),
              s.getSchool(),
              s.getMajor(),
              s.getWage());

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Teacher[] findAll() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select teacher_id, name, tel, degree, major, wage"
                + " from app_teacher"
                + " order by teacher_id desc")) {

      ArrayList<Teacher> list = new ArrayList<>();
      while (rs.next()) {
        Teacher s = new Teacher();
        s.setNo(rs.getInt("teacher_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setDegree(rs.getInt("degree"));
        s.setMajor(rs.getString("major"));
        s.setWage(rs.getInt("wage"));

        list.add(s);
      }

      Teacher[] arr = new Teacher[list.size()];
      list.toArray(arr);

      return arr;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Teacher findByNo(int no) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select teacher_id, name, tel, created_date, email, degree, school, major, wage"
                + " from app_teacher"
                + " where teacher_id=" + no)) {

      if (rs.next()) {
        Teacher s = new Teacher();
        s.setNo(rs.getInt("teacher_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setCreatedDate(rs.getString("created_date"));
        s.setEmail(rs.getString("email"));
        s.setDegree(rs.getInt("degree"));
        s.setSchool(rs.getString("school"));
        s.setMajor(rs.getString("major"));
        s.setWage(rs.getInt("wage"));
        return s;
      }

      return null;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public void update(Teacher t) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format(
          "update app_teacher set "
              + " name='%s', tel='%s', email='%s', degree=%d,"
              + " school='%s', major='%s', wage=%d "
              + " where teacher_id=%d",
              t.getName(),
              t.getTel(),
              t.getEmail(),
              t.getDegree(),
              t.getSchool(),
              t.getMajor(),
              t.getWage(),
              t.getNo());

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public boolean delete(Teacher t) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format("delete from app_teacher where teacher_id=%d", t.getNo());

      return stmt.executeUpdate(sql) == 1;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}























