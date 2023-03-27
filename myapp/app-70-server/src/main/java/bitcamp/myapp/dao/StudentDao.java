package bitcamp.myapp.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import bitcamp.myapp.vo.Student;

@Mapper
public interface StudentDao {
  void insert(Student s);
  List<Student> findAll(String keyword);
  Student findByNo(int no);
  Student findByEmailAndPassword(Map<String,Object> params);
  Student findByEmail(String email);
  int update(Student s);
  int delete(int no);
}







