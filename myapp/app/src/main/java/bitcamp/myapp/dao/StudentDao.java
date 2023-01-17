package bitcamp.myapp.dao;

import java.sql.Date;
import bitcamp.myapp.vo.Student;

public class StudentDao extends ObjectDao {

  int lastNo;

  public Student findByNo(int no) {
    Student s = new Student();
    s.setNo(no);
    return (Student) this.get(this.indexOf(s));
  }

  @Override
  protected int indexOf(Object obj) {
    for (int i = 0; i < this.size(); i++) {
      if (((Student) this.objects[i]).getNo() == ((Student) obj).getNo()) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void insert(Object object) {
    Student s = (Student) object;
    s.setNo(++lastNo);
    s.setCreatedDate(new Date(System.currentTimeMillis()).toString());

    super.insert(object);
  }
}







