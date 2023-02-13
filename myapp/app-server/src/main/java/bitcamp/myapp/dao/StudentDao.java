package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.Student;

public interface StudentDao {

  void insert(Student s);

  List<Student> findAll();

  Student findByNo(int no);

  Student[] findByKeyword(String keyword);

  void update(Student s);

  boolean delete(Student s);

}







