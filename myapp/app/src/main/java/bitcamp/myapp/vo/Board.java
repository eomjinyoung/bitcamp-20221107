package bitcamp.myapp.vo;

import java.util.Objects;

// Serializable 인터페이스
// - 객체를 자동으로 직렬화할 수 있도록 설정한다.
// - 따로 메서드를 구현할 필요는 없다.
// - 단지 직렬화를 활성화시키는 표시자 역할을 할 뿐이다.
//
public class Board implements java.io.Serializable {
  // 직렬화 데이터의 버전을 명시한다.
  // - 나중에 데이터를 읽을 때,
  //   이 버전을 보고 읽을 수 있는 데이터인지 아닌지 판단하는 용도로 사용한다.
  // - 누가 판단? ObjectInputStream 클래스!
  //
  private static final long serialVersionUID = 1L;

  private int no;
  private String title;
  private String content;
  private String password;
  private String createdDate;
  private int viewCount;

  // Factory Method 패턴 + Information Expert 패턴
  public static Board create(String csv) {
    try {
      String[] values = csv.split(",");

      Board obj = new Board();
      obj.setNo(Integer.parseInt(values[0]));
      obj.setTitle(values[1]);
      obj.setContent(values[2]);
      obj.setPassword(values[3]);
      obj.setViewCount(Integer.parseInt(values[4]));
      obj.setCreatedDate(values[5]);

      return obj;

    } catch (Exception e) {
      throw new RuntimeException("Board 객체 생성 오류!", e);
    }
  }

  // Information Expert 패턴
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%d,%s",
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getPassword(),
        this.getViewCount(),
        this.getCreatedDate());
  }

  @Override
  public String toString() {
    return "Board [no=" + no + ", title=" + title + ", content=" + content + ", password="
        + password + ", createdDate=" + createdDate + ", viewCount=" + viewCount + "]";
  }
  @Override
  public int hashCode() {
    return Objects.hash(no);
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Board other = (Board) obj;
    return no == other.no;
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }


}
