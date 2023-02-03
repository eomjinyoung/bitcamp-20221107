package bitcamp.myapp.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.google.gson.Gson;
import bitcamp.myapp.vo.Teacher;

public class NetworkTeacherDao implements TeacherDao {

  DataInputStream in;
  DataOutputStream out;

  public NetworkTeacherDao(DataInputStream in, DataOutputStream out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void insert(Teacher b) {
    fetch("teacher", "insert", b);
  }

  @Override
  public Teacher[] findAll() {
    return new Gson().fromJson(fetch("teacher", "findAll"), Teacher[].class);
  }

  @Override
  public Teacher findByNo(int no) {
    return new Gson().fromJson(fetch("teacher", "findByNo", no), Teacher.class);
  }

  @Override
  public void update(Teacher b) {
    fetch("teacher", "update", b);
  }

  @Override
  public boolean delete(Teacher b) {
    fetch("teacher", "delete", b);
    return true;
  }

  public String fetch(String dataName, String action, Object... data) throws DaoException {
    try {
      out.writeUTF(dataName);
      out.writeUTF(action);

      if (data.length > 0) {
        out.writeUTF(new Gson().toJson(data[0]));
      }

      // 응답
      String status = in.readUTF();
      if (status.equals("400")) {
        throw new DaoException("클라이언트 요청 오류!");
      } else if (status.equals("500")) {
        throw new DaoException("서버 실행 오류!");
      }
      return in.readUTF();

    } catch (DaoException e) {
      throw e;
    } catch (Exception e) {
      throw new DaoException("오류 발생!", e);
    }
  }
}























