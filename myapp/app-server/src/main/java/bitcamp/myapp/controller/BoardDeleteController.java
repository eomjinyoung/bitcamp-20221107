package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

public class BoardDeleteController implements PageController {

  private BoardService boardService;

  public BoardDeleteController(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
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
}
