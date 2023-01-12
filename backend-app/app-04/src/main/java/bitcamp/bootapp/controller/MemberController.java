package bitcamp.bootapp.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import bitcamp.bootapp.dao.MemberDao;
import bitcamp.bootapp.vo.Member;

@RestController
public class MemberController {

  MemberDao memberDao;

  // Spring IoC 컨테이너가 이 클래스의 인스턴스를 만들기 위해
  // 생성자를 호출할 때,
  // 파라미터로 선언된 MemberDao 객체를 주입할 것이다.
  public MemberController(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @PostMapping("/members")
  public Object addMember(
      //@RequestParam(required = false)
      String name, // ..&name=xxx&..
      String tel, // ..&tel=xxx&..
      String postNo, // ..&postNo=xxx&..
      String basicAddress, // ..&basicAddress=xxx&..
      String detailAddress, // ..&detailAddress=xxx&..
      boolean working, // ..&working=xxx&..  => "true"=true/"false"=false, 파라미터 없으면 false,
      // "on"=true/"off"=false, "1"=true/"0"=false, 그 밖에 문자열은 변환 오류 발생!
      char gender, // ..&gender=M&..  => 문자 1개의 문자열 변환, null 또는 그 밖에 문자열은 변환 오류 발생!
      byte level // ..&level=1&..  => Byte.parseByte("1") => 1, null 또는 byte 범위를 초과하는 숫자는 변환 오류 발생!
      ) {

    Member m = new Member();
    m.setName(name);
    m.setTel(tel);
    m.setPostNo(postNo);
    m.setBasicAddress(basicAddress);
    m.setDetailAddress(detailAddress);
    m.setWorking(working);
    m.setGender(gender);
    m.setLevel(level);
    m.setCreatedDate(new Date(System.currentTimeMillis()).toString());

    this.memberDao.insert(m);

    Map<String,Object> contentMap = new HashMap<>();
    contentMap.put("status", "success");

    return contentMap;
  }


  @GetMapping("/members")
  public Object getMembers() {

    Member[] members = this.memberDao.findAll();

    Map<String,Object> contentMap = new HashMap<>();
    contentMap.put("status", "success");
    contentMap.put("data", members);

    return contentMap;
  }


  @GetMapping("/members/{memberNo}")
  public Object getMember(@PathVariable int memberNo) {

    Member b = this.memberDao.findByNo(memberNo);

    Map<String,Object> contentMap = new HashMap<>();

    if (b == null) {
      contentMap.put("status", "failure");
      contentMap.put("data", "회원이 없습니다.");
    } else {
      contentMap.put("status", "success");
      contentMap.put("data", b);
    }

    return contentMap;
  }

  @PutMapping("/members/{no}")
  public Object updateMember(
      //@PathVariable int memberNo, // Member 인스턴스로 직접 받을 수 있다.
      Member member) {

    Map<String,Object> contentMap = new HashMap<>();

    Member old = this.memberDao.findByNo(member.getNo());
    if (old == null) {
      contentMap.put("status", "failure");
      contentMap.put("data", "회원이 없습니다.");
      return contentMap;
    }

    member.setCreatedDate(old.getCreatedDate());

    this.memberDao.update(member);

    contentMap.put("status", "success");

    return contentMap;
  }

  @DeleteMapping("/members/{memberNo}")
  public Object deleteMember(
      // 낱개로 받을 때는 @PathVariable 애노테이션을 생략하면 안된다.
      @PathVariable int memberNo) {

    Member m = this.memberDao.findByNo(memberNo);

    Map<String,Object> contentMap = new HashMap<>();

    if (m == null) {
      contentMap.put("status", "failure");
      contentMap.put("data", "회원이 없습니다.");

    } else {
      this.memberDao.delete(m);
      contentMap.put("status", "success");
    }

    return contentMap;
  }
}
