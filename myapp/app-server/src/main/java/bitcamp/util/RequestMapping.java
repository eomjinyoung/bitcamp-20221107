package bitcamp.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 애노테이션 유지 정책 변경하기
// => CLASS : 컴파일 했을 때 바이트코드에 유지하기 (기본)
// => RUNTIME : CLASS + 실행 중에 Reflection API 를 사용해서 값 꺼내기
// => SOURCE : 컴파일 했을 때 제거하기. 즉 소스 파일에서만 유지하기.
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
  String value(); // 필수 속성
}
