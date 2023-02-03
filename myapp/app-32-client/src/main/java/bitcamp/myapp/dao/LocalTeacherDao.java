package bitcamp.myapp.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import bitcamp.myapp.vo.Teacher;

public class LocalTeacherDao implements TeacherDao {

  List<Teacher> list;

  int lastNo;

  public LocalTeacherDao(List<Teacher> list) {
    this.list = list;
  }

  @Override
  public void insert(Teacher t) {
    t.setNo(++lastNo);
    t.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(t);
  }

  @Override
  public Teacher[] findAll() {
    Teacher[] teachers = new Teacher[list.size()];
    Iterator<Teacher> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
      teachers[index++] = i.next();
    }
    return teachers;
  }

  @Override
  public Teacher findByNo(int no) {
    Teacher t = new Teacher();
    t.setNo(no);

    int index = list.indexOf(t);
    if (index == -1) {
      return null;
    }

    return list.get(index);
  }

  @Override
  public void update(Teacher t) {
    int index = list.indexOf(t);
    list.set(index, t);
  }

  @Override
  public boolean delete(Teacher t) {
    return list.remove(t);
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

      list = new Gson().fromJson(in, new TypeToken<List<Teacher>>() {});

      if (list.size() > 0) {
        lastNo = list.get(list.size() - 1).getNo();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}







