package bitcamp.myapp.dao;

import java.util.List;
import java.util.Map;
import bitcamp.myapp.vo.Student;

public interface StudentDao {
  void insert(Student s);
  List<Student> findAll(String keyword);
  Student findByNo(int no);
  Student findByEmailAndPassword(Map<String,Object> params);
  int update(Student s);
  int delete(int no);
}







