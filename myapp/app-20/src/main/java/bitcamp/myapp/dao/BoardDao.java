package bitcamp.myapp.dao;

import java.sql.Date;
import bitcamp.myapp.vo.Board;
import bitcamp.util.LinkedList;

public class BoardDao {

  LinkedList list = new LinkedList();

  int lastNo;

  public void insert(Board board) {
    board.setNo(++lastNo);
    board.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(board);
  }

  public Board[] findAll() {
    Board[] boards = new Board[list.size()];
    Object[] arr = list.toArray();
    for (int i = 0; i < boards.length; i++) {
      boards[i] = (Board) arr[i];
    }
    return boards;
  }

  public Board findByNo(int no) {
    Board b = new Board();
    b.setNo(no);

    int index = list.indexOf(b);
    if (index == -1) {
      return null;
    }

    return (Board) list.get(index);
  }

  public void update(Board b) {
    int index = list.indexOf(b);
    list.set(index, b);
  }

  public boolean delete(Board b) {
    return list.remove(b);
  }
}























