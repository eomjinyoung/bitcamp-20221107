package bitcamp.myapp.dao;

import com.google.gson.Gson;
import bitcamp.myapp.vo.Student;

public class NetworkStudentDao implements StudentDao {

  DaoStub daoStub;

  public NetworkStudentDao(DaoStub daoStub) {
    this.daoStub = daoStub;
  }

  @Override
  public void insert(Student b) {
    daoStub.fetch("student", "insert", b);
  }

  @Override
  public Student[] findAll() {
    return new Gson().fromJson(daoStub.fetch("student", "findAll"), Student[].class);
  }

  @Override
  public Student findByNo(int no) {
    return new Gson().fromJson(daoStub.fetch("student", "findByNo", no), Student.class);
  }

  @Override
  public void update(Student b) {
    daoStub.fetch("student", "update", b);
  }

  @Override
  public boolean delete(Student b) {
    daoStub.fetch("student", "delete", b);
    return true;
  }
}























