package bitcamp.myapp.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Board;
import bitcamp.util.BinaryDecoder;
import bitcamp.util.BinaryEncoder;

public class BoardDao {

  List<Board> list;
  int lastNo;

  public BoardDao(List<Board> list) {
    this.list = list;
  }

  public void insert(Board board) {
    board.setNo(++lastNo);
    board.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(board);
  }

  public Board[] findAll() {
    Board[] boards = new Board[list.size()];
    Iterator<Board> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
      boards[index++] = i.next();
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

    return list.get(index);
  }

  public void update(Board b) {
    int index = list.indexOf(b);
    list.set(index, b);
  }

  public boolean delete(Board b) {
    return list.remove(b);
  }

  public void save(String filename) {
    try (
        // 1) 바이너리 데이터(바이트배열)를 출력할 도구를 준비한다.
        FileOutputStream out = new FileOutputStream(filename)) {

      // 2) 게시글 개수를 출력 : 4byte
      out.write(BinaryEncoder.write(list.size()));

      // 3) 게시글 출력
      // 목록에서 Board 객체를 꺼내 바이트 배열로 만든 다음 출력한다.
      for (Board b : list) {
        out.write(BinaryEncoder.write(b.getNo()));
        out.write(BinaryEncoder.write(b.getTitle()));
        out.write(BinaryEncoder.write(b.getContent()));
        out.write(BinaryEncoder.write(b.getPassword()));
        out.write(BinaryEncoder.write(b.getViewCount()));
        out.write(BinaryEncoder.write(b.getCreatedDate()));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void load(String filename) {
    if (list.size() > 0) { // 중복 로딩 방지!
      return;
    }

    try (
        // 1) 바이너리 데이터를 읽을 도구 준비
        FileInputStream in = new FileInputStream(filename)) {

      // 2) 저장된 게시글 개수를 읽는다: 4byte
      int size = BinaryDecoder.readInt(in);

      // 3) 게시글 개수 만큼 반복해서 데이터를 읽어 Board 객체에 저장한다.
      for (int i = 0; i < size; i++) {
        // 4) 바이너리 데이터를 저장한 순서대로 읽어서 Board 객체에 담는다.
        Board b = new Board();
        b.setNo(BinaryDecoder.readInt(in));
        b.setTitle(BinaryDecoder.readString(in));
        b.setContent(BinaryDecoder.readString(in));
        b.setPassword(BinaryDecoder.readString(in));
        b.setViewCount(BinaryDecoder.readInt(in));
        b.setCreatedDate(BinaryDecoder.readString(in));

        // 5) Board 객체를 목록에 추가한다.
        list.add(b);
      }

      if (list.size() > 0) {
        //        int lastIndex = list.size() - 1;
        //        Board b = list.get(lastIndex);
        //        lastNo = b.getNo();
        lastNo = list.get(list.size() - 1).getNo();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}























