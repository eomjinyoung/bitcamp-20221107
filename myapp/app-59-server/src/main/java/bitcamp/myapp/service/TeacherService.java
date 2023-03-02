package bitcamp.myapp.service;

import java.util.List;
import bitcamp.myapp.vo.Teacher;

public interface TeacherService {
  void add(Teacher teacher);
  List<Teacher> list();
  Teacher get(int no);
  Teacher get(String email, String password);
  void update(Teacher teacher);
  void delete(int no);
}





