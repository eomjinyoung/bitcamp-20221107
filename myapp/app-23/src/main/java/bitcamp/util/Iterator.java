package bitcamp.util;

// 데이터 목록(컬렉션; collection)에서 데이터를 꺼낼 때 호출하는 메서드 규칙
public interface Iterator {
  boolean hasNext(); // 꺼낼 값이 있는지 검사할 때
  Object next(); // 값을 꺼낼 때
}
