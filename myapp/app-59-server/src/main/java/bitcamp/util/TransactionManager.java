package bitcamp.util;

import org.apache.ibatis.session.SqlSession;

public class TransactionManager {

  BitcampSqlSessionFactory sqlSessionFactory;

  public TransactionManager(BitcampSqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public void startTransaction() {
    sqlSessionFactory.prepareSqlSessionForThread();
  }

  public void commit() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    sqlSession.commit();
    sqlSessionFactory.clearSqlSessionForThread();
  }

  public void rollback() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    sqlSession.rollback();
    sqlSessionFactory.clearSqlSessionForThread();
  }
}










