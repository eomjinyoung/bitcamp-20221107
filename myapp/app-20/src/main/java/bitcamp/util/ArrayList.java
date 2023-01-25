package bitcamp.util;

import java.util.Arrays;

public class ArrayList {

  private static final int SIZE = 3;
  private int count;
  protected Object[] objects = new Object[SIZE];

  public void append(Object object) {
    if (count == objects.length) {
      objects = Arrays.copyOf(objects, objects.length + (objects.length >> 1));
    }
    this.objects[this.count++] = object;
  }

  public Object[] getList() {
    return Arrays.copyOf(objects, count);
  }

  public Object get(int index) {
    if (index < 0 || index >= this.count) {
      throw new IndexOutOfBoundsException("인덱스가 무효합니다.");
    }
    return this.objects[index];
  }

  public void modify(int index, Object object) {
    this.objects[index] = object;
  }

  public boolean delete(Object object) {
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

  public int size() {
    return this.count;
  }

  public int indexOf(Object object) {
    for (int i = 0; i < this.count; i++) {
      if (objects[i].equals(object)) {
        return i;
      }
    }
    return -1;
  }

}






