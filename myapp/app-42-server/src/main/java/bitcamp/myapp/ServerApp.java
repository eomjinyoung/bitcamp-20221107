package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.impl.BoardDaoImpl;
import bitcamp.myapp.dao.impl.MemberDaoImpl;
import bitcamp.myapp.dao.impl.StudentDaoImpl;
import bitcamp.myapp.dao.impl.TeacherDaoImpl;
import bitcamp.myapp.handler.BoardHandler;
import bitcamp.myapp.handler.HelloHandler;
import bitcamp.myapp.handler.StudentHandler;
import bitcamp.myapp.handler.TeacherHandler;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.StreamTool;
import bitcamp.util.TransactionManager;

public class ServerApp {

  StudentHandler studentHandler;
  TeacherHandler teacherHandler;
  BoardHandler boardHandler;
  HelloHandler helloHandler = new HelloHandler();

  public static void main(String[] args) {
    try {
      new ServerApp().execute(8888);
    } catch (Exception e) {
      System.out.println("서버 실행 오류!");
      e.printStackTrace();
    }
  }

  public ServerApp() throws Exception{

    // Mybatis API 사용 준비
    // 1) Mybatis 설정 파일 준비
    //    => resources/bitcamp/myapp/config/mybatis-config.xml

    // 2) SQL Mapper 파일 준비
    //    => resources/bitcamp/myapp/mapper/BoardMapper.xml

    // 3) Mybatis 설정 파일을 읽을 때 사용할 입력 스트림 객체 준비
    InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
        "bitcamp/myapp/config/mybatis-config.xml");

    // 4) SqlSessionFactoryBuilder 객체 준비
    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

    // 5) builder를 이용하여 SqlSessionFactory 객체 생성
    // 6) 오리지널 SqlSessionFactory에 트랜잭션 보조 기능이 덧붙여진 프록시 객체를 준비한다.
    BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
        builder.build(mybatisConfigInputStream));

    // 7) BitcampSqlSessionFactory객체를 이용하여 트랜잭션을 다루는 객체를 준비한다.
    TransactionManager txManager = new TransactionManager(sqlSessionFactory);

    BoardDaoImpl boardDao = new BoardDaoImpl(sqlSessionFactory);
    MemberDaoImpl memberDao = new MemberDaoImpl(sqlSessionFactory);
    StudentDaoImpl studentDao = new StudentDaoImpl(sqlSessionFactory);
    TeacherDaoImpl teacherDao = new TeacherDaoImpl(sqlSessionFactory);

    this.studentHandler = new StudentHandler("학생", txManager, memberDao, studentDao);
    this.teacherHandler = new TeacherHandler("강사", txManager, memberDao, teacherDao);
    this.boardHandler = new BoardHandler("게시판", boardDao);
  }

  void execute(int port) {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("서버 실행 중...");

      while (true) {
        Socket socket = serverSocket.accept();
        new Thread(() -> service(socket)).start();
      }

    } catch (Exception e) {
      System.out.println("서버 소켓 오류!");
      e.printStackTrace();
    }
  }

  private void hello(StreamTool streamTool) throws Exception {
    streamTool.println("비트캠프 관리 시스템")
    .println("  Copyright by 네이버클라우드1기")
    .println("--------------------------------------")
    .println("안녕하세요!")
    .println()
    .send();
  }

  private void processRequest(StreamTool streamTool) throws Exception {

    loop: while (true) {
      String command = streamTool.readString();

      if (command.equals("menu")) {
        menu(streamTool);
        continue;
      }

      int menuNo;
      try {
        menuNo = Integer.parseInt(command);
      } catch (Exception e) {
        streamTool.println("메뉴 번호가 옳지 않습니다!").println().send();
        continue;
      }

      try {
        switch (menuNo) {
          case 1:
            studentHandler.service(streamTool);
            break;
          case 2:
            teacherHandler.service(streamTool);
            break;
          case 3:
            boardHandler.service(streamTool);
            break;
          case 4:
            helloHandler.service(streamTool);
            break;
          case 9:
            break loop; // loop 라벨이 붙은 while 문을 나간다.
          default:
            streamTool.println("잘못된 메뉴 번호 입니다.").send();
        }



      } catch (Exception e) {
        streamTool.printf("명령 실행 중 오류 발생! - %s : %s\n",
            e.getMessage(),
            e.getClass().getSimpleName()).send();
      }
    }

    // while 종료하면 클라이언트와 연결을 끊는다.
    streamTool.print("quit").send();

  }

  void menu(StreamTool streamTool) throws Exception {
    streamTool.println("1. 학생관리")
    .println("2. 강사관리")
    .println("3. 게시판")
    .println("4. 인사")
    .println("9. 종료")
    .println("메뉴 번호:")
    .send();
  }

  public void service(Socket clientSocket) {
    // 스레드가 실행할 코드를 둔다.
    try (Socket socket = clientSocket;
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream())) {

      // 입출력 보조 도구 준비
      StreamTool streamTool = new StreamTool(in, out);

      String clientIP = socket.getInetAddress().getHostAddress();
      System.out.printf("접속: %s\n", clientIP);

      hello(streamTool);
      processRequest(streamTool);

      System.out.printf("끊기: %s\n", clientIP);

    } catch (Exception e) {
      System.out.println("클라이언트 요청 처리 오류!");
      e.printStackTrace();
    }
  }
}









