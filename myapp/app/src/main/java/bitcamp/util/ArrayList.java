package bitcamp.util;

import java.util.Arrays;

public class ArrayList implements List {

  private static final int SIZE = 3;
  private int count;
  protected Object[] objects = new Object[SIZE];

  @Override
  public void add(Object object) {
    if (count == objects.length) {
      objects = Arrays.copyOf(objects, objects.length + (objects.length >> 1));
    }
    this.objects[this.count++] = object;
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(objects, count);
  }

  @Override
  public Object get(int index) {
    if (index < 0 || index >= this.count) {
      throw new IndexOutOfBoundsException("인덱스가 무효합니다.");
    }
    return this.objects[index];
  }

  @Override
  public Object set(int index, Object object) {
    Object old  = this.objects[index];
    this.objects[index] = object;
    return old;
  }

  @Override
  public boolean remove(Object object) {
    int index = indexOf(object);
    if (index == -1) {
      return false;
    }

    for (int i = index + 1; i < this.count; i++) {
      this.objects[i - 1] = this.objects[i];
    }
    this.objects[--this.count] = null; // 레퍼런스 카운트를 줄인다.
    return true;
  }

  @Override
  public int size() {
    return this.count;
  }

  @Override
  public int indexOf(Object object) {
    for (int i = 0; i < this.count; i++) {
      if (objects[i].equals(object)) {
        return i;
      }
    }
    return -1;
  }

}






