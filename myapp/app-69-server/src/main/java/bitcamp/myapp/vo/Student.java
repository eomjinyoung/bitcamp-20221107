package bitcamp.myapp.vo;

import lombok.Data;

@Data
public class Student extends Member{
  private String postNo;
  private String basicAddress;
  private String detailAddress;
  private boolean working;
  private char gender;
  private byte level;
}
