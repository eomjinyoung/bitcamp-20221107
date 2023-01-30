package bitcamp.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BinaryDecoder {

  public static int readInt(InputStream in) throws Exception {
    int value = 0;
    value  = in.read() << 24;
    value  |= in.read() << 16;
    value  |= in.read() << 8;
    value  |= in.read();
    return value;
  }

  public static String readString(InputStream in) throws Exception {
    // [2byte: 문자열의 바이트 배열 길이][n바이트: 문자열의 바이트 배열]

    // 1) 2바이트를 읽어 문자열의 배열 개수를 알아낸다.
    int length = in.read() << 8;
    length |= in.read();

    // 2) 문자열의 배열을 읽어 들일 빈 배열을 준비한다.
    byte[] bytes = new byte[length];

    // 3) 문자열의 배열을 읽어 빈 배열에 담는다.
    in.read(bytes, 0, length);

    // 4) 배열에 들어 있는 문자 코드를 가지고 String 객체를 생성한다.
    String str = new String(bytes);

    return str;
  }

  public static void main(String[] args) throws Exception {

    // 데이터가 저장된 바이트 배열 준비
    byte[] bytes = new byte[] {0x00, 0x05, (byte)0x41, (byte)0x42, (byte)0xea, (byte)0xb0, (byte)0x80};

    // 바이트 배열에서 데이터를 읽는 도구 준비
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);

    // 바이트 배열을 읽어서 String을 리턴 받는다.
    String str = BinaryDecoder.readString(in);

    // 잘읽었는지 출력해 본다.
    System.out.println(str);

    in.close();
  }
}









