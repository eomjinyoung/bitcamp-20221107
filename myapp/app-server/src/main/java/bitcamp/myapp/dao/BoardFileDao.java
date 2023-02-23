package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.BoardFile;

public interface BoardFileDao {
  int insert(BoardFile boardFile);
  List<BoardFile> findAllOfBoard(int boardNo);
  BoardFile findByNo(int boardFileNo);
  int delete(int no);
}























