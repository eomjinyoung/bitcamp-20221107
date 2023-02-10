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
        System.out.print(in.readUTF());

        String input = prompt(">", keyboard);
        out.writeUTF(input);
        if (input.equalsIgnoreCase("quit")) {
          break;
        }
      }
      System.out.println("안녕!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String prompt(String title, Scanner keyboard) throws Exception {
    System.out.printf("\n%s", title);
    return keyboard.nextLine();
  }
}









