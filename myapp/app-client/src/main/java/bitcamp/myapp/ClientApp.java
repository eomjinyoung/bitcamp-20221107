package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientApp {

  public static void main(String[] args) {
    new ClientApp().execute("localhost", 8888);
  }

  void execute(String ip, int port) {
    try (Socket socket = new Socket(ip, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      while (true) {
        String str = in.readUTF();
        if (str.equals("[[END]]"))
          break;
        System.out.print(str);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}









