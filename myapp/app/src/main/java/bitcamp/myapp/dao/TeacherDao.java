package bitcamp.myapp.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.BinaryDecoder;
import bitcamp.util.BinaryEncoder;

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
    try (FileOutputStream out = new FileOutputStream(filename)) {

      out.write(BinaryEncoder.write(list.size()));

      for (Teacher t : list) {
        out.write(BinaryEncoder.write(t.getNo()));
        out.write(BinaryEncoder.write(t.getName()));
        out.write(BinaryEncoder.write(t.getTel()));
        out.write(BinaryEncoder.write(t.getCreatedDate()));
        out.write(BinaryEncoder.write(t.getEmail()));
        out.write(BinaryEncoder.write(t.getDegree()));
        out.write(BinaryEncoder.write(t.getSchool()));
        out.write(BinaryEncoder.write(t.getMajor()));
        out.write(BinaryEncoder.write(t.getWage()));
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
        Teacher t = new Teacher();
        t.setNo(BinaryDecoder.readInt(in));
        t.setName(BinaryDecoder.readString(in));
        t.setTel(BinaryDecoder.readString(in));
        t.setCreatedDate(BinaryDecoder.readString(in));
        t.setEmail(BinaryDecoder.readString(in));
        t.setDegree(BinaryDecoder.readInt(in));
        t.setSchool(BinaryDecoder.readString(in));
        t.setMajor(BinaryDecoder.readString(in));
        t.setWage(BinaryDecoder.readInt(in));

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







