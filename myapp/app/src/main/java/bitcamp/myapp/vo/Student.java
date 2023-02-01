package bitcamp.myapp.vo;

// 회원 데이터를 담을 메모리를 설계한다.
public class Student extends Member implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private String postNo;
  private String basicAddress;
  private String detailAddress;
  private boolean working;
  private char gender;
  private byte level;

  // Factory Method 패턴 + Information Expert 패턴
  public static Student create(String csv) {
    try {
      String[] values = csv.split(",");

      Student obj = new Student();
      obj.setNo(Integer.parseInt(values[0]));
      obj.setName(values[1]);
      obj.setTel(values[2]);
      obj.setCreatedDate(values[3]);
      obj.setPostNo(values[4]);
      obj.setBasicAddress(values[5]);
      obj.setDetailAddress(values[6]);
      obj.setWorking(Boolean.parseBoolean(values[7]));
      obj.setGender(values[8].charAt(0));
      obj.setLevel(Byte.parseByte(values[9]));

      return obj;

    } catch (Exception e) {
      throw new RuntimeException("Board 객체 생성 오류!", e);
    }
  }

  // Information Expert 패턴
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s,%s,%b,%s,%d",
        this.getNo(),
        this.getName(),
        this.getTel(),
        this.getCreatedDate(),
        this.getPostNo(),
        this.getBasicAddress(),
        this.getDetailAddress(),
        this.isWorking(),
        this.getGender(),
        this.getLevel());
  }

  @Override
  public String toString() {
    return "Student [postNo=" + postNo + ", basicAddress=" + basicAddress + ", detailAddress="
        + detailAddress + ", working=" + working + ", gender=" + gender + ", level=" + level + "]";
  }

  public String getPostNo() {
    return postNo;
  }
  public void setPostNo(String postNo) {
    this.postNo = postNo;
  }
  public String getBasicAddress() {
    return basicAddress;
  }
  public void setBasicAddress(String basicAddress) {
    this.basicAddress = basicAddress;
  }
  public String getDetailAddress() {
    return detailAddress;
  }
  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
  }
  public boolean isWorking() {
    return working;
  }
  public void setWorking(boolean working) {
    this.working = working;
  }
  public char getGender() {
    return gender;
  }
  public void setGender(char gender) {
    this.gender = gender;
  }
  public byte getLevel() {
    return level;
  }
  public void setLevel(byte level) {
    this.level = level;
  }
}
