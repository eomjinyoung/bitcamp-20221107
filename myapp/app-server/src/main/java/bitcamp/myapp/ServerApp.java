package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.servlet.BoardServlet;
import bitcamp.myapp.servlet.StudentServlet;
import bitcamp.myapp.servlet.TeacherServlet;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Student;
import bitcamp.myapp.vo.Teacher;

public class ServerApp {

  public static void main(String[] args) {
    new ServerApp().service(8888);
    System.out.println("서버 종료!");
  }

  void service(int port) {
    System.out.println("서버 실행 중...");

    try (ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      BoardDao boardDao = new BoardDao(new LinkedList<Board>());
      boardDao.load("board.json");

      StudentDao studentDao = new StudentDao(new ArrayList<Student>());
      studentDao.load("student.json");

      TeacherDao teacherDao = new TeacherDao(new ArrayList<Teacher>());
      teacherDao.load("teacher.json");

      StudentServlet studentServlet = new StudentServlet(studentDao);
      TeacherServlet teacherServlet = new TeacherServlet(teacherDao);
      BoardServlet boardServlet = new BoardServlet(boardDao);

      while (true) {
        String dataName = in.readUTF();
        switch (dataName) {
          case "board":
            boardServlet.service(in, out);
            boardDao.save("board.json");
            break;
          case "student":
            studentServlet.service(in, out);
            studentDao.save("student.json");
            break;
          case "teacher":
            teacherServlet.service(in, out);
            teacherDao.save("teacher.json");
            break;
          case "quit":
            return;
        }
      }

    } catch (Exception e) {
      System.out.println("서버 오류 발생!");
      e.printStackTrace();
    }
  }

}









