package bitcamp.util;

public abstract class AbstractList_1 implements List {

  protected int size;

  @Override
  public Object get(int index) {
    if (index < 0 || index >= this.size) {
      throw new IndexOutOfBoundsException("인덱스가 무효합니다.");
    }
    return null;
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public Iterator iterator() {
    return new ListIterator(this);
  }

  // AbstractList 클래스에서만 사용하는 클래스라면
  // 이 클래스 안에 두는 것이 유지보수에 좋다.
  //
  // => 스태틱 중첩 클래스(static nested class)
  //
  static class ListIterator implements Iterator {

    List list;
    int cursor;

    public ListIterator(List list) {
      this.list = list;
    }

    @Override
    public boolean hasNext() {
      return cursor >= 0 && cursor < list.size();
    }

    @Override
    public Object next() {
      return list.get(cursor++);
    }
  }

}




