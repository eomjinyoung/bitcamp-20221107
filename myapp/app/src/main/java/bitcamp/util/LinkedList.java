package bitcamp.util;

// LinkedList 를 생성할 때 어떤 데이터를 다룰지 타입을 지정한다.
// 예) new LinkedList<Student>();
//
public class LinkedList<E> extends AbstractList<E> {

  private Node<E> head;
  private Node<E> tail;

  @Override
  public void add(E value) {
    Node<E> node = new Node<>(value);
    if (this.tail == null) { // size == 0, head == null
      this.head = this.tail = node;

    } else {
      this.tail.next = node;
      this.tail = node;
    }

    this.size++;
  }

  @Override
  public Object[] toArray() {
    Object[] values = new Object[this.size];
    int index = 0;
    Node<E> cursor = this.head;

    while (cursor != null) {
      values[index++] = cursor.value;
      cursor = cursor.next;
    }
    return values;
  }

  @Override
  public E set(int index, E value) {
    if (index < 0 || index >= this.size) {
      throw new IndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }

    Node<E> cursor = head;
    int i = 0;

    while (cursor != null) {
      if (i == index) {
        E old = cursor.value;
        cursor.value = value;
        return old;
      }
      cursor = cursor.next;
      i++;
    }

    return null;
  }

  @Override
  public boolean remove(E value) {
    Node<E> prevNode = null;
    Node<E> deletedNode = null;
    Node<E> cursor = this.head;

    while (cursor != null) {
      if (cursor.value.equals(value)) {
        deletedNode = cursor;
        break;
      }
      prevNode = cursor;
      cursor = cursor.next;
    }

    if (deletedNode == null) {
      return false;
    }

    if (prevNode == null) {
      this.head = this.head.next;
      deletedNode.next = null;
      if (this.head == null) {
        this.tail = null;
      }

    } else {
      prevNode.next = deletedNode.next;
      deletedNode.next = null;
      if (prevNode.next == null) {
        this.tail = prevNode;
      }
    }
    this.size--;
    return true;
  }

  @Override
  public int indexOf(E b) {
    Node<E> cursor = head;
    int i = 0;

    while (cursor != null) {
      if (cursor.value.equals(b)) {
        return i;
      }
      cursor = cursor.next;
      i++;
    }
    return -1;
  }

  @Override
  public E get(int index) {
    super.get(index);

    Node<E> cursor = head;
    int i = 0;

    while (i < index) {
      cursor = cursor.next;
      i++;
    }
    return cursor.value;
  }

  // Node 객체에 담을 데이터의 타입이 무엇인지 지정한다.
  // 예) new Node<Student>();
  //
  static class Node<T> {
    T value;
    Node<T> next;

    public Node() {}

    public Node(T value) {
      this.value = value;
    }
  }
}







