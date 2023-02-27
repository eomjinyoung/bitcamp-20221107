package bitcamp.myapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;
import bitcamp.util.RequestParam;

@Controller
public class BoardController {

  private BoardService boardService;

  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/form")
  public String form() {
    return "/board/form.jsp";
  }

  @RequestMapping("/board/insert")
  public String insert(
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam("files") Part[] files,
      HttpServletRequest request,
      HttpSession session) {
    try {
      Board board = new Board();
      board.setTitle(title);
      board.setContent(content);

      Member loginUser = (Member) session.getAttribute("loginUser");
      Member writer = new Member();
      writer.setNo(loginUser.getNo());
      board.setWriter(writer);

      List<BoardFile> boardFiles = new ArrayList<>();
      for (Part part : files) {
        if (part.getSize() == 0) {
          continue;
        }

        String filename = UUID.randomUUID().toString();
        part.write(request.getServletContext().getRealPath("/board/upload/" + filename));

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
      request.setAttribute("error", "data");
    }
    return "/board/insert.jsp";
  }

  @RequestMapping("/board/list")
  public String list(
      @RequestParam("keyword") String keyword,
      HttpServletRequest request) {

    request.setAttribute("boards", boardService.list(keyword));
    return "/board/list.jsp";
  }

  @RequestMapping("/board/view")
  public String view(
      @RequestParam("no") int no,
      HttpServletRequest request) {

    request.setAttribute("board", boardService.get(no));
    return"/board/view.jsp";
  }

  @RequestMapping("/board/update")
  public String update(
      @RequestParam("no") int no,
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam("files") Part[] files,
      HttpServletRequest request,
      HttpSession session) {
    try {
      Member loginUser = (Member) session.getAttribute("loginUser");

      Board board = new Board();
      board.setNo(no);
      board.setTitle(title);
      board.setContent(content);

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
        part.write(request.getServletContext().getRealPath("/board/upload/" + filename));

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
      request.setAttribute("error", "data");
    }

    return "/board/update.jsp";
  }

  @RequestMapping("/board/delete")
  public String delete(
      @RequestParam("no") int boardNo,
      HttpServletRequest request,
      HttpSession session) {
    try {
      Member loginUser = (Member) session.getAttribute("loginUser");

      Board old = boardService.get(boardNo);
      if (old.getWriter().getNo() != loginUser.getNo()) {
        return "redirect:../auth/fail";
      }
      boardService.delete(boardNo);

    }  catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "data");
    }
    return "/board/delete.jsp";
  }

  @RequestMapping("/board/filedelete")
  public String filedelete(
      @RequestParam("boardNo") int boardNo,
      @RequestParam("fileNo") int fileNo,
      HttpSession session) {

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








