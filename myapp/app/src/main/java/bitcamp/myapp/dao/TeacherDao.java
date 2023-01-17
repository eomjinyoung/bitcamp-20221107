package bitcamp.myapp.dao;

import java.sql.Date;
import bitcamp.myapp.vo.Teacher;

public class TeacherDao extends ObjectDao {

  int lastNo;

  public Teacher findByNo(int no) {
    Teacher t = new Teacher();
    t.setNo(no);
    return (Teacher) this.get(this.indexOf(t));
  }

  @Override
  protected int indexOf(Object obj) {
    for (int i = 0; i < this.size(); i++) {
      if (((Teacher) this.objects[i]).getNo() == ((Teacher) obj).getNo()) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void insert(Object object) {
    Teacher t = (Teacher) object;
    t.setNo(++lastNo);
    t.setCreatedDate(new Date(System.currentTimeMillis()).toString());

    super.insert(object);
  }
}







