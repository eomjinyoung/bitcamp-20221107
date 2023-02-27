package bitcamp.util;

import java.sql.Connection;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

public class BitcampSqlSessionFactory implements SqlSessionFactory {

  SqlSessionFactory original;

  ThreadLocal<SqlSession> sqlSessionLocal = new ThreadLocal<>();

  public void prepareSqlSessionForThread() {
    SqlSession sqlSession = sqlSessionLocal.get();
    if (sqlSession == null) {
      sqlSessionLocal.set(new BitcampSqlSession(original.openSession(false)));
    }
  }

  public void clearSqlSessionForThread() {
    BitcampSqlSession sqlSession = (BitcampSqlSession) sqlSessionLocal.get();
    sqlSession.closeOriginal();
    sqlSessionLocal.set(null);
  }

  public BitcampSqlSessionFactory(SqlSessionFactory original) {
    this.original = original;
  }

  @Override
  public SqlSession openSession() {
    SqlSession sqlSession = sqlSessionLocal.get();
    if (sqlSession == null) {
      // 트랜잭션 없이 사용할 SqlSession 리턴
      return original.openSession(true);
    }

    // 스레드에서 공유할 SqlSession 객체 리턴
    return sqlSession;
  }

  @Override
  public SqlSession openSession(boolean autoCommit) {
    return original.openSession(autoCommit);
  }

  @Override
  public SqlSession openSession(Connection connection) {
    return original.openSession(connection);
  }

  @Override
  public SqlSession openSession(TransactionIsolationLevel level) {
    return original.openSession(level);
  }

  @Override
  public SqlSession openSession(ExecutorType execType) {
    return original.openSession(execType);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return original.openSession(execType, autoCommit);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return original.openSession(execType, level);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return original.openSession(execType, connection);
  }

  @Override
  public Configuration getConfiguration() {
    return original.getConfiguration();
  }


}
