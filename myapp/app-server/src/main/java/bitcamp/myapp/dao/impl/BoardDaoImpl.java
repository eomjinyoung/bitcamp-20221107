package bitcamp.myapp.dao.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

public class BoardDaoImpl implements BoardDao {

  SqlSessionFactory sqlSessionFactory;

  public BoardDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Board b) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      sqlSession.insert("BoardMapper.insert", b);
      sqlSession.commit();
    }
  }

  @Override
  public List<Board> findAll() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("BoardMapper.findAll");
    }
  }

  @Override
  public Board findByNo(int no) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("BoardMapper.findByNo", no);
    }
  }

  @Override
  public void increaseViewCount(int no) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      sqlSession.update("BoardMapper.increaseViewCount", no);
      sqlSession.commit();
    }
  }

  @Override
  public List<Board> findByKeyword(String keyword) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("BoardMapper.findByKeyword", keyword);
    }
  }

  @Override
  public int update(Board b) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int n = sqlSession.update("BoardMapper.update", b);
      sqlSession.commit();
      return n;
    }
  }

  @Override
  public int delete(int no) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int n = sqlSession.delete("BoardMapper.delete", no);
      sqlSession.commit();
      return n;
    }
  }
}























