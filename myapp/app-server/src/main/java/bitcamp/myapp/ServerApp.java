package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.servlet.BoardServlet;
import bitcamp.myapp.servlet.StudentServlet;
import bitcamp.myapp.servlet.TeacherServlet;
import bitcamp.myapp.vo.Board;

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

      StudentServlet studentServlet = new StudentServlet("학생");
      TeacherServlet teacherServlet = new TeacherServlet("강사");
      BoardServlet boardServlet = new BoardServlet(boardDao);

      while (true) {
        String dataName = in.readUTF();
        switch (dataName) {
          case "board":
            boardServlet.service(in, out);
            boardDao.save("board.json");
            break;
        }
      }

    } catch (Exception e) {
      System.out.println("서버 오류 발생!");
      e.printStackTrace();
    }
  }

}









