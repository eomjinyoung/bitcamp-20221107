package bitcamp.util;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

public class BitcampSqlSession implements SqlSession {

  SqlSession original;

  public void closeOriginal() {
    original.close();
  }

  public BitcampSqlSession(SqlSession original) {
    this.original = original;
  }

  @Override
  public <T> T selectOne(String statement) {
    return original.selectOne(statement);
  }

  @Override
  public <T> T selectOne(String statement, Object parameter) {
    return original.selectOne(statement, parameter);
  }

  @Override
  public <E> List<E> selectList(String statement) {
    return original.selectList(statement);
  }

  @Override
  public <E> List<E> selectList(String statement, Object parameter) {
    return original.selectList(statement, parameter);
  }

  @Override
  public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
    return original.selectList(statement, parameter, rowBounds);
  }

  @Override
  public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
    return original.selectMap(statement, mapKey);
  }

  @Override
  public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
    return original.selectMap(statement, parameter, mapKey);
  }

  @Override
  public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey,
      RowBounds rowBounds) {
    return original.selectMap(statement, parameter, mapKey, rowBounds);
  }

  @Override
  public <T> Cursor<T> selectCursor(String statement) {
    return original.selectCursor(statement);
  }

  @Override
  public <T> Cursor<T> selectCursor(String statement, Object parameter) {
    return original.selectCursor(statement, parameter);
  }

  @Override
  public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
    return original.selectCursor(statement, parameter, rowBounds);
  }

  @Override
  public void select(String statement, Object parameter, ResultHandler handler) {
    original.select(statement, parameter, handler);
  }

  @Override
  public void select(String statement, ResultHandler handler) {
    original.select(statement, handler);
  }

  @Override
  public void select(String statement, Object parameter, RowBounds rowBounds,
      ResultHandler handler) {
    original.select(statement, parameter, rowBounds, handler);
  }

  @Override
  public int insert(String statement) {
    return original.insert(statement);
  }

  @Override
  public int insert(String statement, Object parameter) {
    return original.insert(statement, parameter);
  }

  @Override
  public int update(String statement) {
    return original.update(statement);
  }

  @Override
  public int update(String statement, Object parameter) {
    return original.update(statement, parameter);
  }

  @Override
  public int delete(String statement) {
    return original.delete(statement);
  }

  @Override
  public int delete(String statement, Object parameter) {
    return original.delete(statement, parameter);
  }

  @Override
  public void commit() {
    original.commit();
  }

  @Override
  public void commit(boolean force) {
    original.commit(force);
  }

  @Override
  public void rollback() {
    original.rollback();
  }

  @Override
  public void rollback(boolean force) {
    original.rollback(force);
  }

  @Override
  public List<BatchResult> flushStatements() {
    return original.flushStatements();
  }

  @Override
  public void close() {
    //original.close();
  }

  @Override
  public void clearCache() {
    original.clearCache();
  }

  @Override
  public Configuration getConfiguration() {
    return original.getConfiguration();
  }

  @Override
  public <T> T getMapper(Class<T> type) {
    return original.getMapper(type);
  }

  @Override
  public Connection getConnection() {
    return original.getConnection();
  }


}
