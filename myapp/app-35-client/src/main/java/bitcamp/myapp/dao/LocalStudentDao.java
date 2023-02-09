package bitcamp.myapp.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import bitcamp.myapp.vo.Student;

public class LocalStudentDao implements StudentDao {

  List<Student> list;

  int lastNo;

  public LocalStudentDao(List<Student> list) {
    this.list = list;
  }

  @Override
  public void insert(Student s) {
    s.setNo(++lastNo);
    s.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(s);
  }

  @Override
  public Student[] findAll() {
    Student[] students = new Student[list.size()];
    Iterator<Student> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
      students[index++] = i.next();
    }
    return students;
  }

  @Override
  public Student findByNo(int no) {
    Student s = new Student();
    s.setNo(no);

    int index = list.indexOf(s);
    if (index == -1) {
      return null;
    }
    return list.get(index);
  }

  @Override
  public void update(Student s) {
    int index = list.indexOf(s);
    list.set(index, s);
  }

  @Override
  public boolean delete(Student s) {
    return list.remove(s);
  }

  public void save(String filename) {
    try (FileWriter out = new FileWriter(filename)) {

      out.write(new Gson().toJson(list));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void load(String filename) {
    if (list.size() > 0) { // 중복 로딩 방지!
      return;
    }

    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {

      list = new Gson().fromJson(in, new TypeToken<List<Student>>() {});

      if (list.size() > 0) {
        lastNo = list.get(list.size() - 1).getNo();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}







