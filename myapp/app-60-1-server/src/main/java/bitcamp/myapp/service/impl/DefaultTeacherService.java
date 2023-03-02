package bitcamp.myapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Teacher;

@Service
public class DefaultTeacherService implements TeacherService {

  @Autowired private PlatformTransactionManager txManager;
  @Autowired private MemberDao memberDao;
  @Autowired private TeacherDao teacherDao;

  @Override
  public void add(Teacher teacher) {
    // 트랜잭션 동작을 설정
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 위에서 설정한 대로 동작할 트랜잭션을 준비
    TransactionStatus status = txManager.getTransaction(def);
    try {
      memberDao.insert(teacher);
      teacherDao.insert(teacher);
      txManager.commit(status);

    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public List<Teacher> list() {
    return teacherDao.findAll();
  }

  @Override
  public Teacher get(int no) {
    return teacherDao.findByNo(no);
  }

  @Override
  public Teacher get(String email, String password) {
    Map<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);

    return teacherDao.findByEmailAndPassword(paramMap);
  }

  @Override
  public void update(Teacher teacher) {
    // 트랜잭션 동작을 설정
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 위에서 설정한 대로 동작할 트랜잭션을 준비
    TransactionStatus status = txManager.getTransaction(def);
    try {
      if (memberDao.update(teacher) == 1 &&
          teacherDao.update(teacher) == 1) {
        txManager.commit(status);
      } else {
        throw new RuntimeException("강사가 존재하지 않습니다.");
      }
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public void delete(int no) {
    // 트랜잭션 동작을 설정
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 위에서 설정한 대로 동작할 트랜잭션을 준비
    TransactionStatus status = txManager.getTransaction(def);
    try {
      if (teacherDao.delete(no) == 1 &&
          memberDao.delete(no) == 1) {
        txManager.commit(status);
      } else {
        throw new RuntimeException("강사가 존재하지 않습니다.");
      }
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }
}





