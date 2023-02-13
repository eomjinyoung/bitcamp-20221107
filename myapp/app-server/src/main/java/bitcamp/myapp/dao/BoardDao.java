package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.Board;

public interface BoardDao {
  void insert(Board board);
  List<Board> findAll();
  Board findByNo(int no);
  void increaseViewCount(int no);
  List<Board> findByKeyword(String keyword);
  int update(Board b);
  int delete(int no);
}























