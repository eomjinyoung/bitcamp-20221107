package bitcamp.util;

// 이 클래스를 상속 받는 쪽에서
// 이 클래스가 다룰 데이터의 타입을 지정한다.
//
public abstract class AbstractList<E> implements List<E> {

  protected int size;

  @Override
  public E get(int index) {
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
  public Iterator<E> iterator() {

    // => return 문 + anonymous class
    return new Iterator<E>() {
      int cursor;
      @Override
      public boolean hasNext() {
        return cursor >= 0 && cursor < AbstractList.this.size();
      }
      @Override
      public E next() {
        return AbstractList.this.get(cursor++);
      }
    };
  }
}




