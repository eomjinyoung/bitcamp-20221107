package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

public class BoardFileDeleteController implements PageController {

  private BoardService boardService;

  public BoardFileDeleteController(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {

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
