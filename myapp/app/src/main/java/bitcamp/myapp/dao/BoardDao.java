package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;

public class BoardDao extends ObjectDao {

  // Board 객체를 게시글 번호를 찾는 메서드
  public Board findByNo(int no) {
    Board b = new Board();
    b.setNo(no);

    int index = this.indexOf(b);

    if (index < 0) {
      return null;
    } else {
      return (Board) this.get(index);
    }
  }

  @Override // 컴파일러에게 오버라이딩을 제대로 했는지 검사해 달라고 표시함
  protected int indexOf(Object obj) {
    for (int i = 0; i < this.size(); i++) {
      if (((Board)this.objects[i]).getNo() == ((Board)obj).getNo()) {
        return i;
      }
    }
    return -1;
  }
}







