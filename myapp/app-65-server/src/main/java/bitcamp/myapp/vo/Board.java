package bitcamp.myapp.vo;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class Board implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private String title;
  private String content;
  private String password;

  // Jackson 라이브러리가 Date 타입 값을 JSON 문자열로 변환할 때 사용할 규칙을 설정한다.
  @JsonFormat(
      shape = Shape.STRING,
      pattern = "yyyy-MM-dd")
  private Date createdDate;

  private int viewCount;
  private int writerNo;
  private String writerName;
  private Member writer;
  private List<BoardFile> attachedFiles;

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
  public int getWriterNo() {
    return writerNo;
  }
  public void setWriterNo(int writerNo) {
    this.writerNo = writerNo;
  }
  public String getWriterName() {
    return writerName;
  }
  public void setWriterName(String writerName) {
    this.writerName = writerName;
  }
  public Member getWriter() {
    return writer;
  }
  public void setWriter(Member writer) {
    this.writer = writer;
  }
  public List<BoardFile> getAttachedFiles() {
    return attachedFiles;
  }
  public void setAttachedFiles(List<BoardFile> attachedFiles) {
    this.attachedFiles = attachedFiles;
  }
  @Override
  public String toString() {
    return "Board [no=" + no + ", title=" + title + ", content=" + content + ", password="
        + password + ", createdDate=" + createdDate + ", viewCount=" + viewCount + ", writerNo="
        + writerNo + ", writerName=" + writerName + ", writer=" + writer + ", attachedFiles="
        + attachedFiles + "]";
  }

}
