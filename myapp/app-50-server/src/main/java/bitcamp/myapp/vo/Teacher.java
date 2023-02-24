package bitcamp.myapp.vo;

public class Teacher extends Member implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private int degree;
  private String school;
  private String major;
  private int wage;


  @Override
  public String toString() {
    return "Teacher [degree=" + degree + ", school=" + school + ", major=" + major + ", wage="
        + wage + ", getNo()=" + getNo() + ", getName()=" + getName() + ", getEmail()=" + getEmail()
        + ", getPassword()=" + getPassword() + ", getTel()=" + getTel() + ", getCreatedDate()="
        + getCreatedDate() + "]";
  }
  public int getDegree() {
    return degree;
  }
  public void setDegree(int degree) {
    this.degree = degree;
  }
  public String getSchool() {
    return school;
  }
  public void setSchool(String school) {
    this.school = school;
  }
  public String getMajor() {
    return major;
  }
  public void setMajor(String major) {
    this.major = major;
  }
  public int getWage() {
    return wage;
  }
  public void setWage(int wage) {
    this.wage = wage;
  }

}
