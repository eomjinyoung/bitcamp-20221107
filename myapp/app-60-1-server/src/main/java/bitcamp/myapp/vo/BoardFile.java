package bitcamp.myapp.vo;

import java.io.Serializable;
import java.util.Objects;

public class BoardFile implements Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private String filepath;
  private String originalFilename;
  private String mimeType;
  private int boardNo;

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getFilepath() {
    return filepath;
  }
  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }
  public String getOriginalFilename() {
    return originalFilename;
  }
  public void setOriginalFilename(String originalFilename) {
    this.originalFilename = originalFilename;
  }
  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public String getMimeType() {
    return mimeType;
  }
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
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
    BoardFile other = (BoardFile) obj;
    return no == other.no;
  }
  @Override
  public String toString() {
    return "BoardFile [no=" + no + ", filepath=" + filepath + ", originalFilename="
        + originalFilename + ", mimeType=" + mimeType + ", boardNo=" + boardNo + "]";
  }




}
