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
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;

@Service
public class DefaultStudentService implements StudentService {

  @Autowired private PlatformTransactionManager txManager;
  @Autowired private MemberDao memberDao;
  @Autowired private StudentDao studentDao;

  @Override
  public void add(Student student) {
    // 트랜잭션 동작을 설정
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 위에서 설정한 대로 동작할 트랜잭션을 준비
    TransactionStatus status = txManager.getTransaction(def);
    try {
      memberDao.insert(student);
      studentDao.insert(student);
      txManager.commit(status);

    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
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

  @Override
  public void update(Student student) {
    // 트랜잭션 동작을 설정
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 위에서 설정한 대로 동작할 트랜잭션을 준비
    TransactionStatus status = txManager.getTransaction(def);
    try {
      if (memberDao.update(student) == 1 &&
          studentDao.update(student) == 1) {
        txManager.commit(status);
      } else {
        throw new RuntimeException("회원이 존재하지 않습니다.");
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
      if (studentDao.delete(no) == 1 &&
          memberDao.delete(no) == 1) {
        txManager.commit(status);
      } else {
        throw new RuntimeException("회원이 존재하지 않습니다.");
      }
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }
}





