package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.Student;

public interface StudentDao {
  void insert(Student s);
  List<Student> findAll();
  Student findByNo(int no);
  List<Student> findByKeyword(String keyword);
  int update(Student s);
  int delete(int no);
}







