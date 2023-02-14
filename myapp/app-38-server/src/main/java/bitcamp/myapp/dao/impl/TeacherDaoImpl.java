package bitcamp.myapp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;

public class TeacherDaoImpl implements TeacherDao {

  Connection con;

  public TeacherDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Teacher s) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format("insert into app_teacher("
          + " member_id,"
          + " degree,"
          + " school,"
          + " major,"
          + " wage)"
          + " values(%d, %d,'%s','%s',%d)",
          s.getNo(),
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
  public List<Teacher> findAll() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select"
            + "  m.member_id,"
            + "  m.name,"
            + "  m.tel,"
            + "  t.degree,"
            + "  t.major,"
            + "  t.wage"
            + " from app_teacher t"
            + "   inner join app_member m on t.member_id = m.member_id"
            + " order by"
            + "   m.name asc")) {

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
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select"
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
            + " where m.member_id=" + no)) {

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

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int update(Teacher t) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format(
          "update app_teacher set "
              + " degree=%d,"
              + " school='%s',"
              + " major='%s',"
              + " wage=%d "
              + " where member_id=%d",
              t.getDegree(),
              t.getSchool(),
              t.getMajor(),
              t.getWage(),
              t.getNo());

      return stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format("delete from app_teacher"
          + " where member_id=%d", no);

      return stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public static void main(String[] args) throws Exception {
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");

    TeacherDaoImpl dao = new TeacherDaoImpl(con);

    //    Teacher t = new Teacher();
    //    t.setNo(15);
    //    t.setDegree(3);
    //    t.setSchool("비트대학교");
    //    t.setMajor("수학");
    //    t.setWage(14000);
    //
    //    dao.insert(t);

    //    List<Teacher> list = dao.findAll();
    //    for (Teacher t : list) {
    //      System.out.println(t);
    //    }

    //    Teacher t = dao.findByNo(4);
    //    System.out.println(t);

    //    Teacher t = new Teacher();
    //    t.setNo(4);
    //    t.setDegree(5);
    //    t.setSchool("비트대학교x");
    //    t.setMajor("수학xx");
    //    t.setWage(55000);
    //    System.out.println(dao.update(t));

    //    System.out.println(dao.delete(4));

    con.close();
  }
}























