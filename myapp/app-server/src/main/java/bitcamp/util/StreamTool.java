package bitcamp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;

public class StreamTool {
  DataInputStream in;
  DataOutputStream out;
  StringWriter stringWriter = new StringWriter();
  PrintWriter printWriter = new PrintWriter(stringWriter);

  public StreamTool(DataInputStream in, DataOutputStream out) {
    this.in = in;
    this.out = out;
  }

  public String readString() throws Exception {
    return in.readUTF();
  }

  public int readInt() throws Exception {
    return Integer.parseInt(readString());
  }

  public Date readDate() throws Exception {
    return Date.valueOf(readString());
  }

  public void send() throws Exception {
    out.writeUTF(stringWriter.toString());
    stringWriter.getBuffer().setLength(0);
  }

  public StreamTool print(String s) {
    printWriter.print(s);
    return this;
  }

  public StreamTool println() {
    printWriter.println();
    return this;
  }

  public StreamTool println(String x) {
    printWriter.println(x);
    return this;
  }

  public StreamTool printf(String format, Object... args) {
    printWriter.printf(format, args);
    return this;
  }

  public String promptString(String title) throws Exception {
    this.print(title).send();
    return this.readString();
  }

  public int promptInt(String title) throws Exception {
    this.print(title).send();
    return this.readInt();
  }

}









