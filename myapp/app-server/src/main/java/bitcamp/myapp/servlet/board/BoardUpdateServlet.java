package bitcamp.myapp.servlet.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;

@MultipartConfig(maxFileSize = 1024 * 1024 * 50)
@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {
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
      Member loginUser = (Member) request.getSession().getAttribute("loginUser");

      Board board = new Board();
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));

      Board old = boardService.get(board.getNo());

      if (old.getWriter().getNo() != loginUser.getNo()) {
        response.sendRedirect("../auth/fail");
        return;
      }

      Collection<Part> parts = request.getParts();
      List<BoardFile> boardFiles = new ArrayList<>();
      for (Part part : parts) {
        if (!part.getName().equals("files") || part.getSize() == 0) {
          continue;
        }

        String filename = UUID.randomUUID().toString();
        part.write(this.getServletContext().getRealPath("/board/upload/" + filename));

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

    request.getRequestDispatcher("/board/update.jsp").forward(request, response);
  }

}




