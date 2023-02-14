package bitcamp.myapp.handler;

import bitcamp.util.StreamTool;

public class HelloHandler {

  public void service(StreamTool streamTool) throws Exception {
    streamTool.println("안녕하세요!!!").send();
  }


}











