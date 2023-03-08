package bitcamp.myapp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;

@Controller
@RequestMapping("/board")
public class BoardController {

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("BoardController 생성됨!");
  }

  // ServletContext 는 요청 핸들러의 파라미터로 주입 받을 수 없다.
  // 객체의 필드로만 주입 받을 수 있다.
  @Autowired private ServletContext servletContext;
  @Autowired private BoardService boardService;

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("insert")
  @ResponseBody
  public Object insert(
      Board board,
      List<MultipartFile> files,
      Model model,
      HttpSession session) throws Exception{

    Member loginUser = (Member) session.getAttribute("loginUser");

    Member writer = new Member();
    writer.setNo(loginUser.getNo());
    board.setWriter(writer);

    List<BoardFile> boardFiles = new ArrayList<>();
    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        continue;
      }

      String filename = UUID.randomUUID().toString();
      file.transferTo(new File(servletContext.getRealPath("/board/upload/" + filename)));

      BoardFile boardFile = new BoardFile();
      boardFile.setOriginalFilename(file.getOriginalFilename());
      boardFile.setFilepath(filename);
      boardFile.setMimeType(file.getContentType());
      boardFiles.add(boardFile);
    }
    board.setAttachedFiles(boardFiles);

    boardService.add(board);

    Map<String,Object> result = new HashMap<>();
    result.put("status", "success");

    return result;
  }

  @GetMapping("list")
  @ResponseBody
  public Object list(String keyword, Model model) {
    log.debug("BoardController.list() 호출됨!");
    List<Board> boards = boardService.list(keyword);

    // MappingJackson2HttpMessageConverter 가 jackson 라이브러리를 이용해
    // 자바 객체를 JSON 문자열로 변환하여 클라이언트로 보낸다.
    // 이 컨버터를 사용하면 굳이 UTF-8 변환을 설정할 필요가 없다.
    // 즉 produces = "application/json;charset=UTF-8" 를 설정하지 않아도 된다.
    return boards;
  }

  @GetMapping("view")
  @ResponseBody
  public Object view(int no, Model model) {
    return boardService.get(no);
  }

  @PostMapping("update")
  public String update(
      Board board,
      List<MultipartFile> files,
      Model model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

    Board old = boardService.get(board.getNo());
    if (old.getWriter().getNo() != loginUser.getNo()) {
      return "redirect:../auth/fail";
    }

    List<BoardFile> boardFiles = new ArrayList<>();
    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        continue;
      }

      String filename = UUID.randomUUID().toString();
      file.transferTo(new File(servletContext.getRealPath("/board/upload/" + filename)));

      BoardFile boardFile = new BoardFile();
      boardFile.setOriginalFilename(file.getOriginalFilename());
      boardFile.setFilepath(filename);
      boardFile.setMimeType(file.getContentType());
      boardFile.setBoardNo(board.getNo());
      boardFiles.add(boardFile);
    }
    board.setAttachedFiles(boardFiles);

    boardService.update(board);

    return "board/update";
  }

  @PostMapping("delete")
  public String delete(int no, Model model, HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");

    Board old = boardService.get(no);
    if (old.getWriter().getNo() != loginUser.getNo()) {
      return "redirect:../auth/fail";
    }
    boardService.delete(no);

    return "board/delete";
  }

  @GetMapping("filedelete")
  public String filedelete(int boardNo, int fileNo, HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    Board old = boardService.get(boardNo);
    if (old.getWriter().getNo() != loginUser.getNo()) {
      return "redirect:../auth/fail";
    } else {
      boardService.deleteFile(fileNo);
      return "redirect:view?no=" + boardNo;
    }
  }

}








