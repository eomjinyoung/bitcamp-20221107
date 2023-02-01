package bitcamp.myapp.vo;

public class Teacher extends Member implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  private String email;
  private int degree;
  private String school;
  private String major;
  private int wage;

  // Factory Method 패턴 + Information Expert 패턴
  public static Teacher create(String csv) {
    try {
      String[] values = csv.split(",");

      Teacher obj = new Teacher();
      obj.setNo(Integer.parseInt(values[0]));
      obj.setName(values[1]);
      obj.setTel(values[2]);
      obj.setCreatedDate(values[3]);
      obj.setEmail(values[4]);
      obj.setDegree(Integer.parseInt(values[5]));
      obj.setSchool(values[6]);
      obj.setMajor(values[7]);
      obj.setWage(Integer.parseInt(values[8]));

      return obj;

    } catch (Exception e) {
      throw new RuntimeException("Board 객체 생성 오류!", e);
    }
  }

  // Information Expert 패턴
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%d,%s,%s,%d",
        this.getNo(),
        this.getName(),
        this.getTel(),
        this.getCreatedDate(),
        this.getEmail(),
        this.getDegree(),
        this.getSchool(),
        this.getMajor(),
        this.getWage());
  }

  @Override
  public String toString() {
    return "Teacher [email=" + email + ", degree=" + degree + ", school=" + school + ", major="
        + major + ", wage=" + wage + "]";
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
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
