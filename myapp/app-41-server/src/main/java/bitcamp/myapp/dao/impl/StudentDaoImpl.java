package bitcamp.myapp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.vo.Student;
import bitcamp.util.ConnectionFactory;

public class StudentDaoImpl implements StudentDao {

  ConnectionFactory conFactory;

  public StudentDaoImpl(ConnectionFactory conFactory) {
    this.conFactory = conFactory;
  }

  @Override
  public void insert(Student s) {

    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "insert into app_student("
            + "  member_id,"
            + "  pst_no,"
            + "  bas_addr,"
            + "  det_addr,"
            + "  work,"
            + "  gender,"
            + "  level)"
            + " values(?,?,?,?,?,?,?)")) {

      stmt.setInt(1, s.getNo());
      stmt.setString(2, s.getPostNo());
      stmt.setString(3, s.getBasicAddress());
      stmt.setString(4, s.getDetailAddress());
      stmt.setBoolean(5, s.isWorking());
      stmt.setString(6, String.valueOf(s.getGender()));
      stmt.setInt(7, s.getLevel());

      stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public List<Student> findAll() {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "select"
            + "  m.member_id,"
            + "  m.name,"
            + "  m.email,"
            + "  m.tel,"
            + "  s.work,"
            + "  s.level"
            + " from app_student s"
            + "   inner join app_member m on s.member_id = m.member_id"
            + " order by"
            + "   m.name asc");
        ResultSet rs = stmt.executeQuery()) {

      ArrayList<Student> list = new ArrayList<>();
      while (rs.next()) {
        Student s = new Student();
        s.setNo(rs.getInt("member_id"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setTel(rs.getString("tel"));
        s.setWorking(rs.getBoolean("work"));
        s.setLevel(rs.getByte("level"));

        list.add(s);
      }
      return list;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Student findByNo(int no) {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "select"
            + "  m.member_id,"
            + "  m.name,"
            + "  m.email,"
            + "  m.tel,"
            + "  m.created_date,"
            + "  s.pst_no,"
            + "  s.bas_addr,"
            + "  s.det_addr,"
            + "  s.work,"
            + "  s.gender,"
            + "  s.level"
            + " from app_student s"
            + "   inner join app_member m on s.member_id = m.member_id"
            + " where s.member_id=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Student s = new Student();
          s.setNo(rs.getInt("member_id"));
          s.setName(rs.getString("name"));
          s.setEmail(rs.getString("email"));
          s.setTel(rs.getString("tel"));
          s.setCreatedDate(rs.getDate("created_date"));
          s.setPostNo(rs.getString("pst_no"));
          s.setBasicAddress(rs.getString("bas_addr"));
          s.setDetailAddress(rs.getString("det_addr"));
          s.setWorking(rs.getBoolean("work"));
          s.setGender(rs.getString("gender").charAt(0));
          s.setLevel(rs.getByte("level"));
          return s;
        }
        return null;
      }

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public List<Student> findByKeyword(String keyword) {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "select"
            + "  m.member_id,"
            + "  m.name,"
            + "  m.email,"
            + "  m.tel,"
            + "  s.work,"
            + "  s.level"
            + " from app_student s"
            + "   inner join app_member m on s.member_id = m.member_id"
            + " where"
            + "   m.name like(?)"
            + "   or m.email like(?)"
            + "   or m.tel like(?)"
            + "   or s.bas_addr like(?)"
            + "   or s.det_addr like(?)"
            + " order by"
            + "   m.member_id desc")) {

      stmt.setString(1, "%" + keyword + "%");
      stmt.setString(2, "%" + keyword + "%");
      stmt.setString(3, "%" + keyword + "%");
      stmt.setString(4, "%" + keyword + "%");
      stmt.setString(5, "%" + keyword + "%");

      try (ResultSet rs = stmt.executeQuery()) {
        ArrayList<Student> list = new ArrayList<>();
        while (rs.next()) {
          Student s = new Student();
          s.setNo(rs.getInt("member_id"));
          s.setName(rs.getString("name"));
          s.setEmail(rs.getString("email"));
          s.setTel(rs.getString("tel"));
          s.setWorking(rs.getBoolean("work"));
          s.setLevel(rs.getByte("level"));

          list.add(s);
        }
        return list;
      }

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int update(Student s) {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "update app_student set "
            + "  pst_no=?,"
            + "  bas_addr=?,"
            + "  det_addr=?,"
            + "  work=?,"
            + "  gender=?,"
            + "  level=? "
            + " where member_id=?")) {


      stmt.setString(1, s.getPostNo());
      stmt.setString(2, s.getBasicAddress());
      stmt.setString(3, s.getDetailAddress());
      stmt.setBoolean(4, s.isWorking());
      stmt.setString(5, String.valueOf(s.getGender()));
      stmt.setInt(6, s.getLevel());
      stmt.setInt(7, s.getNo());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (PreparedStatement stmt = conFactory.getConnection().prepareStatement(
        "delete from app_student"
            + " where member_id=?")) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}























