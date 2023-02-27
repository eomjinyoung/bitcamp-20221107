package bitcamp.myapp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.util.Controller;
import bitcamp.util.RequestMapping;
import bitcamp.util.RequestParam;

@Controller
public class DownloadController {

  private BoardService boardService;

  public DownloadController(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/download/boardfile")
  public String execute(
      @RequestParam("fileNo") int fileNo,
      HttpServletRequest request,
      HttpServletResponse response) {

    try {
      BoardFile boardFile = boardService.getFile(fileNo);
      if (boardFile == null) {
        throw new RuntimeException("파일 정보 없음!");
      }

      File downloadFile = new File(
          request.getServletContext().getRealPath("/board/upload/" + boardFile.getFilepath()));
      if (!downloadFile.exists()) {
        throw new RuntimeException("파일이 존재하지 않음!");
      }

      response.setContentType(boardFile.getMimeType());

      response.setHeader("Content-Disposition",
          String.format("attachment; filename=\"%s\"", boardFile.getOriginalFilename()));

      try (
          BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(downloadFile));
          BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());) {

        int b;
        while ((b = fileIn.read()) != -1) {
          out.write(b);
        }
        out.flush();
      }

    } catch (Exception e) {
      e.printStackTrace();
      return "/downloadfail.jsp";
    }
    return null;
  }
}






