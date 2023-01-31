package bitcamp.myapp.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Student;
import bitcamp.util.BinaryDecoder;
import bitcamp.util.BinaryEncoder;

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
    try (FileOutputStream out = new FileOutputStream(filename)) {

      out.write(BinaryEncoder.write(list.size()));

      for (Student s : list) {
        out.write(BinaryEncoder.write(s.getNo()));
        out.write(BinaryEncoder.write(s.getName()));
        out.write(BinaryEncoder.write(s.getTel()));
        out.write(BinaryEncoder.write(s.getCreatedDate()));
        out.write(BinaryEncoder.write(s.getPostNo()));
        out.write(BinaryEncoder.write(s.getBasicAddress()));
        out.write(BinaryEncoder.write(s.getDetailAddress()));
        out.write(BinaryEncoder.write(s.isWorking()));
        out.write(BinaryEncoder.write(s.getGender()));
        out.write(BinaryEncoder.write(s.getLevel()));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void load(String filename) {
    if (list.size() > 0) {
      return;
    }

    try (FileInputStream in = new FileInputStream(filename)) {

      int size = BinaryDecoder.readInt(in);

      for (int i = 0; i < size; i++) {
        Student s = new Student();
        s.setNo(BinaryDecoder.readInt(in));
        s.setName(BinaryDecoder.readString(in));
        s.setTel(BinaryDecoder.readString(in));
        s.setCreatedDate(BinaryDecoder.readString(in));
        s.setPostNo(BinaryDecoder.readString(in));
        s.setBasicAddress(BinaryDecoder.readString(in));
        s.setDetailAddress(BinaryDecoder.readString(in));
        s.setWorking(BinaryDecoder.readBoolean(in));
        s.setGender(BinaryDecoder.readChar(in));
        s.setLevel(BinaryDecoder.readByte(in));

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







