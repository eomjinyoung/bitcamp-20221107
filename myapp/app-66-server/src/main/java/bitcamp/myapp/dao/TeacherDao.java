package bitcamp.myapp.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import bitcamp.myapp.vo.Teacher;

@Mapper
public interface TeacherDao {
  void insert(Teacher t);
  List<Teacher> findAll();
  Teacher findByNo(int no);
  Teacher findByEmailAndPassword(Map<String, Object> params);
  int update(Teacher t);
  int delete(int no);
}







