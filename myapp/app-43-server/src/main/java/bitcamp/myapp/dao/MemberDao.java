package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.Member;

public interface MemberDao {

  void insert(Member m);

  List<Member> findAll();

  Member findByNo(int no);

  int update(Member m);

  int delete(int no);

}







