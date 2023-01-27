package bitcamp.util;

// 사용하는 시점에서 타입을 고정하기
// => Iterator를 사용하는 시점에 어떤 타입의 데이터를 다룰 것인지 지정한다.
//    예) Iterator<Board>
public interface Iterator<T> {
  boolean hasNext(); // 꺼낼 값이 있는지 검사할 때
  T next(); // 값을 꺼낼 때
}
