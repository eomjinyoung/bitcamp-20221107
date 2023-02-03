package bitcamp.myapp.servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.google.gson.Gson;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;

public class TeacherServlet {

  private TeacherDao teacherDao;

  public TeacherServlet(TeacherDao teacherDao) {
    this.teacherDao = teacherDao;
  }

  private void onInsert(DataInputStream in, DataOutputStream out) throws Exception {
    Teacher b = new Gson().fromJson(in.readUTF(), Teacher.class);
    this.teacherDao.insert(b);
    out.writeUTF("200");
    out.writeUTF("success");
  }

  private void onFindAll(DataInputStream in, DataOutputStream out) throws Exception {
    out.writeUTF("200");
    out.writeUTF(new Gson().toJson(this.teacherDao.findAll()));
  }

  private void onFindByNo(DataInputStream in, DataOutputStream out) throws Exception {
    int teacherNo = new Gson().fromJson(in.readUTF(), int.class);

    Teacher b = this.teacherDao.findByNo(teacherNo);
    if (b == null) {
      out.writeUTF("400");
      return;
    }
    out.writeUTF("200");
    out.writeUTF(new Gson().toJson(b));
  }

  private void onUpdate(DataInputStream in, DataOutputStream out) throws Exception{
    Teacher teacher = new Gson().fromJson(in.readUTF(), Teacher.class);

    Teacher old = this.teacherDao.findByNo(teacher.getNo());
    if (old == null) {
      out.writeUTF("400");
      return;
    }
    this.teacherDao.update(teacher);
    out.writeUTF("200");
    out.writeUTF("success");
  }

  private void onDelete(DataInputStream in, DataOutputStream out) throws Exception {
    Teacher teacher = new Gson().fromJson(in.readUTF(), Teacher.class);

    Teacher b = this.teacherDao.findByNo(teacher.getNo());
    if (b == null) {
      out.writeUTF("400");
      return;
    }

    this.teacherDao.delete(b);
    out.writeUTF("200");
    out.writeUTF("success");
  }

  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    try {
      // 클라이언트가 요구하는 액션을 읽는다.
      String action = in.readUTF();

      switch (action) {
        case "insert": this.onInsert(in, out); break;
        case "findAll": this.onFindAll(in, out); break;
        case "findByNo": this.onFindByNo(in, out); break;
        case "update": this.onUpdate(in, out); break;
        case "delete": this.onDelete(in, out); break;
        default:
          System.out.println("잘못된 메뉴 번호 입니다.");
      }
    } catch (Exception e) {
      out.writeUTF("500");
    }
  }
}
