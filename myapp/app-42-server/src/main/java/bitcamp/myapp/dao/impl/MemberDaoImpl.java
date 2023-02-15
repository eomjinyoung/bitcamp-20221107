package bitcamp.myapp.dao.impl;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

public class MemberDaoImpl implements MemberDao {

  SqlSessionFactory sqlSessionFactory;

  public MemberDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Member m) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      sqlSession.insert("MemberMapper.insert", m);
    }
  }

  @Override
  public List<Member> findAll() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("MemberMapper.findAll");
    }
  }

  @Override
  public Member findByNo(int no) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("MemberMapper.findByNo", no);
    }
  }

  @Override
  public int update(Member m) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("MemberMapper.update", m);
    }
  }

  @Override
  public int delete(int no) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("MemberMapper.delete", no);
    }
  }

  public static void main(String[] args) throws Exception {
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(
        Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml"));

    MemberDaoImpl dao = new MemberDaoImpl(sqlSessionFactory);

    //    Member m = new Member();
    //    m.setName("x1");
    //    m.setEmail("x1@test.com");
    //    m.setPassword("1111");
    //    m.setTel("010-1111-1111");
    //    dao.insert(m);
    //
    //    List<Member> list = dao.findAll();
    //    for (Member m : list) {
    //      System.out.println(m);
    //    }

    //    Member m = dao.findByNo(26);
    //    System.out.println(m);

    //    Member m = new Member();
    //    m.setNo(26);
    //    m.setName("x1aa");
    //    m.setEmail("x1aa@test.com");
    //    m.setPassword("1111");
    //    m.setTel("010-1111-1111a");
    //    System.out.println(dao.update(m));

    //    System.out.println(dao.delete(26));

  }
}























