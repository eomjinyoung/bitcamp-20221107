package bitcamp.myapp.service;

import java.util.List;
import bitcamp.myapp.vo.Board;

public interface BoardService {
  void add(Board board);
  List<Board> list(String keyword);
  Board get(int no);
  void update(Board board);
  void delete(int no);
  void deleteFile(int fileNo);
}





