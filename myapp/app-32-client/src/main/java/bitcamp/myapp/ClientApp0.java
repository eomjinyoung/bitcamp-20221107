package bitcamp.myapp;

import java.util.ArrayList;
import java.util.LinkedList;
import bitcamp.myapp.dao.LocalBoardDao;
import bitcamp.myapp.dao.LocalStudentDao;
import bitcamp.myapp.dao.LocalTeacherDao;
import bitcamp.myapp.handler.BoardHandler;
import bitcamp.myapp.handler.StudentHandler;
import bitcamp.myapp.handler.TeacherHandler;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Student;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.Prompt;

public class ClientApp0 {

  public static void main(String[] args) {
    new ClientApp0().service();
  }

  void service() {
    LocalBoardDao boardDao = new LocalBoardDao(new LinkedList<Board>());
    boardDao.load("board.json");
    //NetworkBoardDao boardDao = new NetworkBoardDao(in, out);

    LocalStudentDao studentDao = new LocalStudentDao(new ArrayList<Student>());
    studentDao.load("student.json");

    LocalTeacherDao teacherDao = new LocalTeacherDao(new ArrayList<Teacher>());
    teacherDao.load("teacher.json");

    StudentHandler studentHandler = new StudentHandler("학생", studentDao);
    TeacherHandler teacherHandler = new TeacherHandler("강사", teacherDao);
    BoardHandler boardHandler = new BoardHandler("게시판", boardDao);

    loop: while (true) {
      System.out.println("1. 학생관리");
      System.out.println("2. 강사관리");
      System.out.println("3. 게시판");
      System.out.println("9. 종료");

      int menuNo;
      try {
        menuNo = Prompt.inputInt("메뉴> ");
      } catch (Exception e) {
        System.out.println("메뉴 번호가 옳지 않습니다!");
        continue;
      }

      try {
        switch (menuNo) {
          case 1:
            studentHandler.service();
            studentDao.save("student.json");
            break;
          case 2:
            teacherHandler.service();
            teacherDao.save("teacher.json");
            break;
          case 3:
            boardHandler.service();
            boardDao.save("board.json");
            break;
          case 9: break loop; // loop 라벨이 붙은 while 문을 나간다.
          default:
            System.out.println("잘못된 메뉴 번호 입니다.");
        }
      } catch (Exception e) {
        System.out.printf("명령 실행 중 오류 발생! - %s : %s\n",
            e.getMessage(),
            e.getClass().getSimpleName());
      }
    }

    System.out.println("안녕히 가세요!");

    Prompt.close();
  }
}









