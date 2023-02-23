package bitcamp.myapp.servlet.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

@WebServlet("/board/insert")
public class BoardInsertServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private TransactionManager txManager;
  private BoardDao boardDao;
  private BoardFileDao boardFileDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
    boardFileDao = (BoardFileDao) ctx.getAttribute("boardFileDao");
    txManager = (TransactionManager) ctx.getAttribute("txManager");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 멀티파트 데이터를 디스크에 저장한 후 객체를 리턴해주는 일을 한다.
    DiskFileItemFactory factory = new DiskFileItemFactory();

    // 멀티파트로 전송된 요청 데이터를 읽을 준비를 한다.
    ServletFileUpload upload = new ServletFileUpload(factory);

    // 위에서 준비한 객체를 사용하여 멀티파트 요청 데이터를 처리한다.
    // => 리턴되는 값은 각 파트를 FileItem 객체에 담은 객체 목록이다.
    try {
      List<FileItem> items = upload.parseRequest(request);

      // 일반 파라미터 값을 저장할 맵
      Map<String,String> paramMap = new HashMap<>();

      // 첨부파일을 보관할 목록
      List<FileItem> files = new ArrayList<>();

      // 파트의 값을 일반 파라미터와 첨부파일 파라미터로 분리하여 저장한다.
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

      // 로그인 사용자의 정보를 가져온다.
      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      Member writer = new Member();
      writer.setNo(loginUser.getNo());
      board.setWriter(writer);

      txManager.startTransaction();
      boardDao.insert(board);

      List<BoardFile> boardFiles = new ArrayList<>();
      for (FileItem file : files) {
        if (file.getSize() == 0) {
          continue;
        }

        String filename = UUID.randomUUID().toString();

        // 임시 저장된 첨부파일을 특정 디렉토리로 옮긴다.
        // 이때 전체 경로 및 파일명을 File 객체에 담아 넘겨야 한다.
        // 1) 서블릿 컨테이너가 실행하는 현재 웹 애플리케이션의 실제 경로 알아내기
        String realPath = this.getServletContext().getRealPath("/board/upload/" + filename);
        System.out.println(realPath);
        file.write(new File(realPath));

        BoardFile boardFile = new BoardFile();
        boardFile.setOriginalFilename(file.getName());
        boardFile.setFilepath(filename);
        boardFile.setMimeType(file.getContentType());
        boardFile.setBoardNo(board.getNo());
        //boardFileDao.insert(boardFile);

        boardFiles.add(boardFile);
      }

      if (boardFiles.size() > 0) {
        boardFileDao.insertList(boardFiles);
      }

      txManager.commit();

    } catch (Exception e) {
      txManager.rollback();
      e.printStackTrace();
      request.setAttribute("error", "data");
    }

    request.getRequestDispatcher("/board/insert.jsp").forward(request, response);
  }

}
