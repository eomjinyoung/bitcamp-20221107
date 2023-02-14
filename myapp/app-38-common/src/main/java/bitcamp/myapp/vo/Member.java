package bitcamp.myapp.vo;

import java.sql.Date;
import java.util.Objects;

public class Member implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private String name;
  private String email;
  private String password;
  private String tel;
  private Date createdDate;



  @Override
  public String toString() {
    return "Member [no=" + no + ", name=" + name + ", email=" + email + ", password=" + password
        + ", tel=" + tel + ", createdDate=" + createdDate + "]";
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
    Member other = (Member) obj;
    return no == other.no;
  }

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getTel() {
    return tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }




}
