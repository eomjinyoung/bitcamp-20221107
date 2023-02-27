package bitcamp.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 페이지 컨트롤러를 표시하는 용도
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
  String value() default "";
}
