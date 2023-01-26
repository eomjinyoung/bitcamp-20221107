package bitcamp.util;

public abstract class AbstractList implements List {

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

    // => return 문 + anonymous class
    return new Iterator() {
      int cursor;
      @Override
      public boolean hasNext() {
        return cursor >= 0 && cursor < AbstractList.this.size();
      }
      @Override
      public Object next() {
        return AbstractList.this.get(cursor++);
      }
    };
  }
}




