package bitcamp.myapp.vo;

import java.sql.Date;
import java.util.Objects;

public class Board implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private String title;
  private String content;
  private String password;
  private Date createdDate;
  private int viewCount;

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
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }


}
