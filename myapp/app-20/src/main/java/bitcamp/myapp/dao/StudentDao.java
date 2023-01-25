package bitcamp.myapp.dao;

import java.sql.Date;
import bitcamp.myapp.vo.Student;
import bitcamp.util.ArrayList;

public class StudentDao {

  ArrayList list = new ArrayList();

  int lastNo;

  public void insert(Student s) {
    s.setNo(++lastNo);
    s.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.append(s);
  }

  public Student[] findAll() {
    Student[] students = new Student[list.size()];
    Object[] arr = list.getList();
    for (int i = 0; i < students.length; i++) {
      students[i] = (Student) arr[i];
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
    return (Student) list.get(index);
  }

  public void update(Student s) {
    int index = list.indexOf(s);
    list.modify(index, s);
  }

  public boolean delete(Student s) {
    return list.delete(s);
  }

}







