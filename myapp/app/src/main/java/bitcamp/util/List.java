package bitcamp.util;

// List 인터페이스를 구현하는 시점에서 다룰 데이터의 타입을 지정한다.
// => 예) List<Board>
public interface List<E> extends Iterable<E> {
  void add(E value);
  Object[] toArray();
  E get(int index);
  E set(int index, E value);
  boolean remove(E value);
  int indexOf(E value);
  int size();
}
