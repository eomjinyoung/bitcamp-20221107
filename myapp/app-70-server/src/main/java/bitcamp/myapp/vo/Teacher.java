package bitcamp.myapp.vo;

import lombok.Data;

@Data
public class Teacher extends Member{
  private int degree;
  private String school;
  private String major;
  private int wage;
}
