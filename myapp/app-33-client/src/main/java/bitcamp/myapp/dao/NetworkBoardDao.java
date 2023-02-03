package bitcamp.myapp.dao;

import com.google.gson.Gson;
import bitcamp.myapp.vo.Board;

public class NetworkBoardDao implements BoardDao {

  DaoStub daoStub;

  public NetworkBoardDao(DaoStub daoStub) {
    this.daoStub = daoStub;
  }

  @Override
  public void insert(Board b) {
    daoStub.fetch("board", "insert", b);
  }

  @Override
  public Board[] findAll() {
    return new Gson().fromJson(daoStub.fetch("board", "findAll"), Board[].class);
  }

  @Override
  public Board findByNo(int no) {
    return new Gson().fromJson(daoStub.fetch("board", "findByNo", no), Board.class);
  }

  @Override
  public void update(Board b) {
    daoStub.fetch("board", "update", b);
  }

  @Override
  public boolean delete(Board b) {
    daoStub.fetch("board", "delete", b);
    return true;
  }
}























