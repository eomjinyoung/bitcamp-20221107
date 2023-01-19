package bitcamp.myapp.dao;

public abstract class ObjectDao {

  private Node head;
  private Node tail;
  private int size;

  public void insert(Object value) {

    // 노드(상자)를 만들어 값을 저장한다.
    Node node = new Node(value);

    // 리스트의 마지막 노드와 연결한다.

    // => 만약 리스트에 노드가 없으면 현재 노드를 시작 노드 겸 마지막 노드로 설정한다.
    if (this.tail == null) { // size == 0, head == null
      this.head = this.tail = node;

    } else {

      // => 마지막 노드의 next 필드에 새 노드 주소를 담는다.
      this.tail.next = node;

      // => 새 노드가 마지막 노드가 되도록 tail 의 주소를 바꾼다.
      this.tail = node;
    }

    // 값을 저장할 때 마다 개수를 카운트 한다.
    this.size++;
  }

  public Object[] findAll() {

    // 리스트의 각 노드에 보관된 값을 담을 배열을 준비한다.
    Object[] values = new Object[this.size];

    // 리스트의 각 노드를 따라 가면서, 노드에서 값을 꺼내 배열에 담는다.
    int index = 0;

    // 시작 노드의 주소를 가져온다.
    Node cursor = this.head;

    while (cursor != null) {

      // 커서가 가리키는 노드의 value 필드의 값을 꺼내 배열에 담는다.
      values[index++] = cursor.value;

      // 커서가 가리키는 노드의 next 필드에는 다음 노드의 주소가 들어 있다.
      // 이것을 커서에 저장한다.
      cursor = cursor.next;
    }

    // 배열에 값을 담았으면 리턴한다.
    return values;
  }

  public void update(Object value) {

    // 변경할 값이 보관된 노드의 인덱스를 알아낸다.
    int index = this.indexOf(value);

    if (index == -1) {
      // 해당 값이 보관된 위치를 찾지 못했으면 예외를 발생시킨다.
      throw new DaoException("변경할 값을 찾을 수 없습니다.");
    }

    // 시작 노드의 주소를 가져온다.
    Node cursor = head;
    int i = 0;

    // 해당 인덱스의 노드를 찾는다.
    while (i < index) {
      // 커서를 다음 노드로 이동한다.
      cursor = cursor.next;

      // index를 증가시킨다.
      i++;
    }

    // 커서가 가리키는 노드의 값을 파라미터로 받은 값으로 바꾼다.
    cursor.value = value;
  }

  public void delete(Object value) {

    // 삭제할 값을 보관하고 있는 노드의 인덱스를 알아낸다.
    int index = this.indexOf(value);

    if (index == -1) {
      // 삭제할 값이 보관된 위치를 찾지 못했으면 예외를 발생시킨다.
      throw new DaoException("삭제할 값을 찾을 수 없습니다.");
    }

    // 이전 노드의 주소를 저장할 레퍼런스를 준비한다.
    Node prevNode = null;

    // 시작 노드를 가져온다.
    Node cursor = head;

    int i = 0;

    // 해당 인덱스의 노드를 찾는다.
    while (i < index) {

      // 커서를 다음 노드를 이동시키기 전에 노드의 주소를 보관한다.
      prevNode = cursor;

      // 커서를 다음 노드로 이동한다.
      cursor = cursor.next;

      // index를 증가시킨다.
      i++;
    }


    if (prevNode == null) {
      // 삭제할 노드가 시작 노드라면,

      // 현재 head가 가리키는 다음 노드를 시작 노드로 설정한다.
      head = head.next;

      // 이전의 시작 노드의 next 필드 값을 지운다.
      cursor.next = null;

      // 리스트의 개수가 0이라면 tail의 주소를 지운다.
      if (head == null) {
        tail = null;
      }

    } else {
      // 이전 노드가 커서의 다음 노드를 가리키도록 한다.
      prevNode.next = cursor.next;

      // 삭제할 노드의 다음 노드 주소를 지운다.
      cursor.next = null;

      // 삭제한 노드가 마지막 노드인 경우
      if (prevNode.next == null) {
        // 마지막 노드의 주소를 바꾼다.
        tail = prevNode;
      }
    }

    // 목록의 개수를 하나 줄인다.
    this.size--;
  }

  // 객체의 위치를 찾는 것은
  // 객체의 타입에 따라 다를 수 있기 때문에
  // 이 클래스에서 정의하지 말고,
  // 서브 클래스에서 정의할 수 있도록
  // 그 구현의 책임을 위임해야 한다.
  protected abstract int indexOf(Object b);

  public int size() {
    return this.size;
  }

  public Object get(int index) {
    if (index < 0 || index >= this.size) {
      throw new DaoException("인덱스가 무효합니다!");
    }

    // 시작 노드의 주소를 가져온다.
    Node cursor = head;
    int i = 0;

    while (i < index) {
      // 커서를 다음 노드로 이동한다.
      cursor = cursor.next;

      // index를 증가시킨다.
      i++;
    }

    // 현재 커서가 가리키는 노드에서 value 필드의 값을 꺼내 리턴한다.
    return cursor.value;
  }
}







