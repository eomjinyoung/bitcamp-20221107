package bitcamp.myapp.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Student;

public class StudentDao {

  List<Student> list;

  int lastNo;

  public StudentDao(List<Student> list) {
    this.list = list;
  }

  public void insert(Student s) {
    s.setNo(++lastNo);
    s.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(s);
  }

  public Student[] findAll() {
    Student[] students = new Student[list.size()];
    Iterator<Student> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
      students[index++] = i.next();
    }
    return students;
  }

  public Student findByNo(int no) {
    Student s = new Student();
    s.setNo(no);

    int index = list.indexOf(s);
    if (index == -1) {
      return null;
    }
    return list.get(index);
  }

  public void update(Student s) {
    int index = list.indexOf(s);
    list.set(index, s);
  }

  public boolean delete(Student s) {
    return list.remove(s);
  }

  public void save(String filename) {
    try (FileOutputStream out0 = new FileOutputStream(filename);
        DataOutputStream out = new DataOutputStream(out0)) {

      out.writeInt(list.size());

      for (Student s : list) {
        out.writeInt(s.getNo());
        out.writeUTF(s.getName());
        out.writeUTF(s.getTel());
        out.writeUTF(s.getCreatedDate());
        out.writeUTF(s.getPostNo());
        out.writeUTF(s.getBasicAddress());
        out.writeUTF(s.getDetailAddress());
        out.writeBoolean(s.isWorking());
        out.writeChar(s.getGender());
        out.writeByte(s.getLevel());
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
        Student s = new Student();
        s.setNo(in.readInt());
        s.setName(in.readUTF());
        s.setTel(in.readUTF());
        s.setCreatedDate(in.readUTF());
        s.setPostNo(in.readUTF());
        s.setBasicAddress(in.readUTF());
        s.setDetailAddress(in.readUTF());
        s.setWorking(in.readBoolean());
        s.setGender(in.readChar());
        s.setLevel(in.readByte());

        list.add(s);
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







