package bitcamp.util;

public class ListIterator implements Iterator {

  List list;
  int cursor;

  public ListIterator(List list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    // cursor가 유효한 인덱스를 가리키고 있는지 검사한다.
    return cursor >= 0 && cursor < list.size();
  }

  @Override
  public Object next() {
    // cursor가 가리키는 인덱스의 값을 꺼낸다.
    // => 작업 수행 후 cursor는 다음 인덱스를 가리킨다.
    return list.get(cursor++);
  }
}
