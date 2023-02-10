package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import bitcamp.myapp.dao.JdbcBoardDao;
import bitcamp.myapp.dao.JdbcStudentDao;
import bitcamp.myapp.dao.JdbcTeacherDao;
import bitcamp.myapp.handler.BoardHandler;
import bitcamp.myapp.handler.StudentHandler;
import bitcamp.myapp.handler.TeacherHandler;
import bitcamp.util.Prompt;

public class ServerApp {

  Connection con;
  StudentHandler studentHandler;
  TeacherHandler teacherHandler;
  BoardHandler boardHandler;

  public static void main(String[] args) {
    new ServerApp().execute(8888);
  }

  public ServerApp() throws Exception{
    this.con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb", "study", "1111");

    JdbcBoardDao boardDao = new JdbcBoardDao(con);
    JdbcStudentDao studentDao = new JdbcStudentDao(con);
    JdbcTeacherDao teacherDao = new JdbcTeacherDao(con);

    this.studentHandler = new StudentHandler("학생", studentDao);
    this.teacherHandler = new TeacherHandler("강사", teacherDao);
    this.boardHandler = new BoardHandler("게시판", boardDao);
  }

  void execute(int port) {

    try (Connection con = this.con;
        ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("서버 실행 중...");

      try (Socket socket = serverSocket.accept();
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());
          DataInputStream in = new DataInputStream(socket.getInputStream())) {

        String clientIP = socket.getInetAddress().getHostAddress();
        System.out.printf("접속: %s\n", clientIP);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        hello(printWriter);

        out.writeUTF(stringWriter.toString());

        while (true) {
          String message = in.readUTF();
          if (message.equalsIgnoreCase("quit")) {
            break;
          }

          stringWriter.getBuffer().setLength(0); // StringWriter의 버퍼를 초기화
          printWriter.print(message + "!!!");

          out.writeUTF(stringWriter.toString());
        }

        System.out.printf("끊기: %s\n", clientIP);

      } catch (Exception e) {
        System.out.println("클라이언트 소켓 오류!");
        e.printStackTrace();
      }

    } catch (Exception e) {
      System.out.println("서버 소켓 오류!");
      e.printStackTrace();
    }
  }

  private void hello(PrintWriter out) throws Exception {
    out.println("비트캠프 관리 시스템");
    out.println("  Copyright by 네이버클라우드1기");
    out.println();
    out.println("안녕하세요!");
  }

  private void processRequest(DataInputStream in, ) {

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
            break;
          case 2:
            teacherHandler.service();
            break;
          case 3:
            boardHandler.service();
            break;
          case 9:
            break loop; // loop 라벨이 붙은 while 문을 나간다.
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

  } catch (Exception e) {
    System.out.println("네트워킹 오류!");
    e.printStackTrace();
  }
}
}









