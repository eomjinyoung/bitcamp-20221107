package bitcamp.myapp.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Student extends Member{
  private String postNo;
  private String basicAddress;
  private String detailAddress;
  private boolean working;
  private char gender;
  private byte level;
}
