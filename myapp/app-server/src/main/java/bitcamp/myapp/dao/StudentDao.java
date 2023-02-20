package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.Student;

public interface StudentDao {
  void insert(Student s);
  List<Student> findAll(String keyword);
  Student findByNo(int no);
  int update(Student s);
  int delete(int no);
}







