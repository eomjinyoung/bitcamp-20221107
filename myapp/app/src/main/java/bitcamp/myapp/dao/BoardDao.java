package bitcamp.myapp.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Board;

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
    try (FileOutputStream out0 = new FileOutputStream(filename);
        DataOutputStream out = new DataOutputStream(out0)) {

      out.writeInt(list.size());

      for (Board b : list) {
        out.writeInt(b.getNo());
        out.writeUTF(b.getTitle());
        out.writeUTF(b.getContent());
        out.writeUTF(b.getPassword());
        out.writeInt(b.getViewCount());
        out.writeUTF(b.getCreatedDate());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void load(String filename) {
    if (list.size() > 0) { // 중복 로딩 방지!
      return;
    }

    try (FileInputStream in0 = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(in0)) {

      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Board b = new Board();
        b.setNo(in.readInt());
        b.setTitle(in.readUTF());
        b.setContent(in.readUTF());
        b.setPassword(in.readUTF());
        b.setViewCount(in.readInt());
        b.setCreatedDate(in.readUTF());

        list.add(b);
      }

      if (list.size() > 0) {
        lastNo = list.get(list.size() - 1).getNo();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}























