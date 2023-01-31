package bitcamp.myapp.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Teacher;

public class TeacherDao {

  List<Teacher> list;

  int lastNo;

  public TeacherDao(List<Teacher> list) {
    this.list = list;
  }

  public void insert(Teacher t) {
    t.setNo(++lastNo);
    t.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(t);
  }

  public Teacher[] findAll() {
    Teacher[] teachers = new Teacher[list.size()];
    Iterator<Teacher> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
      teachers[index++] = i.next();
    }
    return teachers;
  }

  public Teacher findByNo(int no) {
    Teacher t = new Teacher();
    t.setNo(no);

    int index = list.indexOf(t);
    if (index == -1) {
      return null;
    }

    return list.get(index);
  }

  public void update(Teacher t) {
    int index = list.indexOf(t);
    list.set(index, t);
  }

  public boolean delete(Teacher t) {
    return list.remove(t);
  }

  public void save(String filename) {
    try (FileOutputStream out0 = new FileOutputStream(filename);
        DataOutputStream out = new DataOutputStream(out0)) {

      out.writeInt(list.size());

      for (Teacher t : list) {
        out.writeInt(t.getNo());
        out.writeUTF(t.getName());
        out.writeUTF(t.getTel());
        out.writeUTF(t.getCreatedDate());
        out.writeUTF(t.getEmail());
        out.writeInt(t.getDegree());
        out.writeUTF(t.getSchool());
        out.writeUTF(t.getMajor());
        out.writeInt(t.getWage());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void load(String filename) {
    if (list.size() > 0) {
      return;
    }

    try (FileInputStream in0 = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(in0)) {

      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Teacher t = new Teacher();
        t.setNo(in.readInt());
        t.setName(in.readUTF());
        t.setTel(in.readUTF());
        t.setCreatedDate(in.readUTF());
        t.setEmail(in.readUTF());
        t.setDegree(in.readInt());
        t.setSchool(in.readUTF());
        t.setMajor(in.readUTF());
        t.setWage(in.readInt());

        list.add(t);
      }

      if (list.size() > 0) {
        lastNo = list.get(list.size() - 1).getNo();
      }

    } catch (FileNotFoundException e) {
      System.out.println("데이터 파일이 존재하지 않습니다!");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}







