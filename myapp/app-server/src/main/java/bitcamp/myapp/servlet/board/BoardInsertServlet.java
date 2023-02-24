package bitcamp.myapp.servlet.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;

@WebServlet("/board/insert")
public class BoardInsertServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardService boardService;

  @Override
  public void init() {
    boardService = (BoardService) getServletContext().getAttribute("boardService");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
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
        file.write(new File(getServletContext().getRealPath("/board/upload/" + filename)));

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

    request.getRequestDispatcher("/board/insert.jsp").forward(request, response);
  }

}
