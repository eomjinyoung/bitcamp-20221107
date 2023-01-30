package bitcamp.util;

public class BinaryEncoder {

  public static byte[] write(int value) {
    byte[] bytes = new byte[4];
    bytes[0] = (byte) (value >> 24);
    bytes[1] = (byte) (value >> 16);
    bytes[2] = (byte) (value >> 8);
    bytes[3] = (byte) value;
    return bytes;
  }

  public static byte[] write(String value) {
    // [2byte: 문자열의 바이트 배열 길이][n바이트: 문자열의 바이트 배열]
    byte[] strBytes = value.getBytes();
    byte[] bytes = new byte[strBytes.length + 2];

    System.arraycopy(strBytes, 0, bytes, 2, strBytes.length);
    bytes[0] = (byte) (strBytes.length >> 8);
    bytes[1] = (byte) (strBytes.length);

    return bytes;
  }

  public static void main(String[] args) {
    //    int value = 0xabcdef31;
    //    byte[] bytes = ByteArrayGenerator.write(value);
    byte[] bytes = BinaryEncoder.write("ABC가각간");
    for (int i = 0; i < bytes.length; i++) {
      System.out.printf("%02x", bytes[i]);
    }
  }
}
