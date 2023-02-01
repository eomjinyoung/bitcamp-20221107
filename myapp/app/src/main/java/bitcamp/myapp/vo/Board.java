package bitcamp.myapp.vo;

import java.util.Objects;

public class Board implements java.io.Serializable {
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
