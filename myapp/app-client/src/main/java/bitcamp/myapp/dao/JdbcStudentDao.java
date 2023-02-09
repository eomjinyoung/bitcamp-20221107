package bitcamp.myapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import bitcamp.myapp.vo.Student;

public class JdbcStudentDao implements StudentDao {

  @Override
  public void insert(Student s) {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "insert into app_student(name, tel, pst_no, bas_addr, det_addr, work, gender, level)"
              + " values('%s','%s','%s','%s','%s',%b,'%s',%d)",
              s.getName(),
              s.getTel(),
              s.getPostNo(),
              s.getBasicAddress(),
              s.getDetailAddress(),
              s.isWorking(),
              s.getGender(),
              s.getLevel());

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Student[] findAll() {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select student_id, name, tel, work, level"
                + " from app_student"
                + " order by student_id desc")) {

      ArrayList<Student> list = new ArrayList<>();
      while (rs.next()) {
        Student s = new Student();
        s.setNo(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setWorking(rs.getBoolean("work"));
        s.setLevel(rs.getByte("level"));

        list.add(s);
      }

      Student[] arr = new Student[list.size()];
      list.toArray(arr);

      return arr;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Student findByNo(int no) {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select student_id, name, tel, created_date, pst_no, bas_addr, det_addr, work, gender, level"
                + " from app_student"
                + " where student_id=" + no)) {

      if (rs.next()) {
        Student s = new Student();
        s.setNo(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setCreatedDate(rs.getString("created_date"));
        s.setPostNo(rs.getString("pst_no"));
        s.setBasicAddress(rs.getString("bas_addr"));
        s.setDetailAddress(rs.getString("det_addr"));
        s.setWorking(rs.getBoolean("work"));
        s.setGender(rs.getString("gender").charAt(0));
        s.setLevel(rs.getByte("level"));
        return s;
      }

      return null;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public void update(Student s) {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "update app_student set "
              + " name='%s', tel='%s', pst_no='%s', bas_addr='%s', det_addr='%s',"
              + " work=%b, gender='%s', level=%d "
              + " where student_id=%d",
              s.getName(),
              s.getTel(),
              s.getPostNo(),
              s.getBasicAddress(),
              s.getDetailAddress(),
              s.isWorking(),
              s.getGender(),
              s.getLevel(),
              s.getNo());

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public boolean delete(Student s) {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format("delete from app_student where student_id=%d", s.getNo());

      return stmt.executeUpdate(sql) == 1;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}























