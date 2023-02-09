package bitcamp.myapp.dao;

import com.google.gson.Gson;
import bitcamp.myapp.vo.Teacher;

public class NetworkTeacherDao implements TeacherDao {

  DaoStub daoStub;

  public NetworkTeacherDao(DaoStub daoStub) {
    this.daoStub = daoStub;
  }

  @Override
  public void insert(Teacher b) {
    daoStub.fetch("teacher", "insert", b);
  }

  @Override
  public Teacher[] findAll() {
    return new Gson().fromJson(daoStub.fetch("teacher", "findAll"), Teacher[].class);
  }

  @Override
  public Teacher findByNo(int no) {
    return new Gson().fromJson(daoStub.fetch("teacher", "findByNo", no), Teacher.class);
  }

  @Override
  public void update(Teacher b) {
    daoStub.fetch("teacher", "update", b);
  }

  @Override
  public boolean delete(Teacher b) {
    daoStub.fetch("teacher", "delete", b);
    return true;
  }
}























