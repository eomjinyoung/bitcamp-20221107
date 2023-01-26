package bitcamp.util;

import java.util.Arrays;

public class ArrayList extends AbstractList {

  private static final int SIZE = 3;
  protected Object[] objects = new Object[SIZE];

  @Override
  public void add(Object object) {
    if (size == objects.length) {
      objects = Arrays.copyOf(objects, objects.length + (objects.length >> 1));
    }
    this.objects[this.size++] = object;
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(objects, size);
  }

  @Override
  public Object get(int index) {
    super.get(index);
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

    for (int i = index + 1; i < this.size; i++) {
      this.objects[i - 1] = this.objects[i];
    }
    this.objects[--this.size] = null; // 레퍼런스 카운트를 줄인다.
    return true;
  }

  @Override
  public int indexOf(Object object) {
    for (int i = 0; i < this.size; i++) {
      if (objects[i].equals(object)) {
        return i;
      }
    }
    return -1;
  }

}






