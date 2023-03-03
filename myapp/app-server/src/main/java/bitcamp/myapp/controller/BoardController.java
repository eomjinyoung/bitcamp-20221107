package bitcamp.myapp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;

@Controller
@RequestMapping("/board")
public class BoardController {

  {
    System.out.println("BoardController 생성됨!");
  }

  // ServletContext 는 요청 핸들러의 파라미터로 주입 받을 수 없다.
  // 객체의 필드로만 주입 받을 수 있다.
  @Autowired private ServletContext servletContext;
  @Autowired private BoardService boardService;

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("insert")
  public void insert(
      Board board,
      List<MultipartFile> files,
      Model model,
      HttpSession session) {
    try {
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
      model.addAttribute("refresh", "list");

    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "data");
    }
  }

  @GetMapping("list")
  public void list(String keyword, Model model) {
    System.out.println("BoardController.list() 호출됨!");
    model.addAttribute("boards", boardService.list(keyword));
  }

  @GetMapping("view")
  public void view(int no, Model model) {
    model.addAttribute("board", boardService.get(no));
  }

  @PostMapping("update")
  public String update(
      Board board,
      List<MultipartFile> files,
      Model model,
      HttpSession session) {
    try {
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
      model.addAttribute("refresh", "list");

    }  catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "data");
    }

    return "board/update";
  }

  @PostMapping("delete")
  public String delete(int no, Model model, HttpSession session) {
    try {
      Member loginUser = (Member) session.getAttribute("loginUser");

      Board old = boardService.get(no);
      if (old.getWriter().getNo() != loginUser.getNo()) {
        return "redirect:../auth/fail";
      }
      boardService.delete(no);

    }  catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "data");
    }
    model.addAttribute("refresh", "list");
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








