package bitcamp.myapp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.DaoException;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

public class MemberDaoImpl implements MemberDao {

  Connection con;

  public MemberDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Member s) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format(
          "insert into app_member(name, tel)"
              + " values('%s','%s')",
              s.getName(),
              s.getTel());

      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public List<Member> findAll() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select member_id, name, tel, created_date"
                + " from app_member"
                + " order by member_id desc")) {

      ArrayList<Member> list = new ArrayList<>();
      while (rs.next()) {
        Member m = new Member();
        m.setNo(rs.getInt("member_id"));
        m.setName(rs.getString("name"));
        m.setTel(rs.getString("tel"));
        m.setCreatedDate(rs.getDate("created_date"));

        list.add(m);
      }
      return list;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Member findByNo(int no) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select member_id, name, tel, created_date"
                + " from app_member"
                + " where member_id=" + no)) {

      if (rs.next()) {
        Member m = new Member();
        m.setNo(rs.getInt("member_id"));
        m.setName(rs.getString("name"));
        m.setTel(rs.getString("tel"));
        m.setCreatedDate(rs.getDate("created_date"));
        return m;
      }
      return null;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int update(Member m) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format(
          "update app_member set "
              + " name='%s', tel='%s'"
              + " where member_id=%d",
              m.getName(),
              m.getTel(),
              m.getNo());

      return stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format(
          "delete from app_member"
              + " where member_id=%d", no);

      return stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public static void main(String[] args) throws Exception {
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");

    MemberDaoImpl dao = new MemberDaoImpl(con);

    Member m = new Member();
    m.setName("aaa4");
    m.setTel("11114");

    dao.insert(m);

    con.close();
  }
}























