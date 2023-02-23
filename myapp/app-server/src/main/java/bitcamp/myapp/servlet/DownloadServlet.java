package bitcamp.myapp.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.BoardFile;

@WebServlet("/download/boardfile")
public class DownloadServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardFileDao boardFileDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardFileDao = (BoardFileDao) ctx.getAttribute("boardFileDao");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      // 다운로드 받을 파일 번호를 알아낸다.
      int fileNo = Integer.parseInt(request.getParameter("fileNo"));

      // 파일 번호를 이용하여 파일 정보를 가져온다.
      BoardFile boardFile = boardFileDao.findByNo(fileNo);
      if (boardFile == null) {
        throw new RuntimeException("파일 정보 없음!");
      }

      // 파일을 찾는다.
      File downloadFile = new File(
          this.getServletContext().getRealPath("/board/upload/" + boardFile.getFilepath()));
      if (!downloadFile.exists()) {
        throw new RuntimeException("파일이 존재하지 않음!");
      }

      // 파일을 보내기 전에 클라이언트에게 파일에 대한 정보를 알려주기 위해 응답헤더를 추가한다.
      // => 보내는 데이터의 MIME 타입을 알려준다.
      //    예) Content-Type: image/jpeg
      response.setContentType(boardFile.getMimeType());

      // => 보내는 데이터의 파일 이름을 알려준다.
      //    예) Content-Disposition: attachment; filename="test.gif"
      response.setHeader("Content-Disposition",
          String.format("attachment; filename=\"%s\"", boardFile.getOriginalFilename()));

      try (// 파일을 읽기 위해 준비한다.
          BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(downloadFile));
          BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());) {

        // 파일에서 데이터를 읽어 클라이언트로 보낸다.
        int b;
        while ((b = fileIn.read()) != -1) {
          out.write(b);
        }
        out.flush();
      }

    } catch (Exception e) {
      request.getRequestDispatcher("/downloadfail.jsp").forward(request, response);
    }
  }
}






