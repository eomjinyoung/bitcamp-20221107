package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;

public interface BoardDao {
  void insert(Board board);
  Board[] findAll();
  Board findByNo(int no);
  void update(Board b);
  boolean delete(Board b);
}























