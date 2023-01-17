package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Student;

public class StudentDao extends ObjectDao {

  public Student findByNo(int no) {
    Student s = new Student();
    s.setNo(no);
    return (Student) this.get(this.indexOf(s));
  }

  @Override
  protected int indexOf(Object obj) {
    for (int i = 0; i < this.size(); i++) {
      if (((Student)this.objects[i]).getNo() == ((Student) obj).getNo()) {
        return i;
      }
    }
    return -1;
  }
}







