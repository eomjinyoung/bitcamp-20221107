package bitcamp.myapp.dao;

import java.sql.Date;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.ArrayList;

public class TeacherDao {

  ArrayList list = new ArrayList();

  int lastNo;

  public void insert(Teacher t) {
    t.setNo(++lastNo);
    t.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.append(t);
  }

  public Teacher[] findAll() {
    Teacher[] teachers = new Teacher[list.size()];
    Object[] arr = list.getList();
    for (int i = 0; i < teachers.length; i++) {
      teachers[i] = (Teacher) arr[i];
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

    return (Teacher) list.get(index);
  }

  public void update(Teacher t) {
    int index = list.indexOf(t);
    list.modify(index, t);
  }

  public boolean delete(Teacher t) {
    return list.delete(t);
  }

}







