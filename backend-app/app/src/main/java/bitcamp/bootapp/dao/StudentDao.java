package bitcamp.bootapp.dao;

import java.sql.Date;
import java.util.Arrays;
import org.springframework.stereotype.Repository;
import bitcamp.bootapp.vo.Student;

@Repository
public class StudentDao {
  private static final int SIZE = 100;

  private int no;
  private int count;
  private Student[] students = new Student[SIZE];

  public void insert(Student student) {
    student.setNo(++no);
    student.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    this.students[this.count++] = student;
  }

  public Student[] findAll() {
    return Arrays.copyOf(students, count);
  }

  public Student findByNo(int no) {
    for (int i = 0; i < this.count; i++) {
      if (this.students[i].getNo() == no) {
        return this.students[i];
      }
    }
    return null;
  }

  public void update(Student student) {
    this.students[this.indexOf(student)] = student;
  }

  public void delete(Student student) {
    for (int i = this.indexOf(student) + 1; i < this.count; i++) {
      this.students[i - 1] = this.students[i];
    }
    this.students[--this.count] = null; // 레퍼런스 카운트를 줄인다.
  }

  private int indexOf(Student b) {
    for (int i = 0; i < this.count; i++) {
      if (this.students[i].getNo() == b.getNo()) {
        return i;
      }
    }
    return -1;
  }
}







