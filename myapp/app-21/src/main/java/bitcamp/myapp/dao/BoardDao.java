package bitcamp.myapp.dao;

import java.sql.Date;
import bitcamp.myapp.vo.Board;
import bitcamp.util.List;

public class BoardDao {

  // 특정 클래스를 지정하기 보다는
  // 인터페이스를 통해 관계를 느슨하게 만든다.
  List list;

  public BoardDao(List list) {
    // List 규칙에 따라서 만든 객체를 외부에서 주입받는다.
    // 이렇게 하면 이 클래스는 ArrayList 또는 LinkedList와 같은
    // 특정 클래스와 관계가 없어진다.
    this.list = list;
  }

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























