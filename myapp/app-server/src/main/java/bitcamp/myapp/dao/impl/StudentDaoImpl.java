package bitcamp.myapp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.vo.Student;

public class StudentDaoImpl implements StudentDao {

  Connection con;

  public StudentDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Student s) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format(
          "insert into app_student(member_id, pst_no, bas_addr, det_addr, work, gender, level)"
              + " values('%s','%s','%s','%s',%b,'%s',%d)",
              s.getNo(), // app_member 테이블에 입력한 후 자동 생성된 PK 값
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
  public List<Student> findAll() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
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
                + "   name asc")) {

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
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select student_id, name, tel, created_date, pst_no, bas_addr, det_addr, work, gender, level"
                + " from app_student"
                + " where student_id=" + no)) {

      if (rs.next()) {
        Student s = new Student();
        s.setNo(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        //s.setCreatedDate(rs.getString("created_date"));
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
  public Student[] findByKeyword(String keyword) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select student_id, name, tel, work, level"
                + " from app_student"
                + " where name like('%" + keyword + "%')"
                + " or tel like('%" + keyword + "%')"
                + " or bas_addr like('%" + keyword + "%')"
                + " or det_addr like('%" + keyword + "%')"
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
  public void update(Student s) {
    try (Statement stmt = con.createStatement()) {

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
    try (Statement stmt = con.createStatement()) {

      String sql = String.format("delete from app_student where student_id=%d", s.getNo());

      return stmt.executeUpdate(sql) == 1;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }


  public static void main(String[] args) throws Exception {
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");

    StudentDaoImpl dao = new StudentDaoImpl(con);

    //    Student s = new Student();
    //    s.setNo(10);
    //    s.setPostNo("11100");
    //    s.setBasicAddress("강남대로10");
    //    s.setDetailAddress("110호");
    //    s.setWorking(false);
    //    s.setGender('W');
    //    s.setLevel((byte)1);
    //
    //    dao.insert(s);

    List<Student> list = dao.findAll();
    for (Student s : list) {
      System.out.println(s);
    }

    //    Member m = dao.findByNo(2);
    //    System.out.println(m);


    //    Member m = new Member();
    //    m.setNo(2);
    //    m.setName("xxxx");
    //    m.setEmail("xxx@test.com");
    //    m.setPassword("2222");
    //    m.setTel("101010");
    //    System.out.println(dao.update(m));

    //    System.out.println(dao.delete(3));

    con.close();
  }
}























