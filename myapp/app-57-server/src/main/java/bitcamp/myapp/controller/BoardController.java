package bitcamp.myapp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;

@Controller
public class BoardController {

  private BoardService boardService;

  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/form")
  public String form(HttpServletRequest request, HttpServletResponse response) {
    return "/board/form.jsp";
  }

  @RequestMapping("/board/insert")
  public String insert(HttpServletRequest request, HttpServletResponse response) {
    try {
      DiskFileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload upload = new ServletFileUpload(factory);
      List<FileItem> items = upload.parseRequest(request);
      Map<String,String> paramMap = new HashMap<>();
      List<FileItem> files = new ArrayList<>();

      for (FileItem item : items) {
        if (item.isFormField()) {
          paramMap.put(item.getFieldName(), item.getString("UTF-8"));
        } else {
          files.add(item);
        }
      }

      Board board = new Board();
      board.setTitle(paramMap.get("title"));
      board.setContent(paramMap.get("content"));

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      Member writer = new Member();
      writer.setNo(loginUser.getNo());
      board.setWriter(writer);

      List<BoardFile> boardFiles = new ArrayList<>();
      for (FileItem file : files) {
        if (file.getSize() == 0) {
          continue;
        }
        String filename = UUID.randomUUID().toString();
        file.write(new File(request.getServletContext().getRealPath("/board/upload/" + filename)));

        BoardFile boardFile = new BoardFile();
        boardFile.setOriginalFilename(file.getName());
        boardFile.setFilepath(filename);
        boardFile.setMimeType(file.getContentType());
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
  public String list(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("boards",
        boardService.list(request.getParameter("keyword")));
    return "/board/list.jsp";
  }

  @RequestMapping("/board/view")
  public String view(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("board",
        boardService.get(Integer.parseInt(request.getParameter("no"))));
    return"/board/view.jsp";
  }

  @RequestMapping("/board/update")
  public String update(HttpServletRequest request, HttpServletResponse response) {
    try {
      Member loginUser = (Member) request.getSession().getAttribute("loginUser");

      Board board = new Board();
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));

      Board old = boardService.get(board.getNo());
      if (old.getWriter().getNo() != loginUser.getNo()) {
        return "redirect:../auth/fail";
      }

      Collection<Part> parts = request.getParts();
      List<BoardFile> boardFiles = new ArrayList<>();
      for (Part part : parts) {
        if (!part.getName().equals("files") || part.getSize() == 0) {
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
  public String delete(HttpServletRequest request, HttpServletResponse response) {
    try {
      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      int boardNo = Integer.parseInt(request.getParameter("no"));

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
  public String filedelete(HttpServletRequest request, HttpServletResponse response) {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    int boardNo = Integer.parseInt(request.getParameter("boardNo"));

    Board old = boardService.get(boardNo);
    if (old.getWriter().getNo() != loginUser.getNo()) {
      return "redirect:../auth/fail";
    } else {
      boardService.deleteFile(Integer.parseInt(request.getParameter("fileNo")));
      return "redirect:view?no=" + boardNo;
    }
  }

}








