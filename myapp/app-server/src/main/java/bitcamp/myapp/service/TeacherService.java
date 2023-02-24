package bitcamp.myapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.TransactionManager;

public class TeacherService {

  private TransactionManager txManager;
  private MemberDao memberDao;
  private TeacherDao teacherDao;

  public TeacherService(TransactionManager txManager, MemberDao memberDao, TeacherDao teacherDao) {
    this.txManager = txManager;
    this.memberDao = memberDao;
    this.teacherDao = teacherDao;
  }

  public void add(Teacher teacher) {
    txManager.startTransaction();
    try {
      memberDao.insert(teacher);
      teacherDao.insert(teacher);
      txManager.commit();

    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }

  public List<Teacher> list() {
    return teacherDao.findAll();
  }

  public Teacher get(int no) {
    return teacherDao.findByNo(no);
  }

  public Teacher get(String email, String password) {
    Map<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);

    return teacherDao.findByEmailAndPassword(paramMap);
  }

  public void update(Teacher teacher) {
    try {
      txManager.startTransaction();
      if (memberDao.update(teacher) == 1 &&
          teacherDao.update(teacher) == 1) {
        txManager.commit();
      } else {
        throw new RuntimeException("강사가 존재하지 않습니다.");
      }
    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }

  public void delete(int no) {
    try {
      txManager.startTransaction();
      if (teacherDao.delete(no) == 1 &&
          memberDao.delete(no) == 1) {
        txManager.commit();
      } else {
        throw new RuntimeException("강사가 존재하지 않습니다.");
      }
    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }
}





