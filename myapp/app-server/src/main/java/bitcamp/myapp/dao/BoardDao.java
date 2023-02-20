package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.Board;

public interface BoardDao {
  void insert(Board board);
  List<Board> findAll(String keyword);
  Board findByNo(int no);
  void increaseViewCount(int no);
  int update(Board b);
  int delete(int no);
}























