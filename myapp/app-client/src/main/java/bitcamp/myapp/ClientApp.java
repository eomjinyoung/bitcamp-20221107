package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

  public static void main(String[] args) {
    new ClientApp().execute("localhost", 8888);
  }

  void execute(String ip, int port) {
    try (Scanner keyboard = new Scanner(System.in);
        Socket socket = new Socket(ip, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      while (true) {
        read(in);
        String input = prompt(">", keyboard);
        send(out, input);
        if (input.equalsIgnoreCase("quit")) {
          break;
        }
      }
      System.out.println("안녕!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void read(DataInputStream in) throws Exception {
    while (true) {
      String str = in.readUTF();
      if (str.equals("[[END]]"))
        break;
      System.out.print(str);
    }
  }

  void send(DataOutputStream out, String message) throws Exception {
    out.writeUTF(message);
  }

  String prompt(String title, Scanner keyboard) throws Exception {
    System.out.printf("\n%s", title);
    return keyboard.nextLine();
  }
}









