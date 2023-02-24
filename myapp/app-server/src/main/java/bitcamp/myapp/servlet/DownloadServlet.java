package bitcamp.myapp.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.BoardFile;

@WebServlet("/download/boardfile")
public class DownloadServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardService boardService;

  @Override
  public void init() {
    boardService = (BoardService) getServletContext().getAttribute("boardService");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      int fileNo = Integer.parseInt(request.getParameter("fileNo"));

      BoardFile boardFile = boardService.getFile(fileNo);
      if (boardFile == null) {
        throw new RuntimeException("파일 정보 없음!");
      }

      File downloadFile = new File(
          this.getServletContext().getRealPath("/board/upload/" + boardFile.getFilepath()));
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
      request.getRequestDispatcher("/downloadfail.jsp").forward(request, response);
      e.printStackTrace();
    }
  }
}






