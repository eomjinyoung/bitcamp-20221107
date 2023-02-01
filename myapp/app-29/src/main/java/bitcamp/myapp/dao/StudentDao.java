package bitcamp.myapp.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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
    try (FileWriter out = new FileWriter(filename)) {

      list.forEach(obj -> {
        try {
          out.write(String.format("%d,%s,%s,%s,%s,%s,%s,%b,%s,%d\n",
              obj.getNo(),
              obj.getName(),
              obj.getTel(),
              obj.getCreatedDate(),
              obj.getPostNo(),
              obj.getBasicAddress(),
              obj.getDetailAddress(),
              obj.isWorking(),
              obj.getGender(),
              obj.getLevel()));
        } catch (Exception e) {
          System.out.println("데이터 출력 중 오류 발생!");
          e.printStackTrace();
        }
      });

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void load(String filename) {
    if (list.size() > 0) { // 중복 로딩 방지!
      return;
    }

    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {

      while (true) {
        String str = in.readLine();
        if (str == null) {
          break;
        }
        String[] values = str.split(",");

        Student obj = new Student();
        obj.setNo(Integer.parseInt(values[0]));
        obj.setName(values[1]);
        obj.setTel(values[2]);
        obj.setCreatedDate(values[3]);
        obj.setPostNo(values[4]);
        obj.setBasicAddress(values[5]);
        obj.setDetailAddress(values[6]);
        obj.setWorking(Boolean.parseBoolean(values[7]));
        obj.setGender(values[8].charAt(0));
        obj.setLevel(Byte.parseByte(values[9]));

        list.add(obj);
      }

      if (list.size() > 0) {
        lastNo = list.get(list.size() - 1).getNo();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}







