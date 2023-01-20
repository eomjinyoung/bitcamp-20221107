package bitcamp.util;

import java.util.Objects;

public class ArrayListTest {

  static class Member {
    int no;
    String name;
    String tel;

    public Member(int no, String name, String tel) {
      this.no = no;
      this.name = name;
      this.tel = tel;
    }

    @Override
    public String toString() {
      return "Member [no=" + no + ", name=" + name + ", tel=" + tel + "]";
    }

    @Override
    public int hashCode() {
      return Objects.hash(no);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Member other = (Member) obj;
      return no == other.no;
    }


  }

  public static void main(String[] args) {
    ArrayList list = new ArrayList();

    list.add(new Member(1, "aaa", "1111"));
    list.add(new Member(2, "bbb", "2222"));
    list.add(new Member(3, "ccc", "3333"));
    list.add(new Member(4, "ddd", "4444"));
    print(list);

    //    System.out.println(list.remove(new Member(3, null, null)));
    //    print(list);
    //    System.out.println(list.remove(new Member(4, null, null)));
    //    print(list);
    //    System.out.println(list.remove(new Member(1, null, null)));
    //    print(list);
    //    System.out.println(list.remove(new Member(2, null, null)));
    //    print(list);
    //    System.out.println(list.remove(new Member(5, "ddd", "4444")));
    //    print(list);

    list.set(2, new Member(3, "cccx", "3333x"));
    print(list);
    list.set(0, new Member(1, "aaax", "1111x"));
    print(list);
    list.set(3, new Member(4, "dddx", "4444x"));
    print(list);
    list.set(4, new Member(4, "dddx", "4444x"));


  }

  static void print(ArrayList list) {
    System.out.println("------------------------------------------------");
    for (Object obj : list.toArray()) {
      System.out.println(obj);
    }
  }


}





