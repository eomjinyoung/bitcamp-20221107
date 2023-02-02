package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import bitcamp.myapp.servlet.BoardServlet;
import bitcamp.myapp.servlet.StudentServlet;
import bitcamp.myapp.servlet.TeacherServlet;

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

      StudentServlet studentServlet = new StudentServlet("학생");
      TeacherServlet teacherServlet = new TeacherServlet("강사");
      BoardServlet boardServlet = new BoardServlet();

      String dataName = in.readUTF();
      switch (dataName) {
        case "board":
          boardServlet.service(in, out);
          break;
      }

    } catch (Exception e) {
      System.out.println("서버 오류 발생!");
      e.printStackTrace();
    }
  }

}









