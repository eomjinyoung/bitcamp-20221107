package bitcamp.util;

// 객체 목록을 다루는 기능을 규정한다.
//
public interface List {
  void add(Object value);
  Object[] toArray();
  Object get(int index);
  Object set(int index, Object value);
  boolean remove(Object value);
  int indexOf(Object value);
  int size();
}
