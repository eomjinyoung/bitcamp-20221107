package bitcamp.myapp.dao;

import java.util.List;
import java.util.Map;
import bitcamp.myapp.vo.Teacher;

public interface TeacherDao {
  void insert(Teacher t);
  List<Teacher> findAll();
  Teacher findByNo(int no);
  Teacher findByEmailAndPassword(Map<String, Object> params);
  int update(Teacher t);
  int delete(int no);
}







