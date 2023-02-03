package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
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

public class ServerApp0 {

  BoardDao boardDao = new BoardDao(new LinkedList<Board>());
  StudentDao studentDao = new StudentDao(new ArrayList<Student>());
  TeacherDao teacherDao = new TeacherDao(new ArrayList<Teacher>());

  StudentServlet studentServlet = new StudentServlet(studentDao);
  TeacherServlet teacherServlet = new TeacherServlet(teacherDao);
  BoardServlet boardServlet = new BoardServlet(boardDao);

  public static void main(String[] args) {
    new ServerApp().service(8888);
    System.out.println("서버 종료!");
  }

  void service(int port) {
    System.out.println("서버 실행 중...");

    try (ServerSocket serverSocket = new ServerSocket(port)) {

      boardDao.load("board.json");
      studentDao.load("student.json");
      teacherDao.load("teacher.json");

      while (true) {
        new RequestProcessorThread(serverSocket.accept()).start(); // 스레드를 실행시킨다.
      }

    } catch (Exception e) {
      System.out.println("서버 오류 발생!");
      e.printStackTrace();
    }
  }

  class RequestProcessorThread extends Thread {
    Socket socket;

    public RequestProcessorThread(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      // 스레드를 통해 독립적으로 실행시키고 싶은 코드가 있다면
      // run() 메서드 안에 두어라!
      // 또는 run() 메서드에서 해당 코드를 호출하도록 만들어라!
      //
      try (Socket socket2 = socket;
          DataInputStream in = new DataInputStream(socket.getInputStream());
          DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

        InetAddress address = socket.getInetAddress();
        System.out.printf("%s 가 연결함!\n", address.getHostAddress());

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
        }
      } catch (Exception e) {
        System.out.println("실행 오류!");
      }
    }
  }
}









