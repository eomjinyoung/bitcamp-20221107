package bitcamp.myapp;

import java.util.Arrays;

public class MemberDao {
  private static final int SIZE = 100;

  private int count;
  private Member[] members = new Member[SIZE];

  public void insert(Member member) {
    this.members[this.count++] = member;
  }

  public Member[] findAll() {
    return Arrays.copyOf(members, count);
  }

  public Member findByNo(int no) {
    for (int i = 0; i < this.count; i++) {
      if (this.members[i].getNo() == no) {
        return this.members[i];
      }
    }
    return null;
  }

  public void update(Member member) {
    this.members[this.indexOf(member)] = member;
  }

  public void delete(Member member) {
    for (int i = this.indexOf(member) + 1; i < this.count; i++) {
      this.members[i - 1] = this.members[i];
    }
    this.members[--this.count] = null; // 레퍼런스 카운트를 줄인다.
  }

  private int indexOf(Member b) {
    for (int i = 0; i < this.count; i++) {
      if (this.members[i].getNo() == b.getNo()) {
        return i;
      }
    }
    return -1;
  }
}







