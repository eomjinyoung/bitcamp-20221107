package bitcamp.myapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;

@Controller
@RequestMapping("/board")
public class BoardController {

  // ServletContext 는 요청 핸들러의 파라미터로 주입 받을 수 없다.
  // 객체의 필드로만 주입 받을 수 있다.
  @Autowired private ServletContext servletContext;
  @Autowired private BoardService boardService;

  @GetMapping("form")
  public String form() {
    return "/board/form.jsp";
  }

  @PostMapping("insert")
  public String insert(
      Board board,
      //      String title,
      //      String content,
      Part[] files,
      Model model, // ServletRequest 보관소에 저장할 값을 담는 임시 저장소
      // 이 객체에 값을 담아 두면 프론트 컨트롤러(DispatcherServlet)가
      // ServletRequest 보관소로 옮겨 담을 것이다.
      HttpSession session) {
    try {
      Member loginUser = (Member) session.getAttribute("loginUser");

      //      Board board = new Board();
      //      board.setTitle(title);
      //      board.setContent(content);

      Member writer = new Member();
      writer.setNo(loginUser.getNo());
      board.setWriter(writer);

      List<BoardFile> boardFiles = new ArrayList<>();
      for (Part part : files) {
        if (part.getSize() == 0) {
          continue;
        }

        String filename = UUID.randomUUID().toString();
        part.write(servletContext.getRealPath("/board/upload/" + filename));

        BoardFile boardFile = new BoardFile();
        boardFile.setOriginalFilename(part.getSubmittedFileName());
        boardFile.setFilepath(filename);
        boardFile.setMimeType(part.getContentType());
        boardFiles.add(boardFile);
      }
      board.setAttachedFiles(boardFiles);

      boardService.add(board);

    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "data");
    }
    return "/board/insert.jsp";
  }

  @GetMapping("list")
  public String list(String keyword, Model model) {
    model.addAttribute("boards", boardService.list(keyword));
    return "/board/list.jsp";
  }

  @GetMapping("view")
  public String view(int no, Model model) {
    model.addAttribute("board", boardService.get(no));
    return"/board/view.jsp";
  }

  @PostMapping("update")
  public String update(
      Board board,
      //      int no,
      //      String title,
      //      String content,
      Part[] files,
      Model model,
      HttpSession session) {
    try {
      Member loginUser = (Member) session.getAttribute("loginUser");

      //      Board board = new Board();
      //      board.setNo(no);
      //      board.setTitle(title);
      //      board.setContent(content);

      Board old = boardService.get(board.getNo());
      if (old.getWriter().getNo() != loginUser.getNo()) {
        return "redirect:../auth/fail";
      }

      List<BoardFile> boardFiles = new ArrayList<>();
      for (Part part : files) {
        if (part.getSize() == 0) {
          continue;
        }

        String filename = UUID.randomUUID().toString();
        part.write(servletContext.getRealPath("/board/upload/" + filename));

        BoardFile boardFile = new BoardFile();
        boardFile.setOriginalFilename(part.getSubmittedFileName());
        boardFile.setFilepath(filename);
        boardFile.setMimeType(part.getContentType());
        boardFile.setBoardNo(board.getNo());
        boardFiles.add(boardFile);
      }
      board.setAttachedFiles(boardFiles);

      boardService.update(board);

    }  catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("error", "data");
    }

    return "/board/update.jsp";
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
    return "/board/delete.jsp";
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








