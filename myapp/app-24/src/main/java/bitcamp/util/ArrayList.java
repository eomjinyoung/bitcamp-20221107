package bitcamp.util;

import java.util.Arrays;

// 이 클래스의 인스턴스를 생성하는 쪽에서
// 이 클래스가 다룰 데이터의 타입을 지정한다.
//
public class ArrayList<E> extends AbstractList<E> {

  private static final int SIZE = 3;
  protected Object[] objects = new Object[SIZE];

  @Override
  public void add(E object) {
    if (size == objects.length) {
      objects = Arrays.copyOf(objects, objects.length + (objects.length >> 1));
    }
    this.objects[this.size++] = object;
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(objects, size);
  }

  @SuppressWarnings("unchecked")
  @Override
  public E get(int index) {
    super.get(index);
    return (E)this.objects[index];
  }

  @SuppressWarnings("unchecked")
  @Override
  public E set(int index, E object) {
    E old  = (E) this.objects[index];
    this.objects[index] = object;
    return old;
  }

  @Override
  public boolean remove(E object) {
    int index = indexOf(object);
    if (index == -1) {
      return false;
    }

    for (int i = index + 1; i < this.size; i++) {
      this.objects[i - 1] = this.objects[i];
    }
    this.objects[--this.size] = null; // 레퍼런스 카운트를 줄인다.
    return true;
  }

  @Override
  public int indexOf(E object) {
    for (int i = 0; i < this.size; i++) {
      if (objects[i].equals(object)) {
        return i;
      }
    }
    return -1;
  }

}






