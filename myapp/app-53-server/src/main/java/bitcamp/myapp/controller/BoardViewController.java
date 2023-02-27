package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;

public class BoardViewController implements PageController {

  private BoardService boardService;

  public BoardViewController(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("board",
        boardService.get(Integer.parseInt(request.getParameter("no"))));
    return"/board/view.jsp";
  }

}







