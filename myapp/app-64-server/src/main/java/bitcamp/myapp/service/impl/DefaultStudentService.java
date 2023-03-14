package bitcamp.myapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;

@Service
public class DefaultStudentService implements StudentService {

  @Autowired private MemberDao memberDao;
  @Autowired private StudentDao studentDao;

  @Transactional
  @Override
  public void add(Student student) {
    memberDao.insert(student);
    studentDao.insert(student);
  }

  @Override
  public List<Student> list(String keyword) {
    return studentDao.findAll(keyword);
  }

  @Override
  public Student get(int no) {
    return studentDao.findByNo(no);
  }

  @Override
  public Student get(String email, String password) {
    Map<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);

    return studentDao.findByEmailAndPassword(paramMap);
  }

  @Transactional
  @Override
  public void update(Student student) {
    if (memberDao.update(student) == 1 &&
        studentDao.update(student) == 1) {
    } else {
      throw new RuntimeException("회원이 존재하지 않습니다.");
    }
  }

  @Transactional
  @Override
  public void delete(int no) {
    if (studentDao.delete(no) == 1 &&
        memberDao.delete(no) == 1) {
    } else {
      throw new RuntimeException("회원이 존재하지 않습니다.");
    }
  }
}





