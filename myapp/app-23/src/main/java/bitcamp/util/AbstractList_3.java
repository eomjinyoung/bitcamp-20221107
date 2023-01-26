package bitcamp.util;

public abstract class AbstractList_3 implements List {

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

    // - iterator() 메서드 안에서만 사용하는 클래스라면
    //   이 메서드 안에 두는 것이 유지보수에 좋다.
    // - 인스턴스를 한 개만 만들어 사용하고 클래스의 크기도 작다면,
    //   익명 클래스로 만드는 것이 코드를 간결하게 만든다.
    //
    // => anonymous class = 클래스 정의 + 객체 생성 코드
    //
    Iterator obj = new Iterator() {
      int cursor;
      @Override
      public boolean hasNext() {
        return cursor >= 0 && cursor < AbstractList_3.this.size();
      }
      @Override
      public Object next() {
        return AbstractList_3.this.get(cursor++);
      }
    };

    return obj;
  }

}




